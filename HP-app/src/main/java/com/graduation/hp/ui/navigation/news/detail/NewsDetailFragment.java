package com.graduation.hp.ui.navigation.news.detail;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.graduation.hp.core.HPApplication;
import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.app.di.component.DaggerFragmentComponent;
import com.graduation.hp.app.di.module.FragmentModule;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.RootFragment;
import com.graduation.hp.core.utils.DateUtils;
import com.graduation.hp.core.utils.GlideUtils;
import com.graduation.hp.core.utils.LogUtils;
import com.graduation.hp.presenter.NewsDetailPresenter;
import com.graduation.hp.repository.contact.NewsDetailContact;
import com.graduation.hp.repository.http.entity.ArticleVO;
import com.graduation.hp.ui.navigation.news.comment.NewsCommentFragment;
import com.graduation.hp.ui.navigation.news.comment.PrepareForDiscussionListener;
import com.graduation.hp.utils.StringUtils;
import com.graduation.hp.widget.AttentionButton;
import com.graduation.hp.widget.LikeButton;
import com.graduation.hp.widget.dialog.CommentDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class NewsDetailFragment extends RootFragment<NewsDetailPresenter>
        implements NewsDetailContact.View {

    public static final String EMPTY_HTML = "file:///android_asset/empty.html";

    public static NewsDetailFragment newInstance(long newsId) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putLong(Key.NEWS_ID, newsId);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.news_detail_author_icon_iv)
    AppCompatImageView newsDetailAuthorIconIv;

    @BindView(R.id.news_detail_author_icon_2_iv)
    AppCompatImageView newsDetailAuthorIcon2Iv;

    @BindView(R.id.news_detail_author_name_2_tv)
    AppCompatTextView newsDetailAuthorName2Tv;

    @BindView(R.id.news_detail_author_name_tv)
    AppCompatTextView newsDetailAuthorNameTv;

    @BindView(R.id.news_detail_author_physical_tv)
    AppCompatTextView newsDetailAuthorPhysicalTv;

    @BindView(R.id.news_detail_author_physical_tv_2)
    AppCompatTextView newsDetailAuthorPhysical2Tv;

    @BindView(R.id.news_detail_date_summary_tv)
    AppCompatTextView newsDetailDateSummaryTv;

    @BindView(R.id.news_detail_title_tv)
    AppCompatTextView newsDetailTitleTv;

    @BindView(R.id.news_detail_sub_cb)
    AttentionButton newsDetailSubCb;

    @BindView(R.id.news_detail_sub_cb_2)
    AttentionButton newsDetailSubCb2;

    @BindView(R.id.news_detail_author_info_2_cl)
    ConstraintLayout newsDetailAuthorInfo2Cl;

    @BindView(R.id.news_detail_author_info_cl)
    ConstraintLayout newsDetailAuthorInfoCl;

    @BindView(R.id.news_detail_comment_num_tv)
    AppCompatTextView newsDetailCommentNumTv;

    @BindView(R.id.news_detail_like_btn)
    LikeButton newsDetailLikeBtn;

    @BindView(R.id.news_detail_like_tv)
    AppCompatTextView newsDetailLikeTv;

    @BindView(R.id.news_detail_scroll_nsv)
    NestedScrollView newsDetailScrollNsv;

    private WebSettings mWebSettings;
    private WebView mWebView;
    private long mNewsId;
    private int mLikeNum = 0;
    private int mDiscussNum = 0;
    private ArticleVO mNews;
    private NewsCommentFragment mCommentFragment;
    private PrepareForDiscussionListener mDiscussionDialogListener;


    @Override
    public void onAttach(Context context) {
        try {
            mDiscussionDialogListener = (PrepareForDiscussionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement the PrepareForDiscussionListener");
        }
        super.onAttach(context);
    }

    @Override
    protected void init(Bundle savedInstanceState, View rootView) {
        super.init(savedInstanceState, rootView);
        Bundle args = getArguments();
        mNewsId = args.getLong(Key.NEWS_ID, 0L);
        if (savedInstanceState != null) {
            mNews = savedInstanceState.getParcelable(Key.NEWS);
        }
        if (mNewsId == 0L) {
            throw new IllegalArgumentException("NewsDetailFragment must receive the news's id");
        }
        initToolbar(rootView, "", R.mipmap.ic_navigation_back, R.mipmap.ic_toolbar_dot);
        initWebView(rootView);
        initListener();
    }

    @Override
    protected void onLazyLoad() {
        if (mNews == null) {
            mPresenter.getNewsDetailByNewsId(mNewsId);
        } else {
            onGetNewsDetailInfoSuccess(mNews);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (mNews != null) {
            outState.putParcelable(Key.NEWS, mNews);
        }
        super.onSaveInstanceState(outState);
    }

    @OnClick({R.id.news_detail_author_info_cl, R.id.news_detail_author_info_2_cl,
            R.id.news_detail_comment_iv_container, R.id.news_detail_comment_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.news_detail_author_info_cl:
            case R.id.news_detail_author_info_2_cl:
                // 跳转用户主页
                break;
            case R.id.news_detail_comment_tv:
            case R.id.news_detail_comment_iv_container:
                mDiscussionDialogListener.showCommentDialog(getString(R.string.hint_comment), listener);
                // 进行评论
                break;
        }
    }

    @Override
    protected boolean shouldShowNoDataView() {
        return false;
    }

    @Override
    protected int getNoDataStringResId() {
        return 0;
    }

    @Override
    protected void onRetryClick() {
        mPresenter.getNewsDetailByNewsId(mNewsId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_detail;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerFragmentComponent.builder()
                .appComponent(appComponent)
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onGetNewsDetailInfoSuccess(ArticleVO articleVO) {
        this.mNews = articleVO;
        this.mLikeNum = articleVO.getLikeNum();
        this.mDiscussNum = articleVO.getDiscussNum();
        showNewsContent();
        showNewsAuthorInfo();
        mPresenter.isFocusOn(articleVO.getUserId());
    }

    private void showNewsAuthorInfo() {
        GlideUtils.loadUserHead(newsDetailAuthorIconIv, mNews.getHeadUrl());
        GlideUtils.loadUserHead(newsDetailAuthorIcon2Iv, mNews.getHeadUrl());
        newsDetailAuthorNameTv.setText(mNews.getNickname());
        newsDetailAuthorName2Tv.setText(Html.fromHtml(getString(R.string.tips_news_author, mNews.getNickname())));
        newsDetailAuthorPhysicalTv.setText(mNews.getPhysiqueStr());
        newsDetailAuthorPhysical2Tv.setText(mNews.getPhysiqueStr());
        newsDetailDateSummaryTv.setText(DateUtils.formatPublishDate(mNews.getCreateTime()));
    }

    private void showNewsContent() {
        newsDetailTitleTv.setText(mNews.getTitle());
        mLikeNum = mNews.getLikeNum();
        mDiscussNum = mNews.getDiscussNum();
        newsDetailLikeTv.setText(String.valueOf(mLikeNum));
        newsDetailCommentNumTv.setText(StringUtils.getFormattedOverMaximumString(mDiscussNum, 999, R.string.tips_over_maximum));
        String body = mNews.getContent();
        // 使用css样式的方式设置图片大小
        String css = "<style type=\"text/css\"> img {width:100%;height:auto;}body {margin-right:15px;margin-left:15px;margin-top:15px;font-size:24px;}</style>";
        String html = "<html><head>" + css + "</head><body><div id=\"box\">" + body + "</div></body></html>";
        mWebView.loadUrl(EMPTY_HTML);
        mWebView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", "");
    }

    @Override
    public void onGetAttentionSuccess(boolean isFocusOn) {
        showNewsSubButton(isFocusOn);
    }

    @Override
    public void operateLikeStateSuccess(boolean isLiked) {
        newsDetailLikeBtn.setLiked(isLiked);
        newsDetailLikeTv.setText(String.valueOf(isLiked ? ++mLikeNum : --mLikeNum));
        showMessage(getString(isLiked ? R.string.tips_like_success : R.string.tips_cancel_like_success));
    }

    @Override
    public void operateLikeStateError() {
        newsDetailLikeBtn.setLiked(!newsDetailLikeBtn.isLiked());
        showMessage(getString(R.string.tips_happen_unknown_error));
    }

    @Override
    public void operateAttentionStateSuccess(boolean isFocusOn) {
        showNewsSubButton(isFocusOn);
        showMessage(getString(isFocusOn ? R.string.tips_focus_on_success : R.string.tips_cancel_focus_on_success));
    }

    @Override
    public void operateArticleCommentStatus(boolean success) {
        mDiscussionDialogListener.dismissCommentDialog();
        showMessage(getString(success ? R.string.tips_comment_success : R.string.tips_comment_failed));
    }

    private void showNewsSubButton(boolean isFocusOn) {
        newsDetailSubCb.setFocusOn(isFocusOn);
        newsDetailSubCb2.setFocusOn(isFocusOn);
    }

    private CommentDialog.CommentDialogClickListener listener = new CommentDialog.CommentDialogClickListener() {
        @Override
        public void onDialogBackPressed() {
            mDiscussionDialogListener.dismissCommentDialog();
            mPresenter.getModel().cancelSubscribe();
        }

        @Override
        public void onSendMessage(String content) {
            mPresenter.addComment(mNewsId, content);
        }
    };

    @Override
    public void dismissDialog() {
        super.dismissDialog();
        if (mDiscussionDialogListener != null) {
            mDiscussionDialogListener.dismissCommentDialog();
        }
    }

    /**
     * 桥接JS方法
     */
    class JavaScriptInterface {
        /**
         * 在javaScript中获得html中的图片url
         *
         * @param imageUrls 图片url
         */
        @android.webkit.JavascriptInterface
        public void startPhotoActivity(String imageUrls) {
            LogUtils.d(imageUrls);
            if (TextUtils.isEmpty(imageUrls)) return;

        }
    }

    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            LogUtils.d("加载进度发生变化:" + newProgress);
        }
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            LogUtils.d("网页开始加载:" + url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            LogUtils.d("网页加载完成..." + url);
            mWebSettings.setBlockNetworkImage(false);
            // 网页加载完毕后，将JS方法加载到网页中
            mWebView.loadUrl("javascript:(" + "function(){var imgs = document.getElementsByTagName(\"img\");var images = imgs[0].src;for(var i = 1; i < imgs.length; i++){images += (\",\"+imgs[i].src);} " +
                    "for(var i = 0; i < imgs.length; i++) {imgs[i].onclick = function() {androidMethod.startPhotoActivity(images);}}");
            int w = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            //重新测量
            mWebView.measure(w, h);
            showMain();
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            LogUtils.d("加载的资源:" + url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogUtils.d("拦截到URL信息为:" + url);
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    @Override
    public void onDestroyView() {
        if (mWebView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = mWebView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mWebView);
            }
            mWebView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mWebView.getSettings().setJavaScriptEnabled(false);
            mWebView.clearHistory();
            mWebView.clearView();
            mWebView.removeAllViews();
            mWebView.destroy();
        }
        super.onDestroyView();
    }

    /**
     * 设置WebView相关配置
     */
    private void initWebView(View rootView) {
        mWebView = new WebView(HPApplication.getInstance());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        FrameLayout frameLayout = rootView.findViewById(R.id.news_detail_web_container);
        frameLayout.addView(mWebView);
        mWebView.setLayoutParams(layoutParams);
        mWebSettings = mWebView.getSettings();
        //自适应屏幕
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        // 打开页面时， 自适应屏幕
        mWebSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        mWebSettings.setSupportZoom(true); //支持缩放
        mWebSettings.setJavaScriptEnabled(true);  //开启javascript
        mWebSettings.setDomStorageEnabled(true);  //开启DOM
        mWebSettings.setDefaultTextEncodingName("utf-8"); //设置编码
        // // web页面处理
        mWebSettings.setAllowFileAccess(true);// 支持文件流
        //提高网页加载速度，暂时阻塞图片加载，然后网页加载好了，再进行加载图片
        mWebSettings.setBlockNetworkImage(true);
        //开启缓存机制
        mWebSettings.setAppCacheEnabled(true);
        setTextSize();
        //设置webview
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.addJavascriptInterface(new JavaScriptInterface(), "androidMethod");
    }

    private void setTextSize() {
        int textSize = mPresenter.getLocalTextSize();
        mWebSettings.setTextZoom(175 + textSize * 25);
    }

    private void initListener() {
        newsDetailLikeBtn.setLikeButtonClickListener((v, liked) -> mPresenter.likeArticle(mNewsId));
        newsDetailSubCb.setAttentionButtonClickListener((v, focusOn) -> {
            if (mNews == null)
                return;
            mPresenter.focusOnAuthor(mNews.getUserId());
        });
        newsDetailSubCb2.setAttentionButtonClickListener((v, focusOn) -> {
            if (mNews == null)
                return;
            mPresenter.focusOnAuthor(mNews.getUserId());
        });
        newsDetailScrollNsv.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (newsDetailAuthorInfo2Cl != null && newsDetailAuthorInfoCl != null) {
                if (scrollY >= newsDetailAuthorInfo2Cl.getY() + newsDetailAuthorInfo2Cl.getMeasuredHeight()) {
                    newsDetailAuthorInfoCl.setVisibility(View.VISIBLE);
                } else {
                    newsDetailAuthorInfoCl.setVisibility(View.INVISIBLE);
                }
            }
            if (scrollY <= (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                LogUtils.d("BOTTOM SCROLL");
                if (mCommentFragment == null || !mCommentFragment.isAdded()) {
                    mCommentFragment = NewsCommentFragment.newInstance(mNewsId);
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.news_detail_comment_container, mCommentFragment).commit();
                }
            }
        });
    }

}
