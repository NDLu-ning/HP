package com.graduation.hp.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.app.listener.OnItemClickListener;
import com.graduation.hp.core.app.listener.SimpleItemClickListenerAdapter;
import com.graduation.hp.core.ui.BaseFragment;
import com.graduation.hp.repository.http.entity.pojo.SearchKeyword;
import com.graduation.hp.utils.ViewUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import am.widget.wraplayout.WrapLayout;
import butterknife.BindView;

public class SearchFragment extends BaseFragment {

    public interface SearchFragmentListener {
        void getSearchKeywords();

        void saveSearchKeyword(String keyword);

        void startSearchResultPage(String keyword);
    }

    public static SearchFragment newInstance(String keyword) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(Key.KEYWORD, keyword);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.search_history_empty_tv)
    AppCompatTextView searchHistoryEmptyTv;

    @BindView(R.id.search_history_wrap_wl)
    WrapLayout searchHistoryWrapWl;

    @BindView(R.id.search_history_keyword_et)
    AppCompatEditText searchHistoryKeywordEt;

    @BindView(R.id.search_history_clear_btn)
    AppCompatButton searchHistoryClearBtn;

    private SearchFragmentListener mListener;

    private String keyword;

    @Override
    public void onAttach(Context context) {
        try {
            mListener = (SearchFragment.SearchFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement UserInfoFragmentListener");
        }
        super.onAttach(context);
    }

    @Override
    protected void init(Bundle savedInstanceState, View view) {
        super.init(savedInstanceState, view);
        if (savedInstanceState != null) {
            keyword = savedInstanceState.getString(Key.KEYWORD, "");
        } else {
            Bundle bundle = getArguments();
            keyword = bundle.getString(Key.KEYWORD, "");
        }
        searchHistoryKeywordEt.setText(keyword);
        searchHistoryKeywordEt.setSelection(!TextUtils.isEmpty(keyword) ? keyword.length() : 0);
        view.findViewById(R.id.toolbar_left_btn).setOnClickListener(v -> onToolbarLeftClickListener(v));
        view.findViewById(R.id.toolbar_right_btn).setOnClickListener(v -> onToolbarRightClickListener(v));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(Key.KEYWORD, keyword);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onLazyLoad() {
        mListener.getSearchKeywords();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetSearchWordsSuccess(List<SearchKeyword> list) {
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                SearchKeyword keyword = list.get(i);
                searchHistoryWrapWl.addView(ViewUtils.makeTagView(getContext(), keyword.getKeyword(), onItemClick));
            }
        } else {
            searchHistoryClearBtn.setVisibility(View.GONE);
            searchHistoryWrapWl.setVisibility(View.GONE);
            searchHistoryEmptyTv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onToolbarRightClickListener(View v) {
        keyword = searchHistoryKeywordEt.getText().toString();
        mListener.saveSearchKeyword(keyword);
    }

    @Override
    public void onToolbarLeftClickListener(View v) {
        ((SearchActivity) getActivity()).onToolbarLeftClickListener(v);
    }

    private OnItemClickListener onItemClick = new SimpleItemClickListenerAdapter() {
        @Override
        public void OnItemClick(View view, Object object) {
            keyword = (String) object;
            mListener.startSearchResultPage(keyword);
        }
    };


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
    }

    @Override
    public boolean useEventBus() {
        return true;
    }
}
