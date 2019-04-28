//package com.graduation.hp.ui.navigation.news.detail;
//
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.constraint.ConstraintLayout;
//import android.support.v4.widget.NestedScrollView;
//import android.support.v7.widget.AppCompatCheckBox;
//import android.support.v7.widget.AppCompatImageView;
//import android.support.v7.widget.AppCompatTextView;
//import android.text.Html;
//import android.text.TextUtils;
//import android.util.TypedValue;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewParent;
//import android.webkit.WebChromeClient;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.FrameLayout;
//
//import com.lning.melireader.R;
//import com.lning.melireader.app.component.DaggerAppFragmentComponent;
//import com.lning.melireader.app.constant.AppConstant;
//import com.lning.melireader.app.module.FragmentModule;
//import com.lning.melireader.contact.NewsDetailContact;
//import com.lning.melireader.core.app.di.component.AppComponent;
//import com.lning.melireader.core.app.listener.OnItemClickListener;
//import com.lning.melireader.core.app.listener.SimpleItemClickListenerAdapter;
//import com.lning.melireader.core.repository.db.pojo.TagPojo;
//import com.lning.melireader.core.repository.http.bean.CommentVo;
//import com.lning.melireader.core.repository.http.bean.NewsListVo;
//import com.lning.melireader.core.repository.http.bean.NewsVo;
//import com.lning.melireader.core.repository.http.bean.SearchChannelItem;
//import com.lning.melireader.core.ui.fragment.RootFragment;
//import com.lning.melireader.core.utils.AnimUtils;
//import com.lning.melireader.core.utils.CommonUtils;
//import com.lning.melireader.core.utils.GlideUtils;
//import com.lning.melireader.core.utils.LogUtils;
//import com.lning.melireader.core.utils.ToastUtils;
//import com.lning.melireader.presenter.NewsDetailPresenter;
//import com.lning.melireader.ui.main.news.comment.NewsCommentFragment;
//import com.lning.melireader.ui.main.news.photo.PhotoFragment;
//import com.lning.melireader.ui.main.user.detail.UserFragment;
//import com.lning.melireader.ui.search.SearchResultFragment;
//import com.lning.melireader.widget.CommentDialog;
//import com.lning.melireader.widget.TagDialog;
//
//import org.greenrobot.eventbus.EventBus;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import am.widget.wraplayout.WrapLayout;
//import butterknife.BindView;
//import butterknife.OnClick;
//import io.reactivex.Observable;
//import io.reactivex.functions.Consumer;
//
///**
// * Created by Ning on 2019/2/18.
// */
//
//public class NewsDetailFragment extends RootFragment<NewsDetailPresenter>
//        implements NewsDetailContact.View {
//
//    @BindView(R.id.news_detail_publisher_icon_iv)
//    AppCompatImageView newsDetailPublisherIconIv;
//
//    @BindView(R.id.news_detail_publisher_icon_2_iv)
//    AppCompatImageView newsDetailPublisherIcon2Iv;
//
//    @BindView(R.id.news_detail_publisher_name_2_tv)
//    AppCompatTextView newsDetailPublisherName2Tv;
//
//    @BindView(R.id.news_detail_publisher_name_tv)
//    AppCompatTextView newsDetailPublisherNameTv;
//
//    @BindView(R.id.news_detail_date_summary_tv)
//    AppCompatTextView newsDetailDateSummaryTv;
//
//    @BindView(R.id.news_detail_title_tv)
//    AppCompatTextView newsDetailTitleTv;
//
//    @BindView(R.id.news_detail_attention_cb)
//    AppCompatCheckBox newsDetailAttentionCb;
//
//    @BindView(R.id.news_detail_attention_cb_2)
//    AppCompatCheckBox newsDetailAttentionCb2;
//
//    @BindView(R.id.news_detail_publisher_info_2_cl)
//    ConstraintLayout newsDetailPublisherInfo2Cl;
//
//    @BindView(R.id.news_detail_publisher_info_cl)
//    ConstraintLayout newsDetailPublisherInfoCl;
//
//    @BindView(R.id.news_detail_operate_cl)
//    ConstraintLayout newsDetailOperateCl;
//
//    @BindView(R.id.news_detail_tag_wl)
//    WrapLayout newsDetailWrapLayout;
//    @BindView(R.id.news_detail_comment_num_tv)
//    AppCompatTextView newsDetailCommentNumTv;
//    @BindView(R.id.news_detail_collection_iv)
//    AppCompatImageView newsDetailCollectionIv;
//
//    @BindView(R.id.news_detail_like_btn)
//    AppCompatImageView newsDetailLikeBtn;
//
//    @BindView(R.id.news_detail_like_tv)
//    AppCompatTextView newsDetailLikeTv;
//
//    @BindView(R.id.news_detail_dislike_tv)
//    AppCompatTextView newsDetailDislikeTv;
//
//    @BindView(R.id.news_detail_scroll_nsv)
//    NestedScrollView newsDetailScrollNsv;
//
//    private NewsListVo newsListVo;
//    private NewsVo newsVo;
//    private WebSettings mWebSettings;
//    private WebView mWebView;
//    private CommentDialog inputDialog;
//    private TagDialog tagDialog;
//    private List<TagPojo> tagPojos = new ArrayList<>();
//
//    private NewsCommentFragment commentFragment;
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.fragment_news_detail;
//    }
//
//    @Override
//    protected void init(@Nullable Bundle savedInstanceState, View rootView) {
//        super.init(savedInstanceState, rootView);
//        initToolbar(rootView, "", R.mipmap.ic_navigation_back, R.mipmap.ic_toolbar_dot);
//        initBasicData();
//        initWebView(rootView);
//        initListener();
//        mPresenter.getNewsDetailByNewsId(newsListVo.getId());
//    }
//
//    private void initListener() {
//        newsDetailScrollNsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (newsDetailPublisherInfo2Cl != null && newsDetailPublisherInfoCl != null) {
////                    LogUtils.d(scrollY + "," + newsDetailPublisherInfo2Cl.getMeasuredHeight() + "," + newsDetailPublisherInfo2Cl.getY());
//                    if (scrollY >= newsDetailPublisherInfo2Cl.getY() + newsDetailPublisherInfo2Cl.getMeasuredHeight()) {
//                        newsDetailPublisherInfoCl.setVisibility(View.VISIBLE);
//                    } else {
//                        newsDetailPublisherInfoCl.setVisibility(View.INVISIBLE);
//                    }
//                }
//                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
//                    LogUtils.d("BOTTOM SCROLL");
//                    if (commentFragment == null || !commentFragment.isAdded()) {
//                        commentFragment = NewsCommentFragment.newInstance(newsListVo.getId(), newsListVo.getCtype());
//                        loadRootFragment(R.id.news_detail_comment_app_container, commentFragment);
//                    }
//                }
//            }
//        });
//    }
//
//    /**
//     * 设置WebView相关配置
//     */
//    private void initWebView(View rootView) {
//        mWebView = new WebView(getSimpleActivity().getApplicationContext());
//        FrameLayout frameLayout = rootView.findViewById(R.id.news_detail_web_container);
//        frameLayout.addView(mWebView);
//        mWebSettings = mWebView.getSettings();
//        //自适应屏幕
//        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        mWebSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//        // 打开页面时， 自适应屏幕
//        mWebSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
//        mWebSettings.setSupportZoom(true); //支持缩放
//        mWebSettings.setJavaScriptEnabled(true);  //开启javascript
//        mWebSettings.setDomStorageEnabled(true);  //开启DOM
//        mWebSettings.setDefaultTextEncodingName("utf-8"); //设置编码
//        // // web页面处理
//        mWebSettings.setAllowFileAccess(true);// 支持文件流
//        //提高网页加载速度，暂时阻塞图片加载，然后网页加载好了，再进行加载图片
//        mWebSettings.setBlockNetworkImage(true);
//        //开启缓存机制
//        mWebSettings.setAppCacheEnabled(true);
//        setTextSize();
//        //设置webview
//        mWebView.setWebChromeClient(new MyWebChromeClient());
//        mWebView.setWebViewClient(new MyWebViewClient());
//        mWebView.addJavascriptInterface(new JavaScriptInterface(), "androidMethod");
//    }
//
//    private void initBasicData() {
//        Bundle bundle = getArguments();
//        newsListVo = bundle.getParcelable(AppConstant.NEWSLIST_VO);
//        if (newsListVo == null) {
//            throw new IllegalArgumentException("新闻不能为空");
//        }
//        Observable.timer(200, TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
//            @Override
//            public void accept(Long aLong) throws Exception {
//                mPresenter.checkNewsListCollected(newsListVo.getId());
//            }
//        });
//        initBasicView(newsListVo);
//    }
//
//    private void initBasicView(NewsListVo newsVo) {
//        newsDetailTitleTv.setText(newsVo.getTitle());
//        GlideUtils.loadUserHead(newsDetailPublisherIcon2Iv, String.format(AppConstant.IMAGE_URL, newsVo.getPublisherProfile()));
//        GlideUtils.loadUserHead(newsDetailPublisherIconIv, String.format(AppConstant.IMAGE_URL, newsVo.getPublisherProfile()));
//        newsDetailPublisherNameTv.setText(newsVo.getPublisherName());
//        newsDetailPublisherName2Tv.setText(Html.fromHtml(getString(R.string.tips_news_detail_channel_name_template, newsVo.getPublisherName())));
//        newsDetailCommentNumTv.setText(newsVo.getCommentCount() + "");
//        String summary = !TextUtils.isEmpty(newsVo.getSummary()) ? newsVo.getSummary() : "";
//        if (summary.length() > 10) {
//            summary = summary.substring(0, 10) + "...";
//        }
//        newsDetailDateSummaryTv.setText(Html.fromHtml(getString(R.string.tips_news_detail_channel_date_summary_template, CommonUtils.formatPublishDate(newsVo.getCreated()), summary)));
//        String[] tags = newsVo.getDislikeNames().split(",");
//        String[] tagIds = newsVo.getDislikeIds().split(",");
//        for (int i = 0; i < tags.length; i++) {
//            newsDetailWrapLayout.addView(makeTagView(tagIds[i], tags[i], simpleItemClickListenerAdapter));
//        }
//        setBasicViewVisible(View.GONE);
//    }
//
//    private SimpleItemClickListenerAdapter simpleItemClickListenerAdapter = new SimpleItemClickListenerAdapter() {
//
//        @Override
//        public void OnItemClick(View view, int position) {
//            if (position == -1) {
//                mPresenter.cancelAddNewsComment();
//            } else if (position == -2) {
//                if (tagPojos.size() == 0) {
//                    mPresenter.getAllUserTags();
//                } else {
//                    showTagDialog();
//                }
//            } else {
//                onUpdateCollectionError();
//            }
//        }
//
//        @Override
//        public void OnItemClick(View view, Object object) {
//            if (object instanceof Wrapper) {
//                Wrapper wrapper = (Wrapper) object;
//                start(SearchResultFragment.newInstance(wrapper.tag));
//            } else if (object instanceof SearchChannelItem) {
//                SearchChannelItem item = (SearchChannelItem) object;
//                start(UserFragment.newInstance(item.getChannelId(), item.getChannelName(), item.getChannelImage()));
//            }
//        }
//
//        @Override
//        public void OnItemClick(View view, Object object, int position) {
//            if (object instanceof String) {
//                // 评论操作
//                if (position == -1)
//                    mPresenter.addNewsComment(newsListVo.getId(), object.toString());
//                else if (position == -2)
//                    mPresenter.updateCollection(newsListVo.getId(), object.toString());
//            }
//        }
//    };
//
//    private void showTagDialog() {
//        if (tagDialog == null || tagDialog.isCancelable()) {
//            LogUtils.d("show");
//            tagDialog = new TagDialog(tagPojos, getString(R.string.tips_create_tag), simpleItemClickListenerAdapter);
//            tagDialog.show(getFragmentManager(), "tagDialog");
//        }
//    }
//
//    private AppCompatTextView makeTagView(final String tagId, final String tag, final OnItemClickListener listeners) {
//        AppCompatTextView button = new AppCompatTextView(getContext());
//        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12.0F);
//        button.setBackgroundResource(R.drawable.bg_common_tag_tv);
//        button.setText(tag);
//        button.setPadding(25, 16, 16, 25);
//        button.setTextColor(getResources().getColor(R.color.md_grey_700));
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listeners.OnItemClick(view, new Wrapper(tagId, tag));
//            }
//        });
//        return button;
//    }
//
//    private void setBasicViewVisible(int visible) {
//        newsDetailTitleTv.setVisibility(visible);
//        newsDetailPublisherInfo2Cl.setVisibility(visible);
//        newsDetailOperateCl.setVisibility(visible);
//        newsDetailWrapLayout.setVisibility(visible);
//    }
//
//    @OnClick({R.id.news_detail_attention_cb})
//    public void onNewsDetailAttentionChanged() {
//        if (newsVo == null) {
//            showMessage(getString(R.string.tips_please_wait));
//            return;
//        }
//        mPresenter.operateAttention(newsDetailAttentionCb.isChecked(), newsVo.getChannelId());
//    }
//
//    @OnClick({R.id.news_detail_attention_cb_2})
//    public void onNewsDetailAttentionChanged2() {
//        if (newsVo == null) {
//            showMessage(getString(R.string.tips_please_wait));
//            return;
//        }
//        mPresenter.operateAttention(newsDetailAttentionCb2.isChecked(), newsVo.getChannelId());
//    }
//
//    @OnClick({R.id.news_detail_publisher_icon_iv, R.id.news_detail_publisher_icon_2_iv, R.id.news_detail_publisher_name_tv, R.id.news_detail_publisher_name_2_tv})
//    public void onUserClick() {
//        simpleItemClickListenerAdapter.OnItemClick(null, new SearchChannelItem(newsListVo.getChannelId(),
//                newsListVo.getPublisherName(), newsListVo.getPublisherProfile()));
//    }
//
//    @Override
//    protected void onToolbarRightClickListener(View view) {
//        // TODO 分享弹窗
//    }
//
//    @Override
//    protected void onNavigationClickListener(View view) {
//        getSimpleActivity().onBackPressedSupport();
//    }
//
//    @OnClick({R.id.news_detail_comment_iv, R.id.news_detail_comment_tv})
//    public void onCommentClick() {
//        if (inputDialog == null || !inputDialog.isAdded()) {
//            inputDialog = new CommentDialog(getString(R.string.tips_news_comment_hint), simpleItemClickListenerAdapter);
//            inputDialog.show(getFragmentManager(), "commentDialog");
//        }
//    }
//
//    @OnClick(R.id.news_detail_collection_iv)
//    public void onCollectionClick() {
//        LogUtils.d("onCollectionClick:newsVo:" + (newsVo == null));
//        if (newsVo == null) {
//            showMessage(getString(R.string.tips_please_wait));
//            return;
//        }
//        if (newsVo.isCollected()) {
//            mPresenter.cancelCollectNews(newsListVo.getId());
//        } else {
//            mPresenter.collectNews(newsVo);
//        }
//    }
//
//    @OnClick(R.id.news_detail_share_iv)
//    public void onShareClick() {
//    }
//
//    public static NewsDetailFragment newInstance(NewsListVo newsListVo) {
//        NewsDetailFragment fragment = new NewsDetailFragment();
//        Bundle bundle = new Bundle();
//        bundle.putParcelable(AppConstant.NEWSLIST_VO, newsListVo);
//        fragment.setArguments(bundle);
//        return fragment;
//    }
//
//    @Override
//    protected void setupFragmentComponent(AppComponent appComponent) {
//        DaggerAppFragmentComponent.builder().appComponent(appComponent)
//                .fragmentModule(new FragmentModule(this))
//                .build().inject(this);
//    }
//
//    public void setSubscriptCbStatus(boolean isSubscripted) {
//        newsDetailAttentionCb.setChecked(isSubscripted);
//        newsDetailAttentionCb2.setChecked(isSubscripted);
//        newsDetailAttentionCb.setText(getString(isSubscripted ? R.string.tips_already_subscripted : R.string.tips_not_subscript));
//        newsDetailAttentionCb2.setText(getString(isSubscripted ? R.string.tips_already_subscripted : R.string.tips_not_subscript));
//    }
//
//    private void setTextSize() {
//        int textSize = mPresenter.getModel().getRepositoryManager().getLocalTextSize();
//        mWebSettings.setTextZoom(175 + textSize * 25);
//    }
//
//    @OnClick(R.id.news_detail_like_ll)
//    public void onLikeClick() {
//        if (newsVo == null) {
//            showMessage(getString(R.string.tips_please_wait));
//            return;
//        }
//        AnimUtils.setScaleAnimation(newsDetailLikeBtn, 1000);
//        if (!newsVo.isLiked()) {
//            mPresenter.likeObject(newsListVo.getId());
//            newsVo.setLiked(true);
//            newsVo.setLikeCount(newsVo.getLikeCount() + 1);
//            newsDetailLikeBtn.setImageResource(R.mipmap.ic_like_seleced);
//            newsDetailLikeTv.setTextColor(getResources().getColor(R.color.md_selected_blue));
//            newsDetailLikeTv.setText(newsVo.getLikeCount() + "");
//        }
//    }
//
//    @OnClick(R.id.news_detail_dislike_ll)
//    public void onDislikeClick() {
//        if (newsVo == null) {
//            showMessage(getString(R.string.tips_please_wait));
//            return;
//        }
//        AnimUtils.setScaleAnimation(newsDetailDislikeTv, 1000);
//        mPresenter.dislikeObject(newsListVo.getId());
//    }
//
//
//    /**
//     * 被JavaScript调用的Android方法
//     * 点击webView中的图片能够跳转到PhotoActivity中查看大图
//     */
//    class JavaScriptInterface {
//        /**
//         * 在javaScript中获得html中的图片url
//         *
//         * @param imageUrls 图片url
//         */
//        @android.webkit.JavascriptInterface
//        public void startPhotoActivity(String imageUrls) {
//            LogUtils.d(imageUrls);
//            if (TextUtils.isEmpty(imageUrls)) return;
//            start(PhotoFragment.newsInstance(imageUrls), SINGLETASK);
//        }
//
//        @android.webkit.JavascriptInterface
//        public void resize(final float height) {
//            LogUtils.d(height + "");
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    mWebView.setLayoutParams(new FrameLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, (int) (height * getResources().getDisplayMetrics().density)));
//                }
//            });
//        }
//    }
//
//    class MyWebChromeClient extends WebChromeClient {
//        @Override
//        public void onProgressChanged(WebView view, int newProgress) {
//            super.onProgressChanged(view, newProgress);
//            LogUtils.d("加载进度发生变化:" + newProgress);
//        }
//    }
//
//    class MyWebViewClient extends WebViewClient {
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            super.onPageStarted(view, url, favicon);
//            LogUtils.d("网页开始加载:" + url);
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//            LogUtils.d("网页加载完成..." + url);
//            mWebSettings.setBlockNetworkImage(false);
//            // 网页加载完毕后，将其js方法注入到网页中
//            mWebView.loadUrl("javascript:(" + "function(){var imgs = document.getElementsByTagName(\"img\");var images = imgs[0].src;for(var i = 1; i < imgs.length; i++){images += (\",\"+imgs[i].src);} for(var i = 0; i < imgs.length; i++) {imgs[i].onclick = function() {androidMethod.startPhotoActivity(images);}}" +
//                    "androidMethod.resize(document.body.getBoundingClientRect().height)}" + ")()");
//            int w = View.MeasureSpec.makeMeasureSpec(0,
//                    View.MeasureSpec.UNSPECIFIED);
//            int h = View.MeasureSpec.makeMeasureSpec(0,
//                    View.MeasureSpec.UNSPECIFIED);
//            //重新测量
//            mWebView.measure(w, h);
//            showMain();
//        }
//
//        @Override
//        public void onLoadResource(WebView view, String url) {
//            super.onLoadResource(view, url);
//            LogUtils.d("加载的资源:" + url);
//        }
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            LogUtils.d("拦截到URL信息为:" + url);
//            return super.shouldOverrideUrlLoading(view, url);
//        }
//    }
//
//    @Override
//    public void onDestroyView() {
//        if (mWebView != null) {
//            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
//            // destory()
//            ViewParent parent = mWebView.getParent();
//            if (parent != null) {
//                ((ViewGroup) parent).removeView(mWebView);
//            }
//            mWebView.stopLoading();
//            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
//            mWebView.getSettings().setJavaScriptEnabled(false);
//            mWebView.clearHistory();
//            mWebView.clearView();
//            mWebView.removeAllViews();
//            mWebView.destroy();
//        }
//        onAddNewsCommentError();
//        onUpdateCollectionError();
//        super.onDestroyView();
//    }
//
//
//    @Override
//    public void onGetNewsVoSuccess(NewsVo newsVo) {
//        this.newsVo = newsVo;
//        String body = newsVo.getNewsDoc();
//        // 使用css样式的方式设置图片大小
//        String css = "<style type=\"text/css\"> img {width:100%;height:auto;}body {margin-right:15px;margin-left:15px;margin-top:15px;font-size:24px;}</style>";
//        String html = "<html><head>" + css + "</head><body><div id=\"box\">" + body + "</div></body></html>";
//        onGetCollectedSuccess(newsVo.isCollected());
//        setBasicViewVisible(View.VISIBLE);
//        setSubscriptCbStatus(newsVo.isSubscripted());
//        newsDetailLikeBtn.setImageResource(newsVo.isLiked() ? R.mipmap.ic_like_seleced : R.mipmap.ic_like_normal);
//        newsDetailLikeTv.setText(newsVo.getLikeCount() + "");
//        newsDetailLikeTv.setTextColor(getResources().getColor(newsVo.isLiked() ? R.color.md_selected_blue : R.color.md_black_300));
//        newsDetailDislikeTv.setText(newsVo.getDislikeCount() + "");
//        mWebView.loadUrl(AppConstant.EMPTY_HTML);
//        mWebView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", "");
//    }
//
//    @Override
//    public void onAddNewsCommentSuccess(CommentVo commentVo) {
//        onAddNewsCommentError();
//        EventBus.getDefault().post(commentVo);
//    }
//
//    @Override
//    public void onAddNewsCommentError() {
//        if (inputDialog != null && inputDialog.isAdded()) {
//            inputDialog.hideSoftkeyboard();
//            inputDialog.hideProgressDialog();
//            inputDialog.dismiss();
//            getFragmentManager().beginTransaction().remove(inputDialog).commit();
//            inputDialog = null;
//        }
//    }
//
//
//    @Override
//    public void onOperateCollectNewsSuccess(boolean collected) {
//        onGetCollectedSuccess(collected);
//        if (collected) {
//            ToastUtils.showCollectedSuccessToast(getContext(), simpleItemClickListenerAdapter);
//        } else { // 更新收藏标签
//            showMessage(getString(R.string.tips_cancel_collection_success));
//        }
//    }
//
//    /**
//     * 收藏与取消收藏操作
//     *
//     * @param collected
//     */
//    @Override
//    public void onGetCollectedSuccess(boolean collected) {
//        newsVo.setCollected(collected);
//        newsDetailCollectionIv.setImageResource(collected ? R.mipmap.ic_center_collection_selected : R.mipmap.ic_center_collection);
//    }
//
//    @Override
//    public void onUpdateCollectionError() {
//        if (tagDialog != null && tagDialog.isAdded()) {
//            tagDialog.hideSoftkeyboard();
//            tagDialog.hideProgressDialog();
//            tagDialog.dismiss();
//            getFragmentManager().beginTransaction().remove(tagDialog).commit();
//            tagDialog = null;
//        }
//    }
//
//    @Override
//    public void onGetUserTagsSuccess(List<TagPojo> tagPojoList) {
//        this.tagPojos = tagPojoList;
//        showTagDialog();
//    }
//
//    @Override
//    public void onDislikeObjectSuccess(boolean isDisliked) {
//        newsVo.setDisliked(isDisliked);
//        int dislikeCount = newsVo.getDislikeCount();
//        newsVo.setDislikeCount(isDisliked ? dislikeCount + 1 : dislikeCount - 1 > 0 ? dislikeCount - 1 : 0);
//        newsDetailDislikeTv.setText(newsVo.getDislikeCount() + "");
//    }
//
//    @Override
//    public void onOperateAttentionSuccess(boolean isAttention) {
//        setSubscriptCbStatus(isAttention);
//        if (isAttention) {
//            showMessage(getString(R.string.tips_attention_success));
//        } else {
//            showMessage(getString(R.string.tips_cancel_attention_success));
//        }
//
//    }
//
//    class Wrapper {
//        String tagId;
//        String tag;
//
//        public Wrapper(String tagId, String tag) {
//            this.tagId = tagId;
//            this.tag = tag;
//        }
//    }
//}
