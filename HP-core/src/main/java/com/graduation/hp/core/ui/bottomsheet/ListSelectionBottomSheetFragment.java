package com.graduation.hp.core.ui.bottomsheet;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.graduation.hp.core.R;
import com.graduation.hp.core.repository.entity.BottomSheetOption;

import java.util.ArrayList;
import java.util.List;

public class ListSelectionBottomSheetFragment extends BottomSheetDialogFragment
        implements BottomSheetListItemViewHolder.BottomSheetListItemViewHolderListener {


    public static final String TITLE = "TITLE";
    public static final String OPTION = "OPTION";
    private String mTitle;
    private ArrayList<BottomSheetOption> mOptions;
    private ListSelectionBottomSheetFragmentListener mListener;


    public interface ListSelectionBottomSheetFragmentListener {
        void onBottomSheetOptionSelected(BottomSheetOption option);
    }

    public static ListSelectionBottomSheetFragment newInstance(String title, @NonNull ArrayList<BottomSheetOption> options) {
        final Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putParcelableArrayList(OPTION, options);
        final ListSelectionBottomSheetFragment fragment = new ListSelectionBottomSheetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            final Bundle args = getArguments();
            mTitle = args.getString(TITLE);
            mOptions = args.getParcelableArrayList(OPTION);
        } else {
            mTitle = savedInstanceState.getString(TITLE);
            mOptions = savedInstanceState.getParcelableArrayList(OPTION);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View layout = inflater.inflate(getLayoutId(), container, false);
        final TextView titleTextView = (TextView) layout.findViewById(R.id.text_view_title);
        if (TextUtils.isEmpty(mTitle)) {
            titleTextView.setVisibility(View.GONE);
        } else {
            titleTextView.setText(mTitle);
        }

        final RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        final ListSelectionBottomSheetAdapter adapter = new ListSelectionBottomSheetAdapter(mOptions);
        recyclerView.setAdapter(adapter);

        return layout;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(TITLE, mTitle);
        outState.putParcelableArrayList(OPTION, mOptions);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        final View layout = View.inflate(getContext(), getLayoutId(), null);
        dialog.setContentView(layout);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (mListener == null && context instanceof ListSelectionBottomSheetFragmentListener) {
                mListener = (ListSelectionBottomSheetFragmentListener) context;
            }
        } catch (ClassCastException e) {
            mListener = null;
        }
    }

    @Override
    public void onOptionSelected(BottomSheetOption option) {
        if (mListener != null) {
            mListener.onBottomSheetOptionSelected(option);
        }
        dismiss();
    }

    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, ListSelectionBottomSheetFragment.class.getSimpleName());
    }

    public void setListener(ListSelectionBottomSheetFragmentListener listener) {
        mListener = listener;
    }


    private class ListSelectionBottomSheetAdapter extends RecyclerView.Adapter<BottomSheetListItemViewHolder> {

        private List<BottomSheetOption> mOptions;

        private ListSelectionBottomSheetAdapter(List<BottomSheetOption> options) {
            mOptions = options;
        }

        @Override
        public BottomSheetListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new BottomSheetListItemViewHolder(
                    inflater.inflate(BottomSheetListItemViewHolder.LAYOUT_ID, parent, false),
                    ListSelectionBottomSheetFragment.this);
        }

        @Override
        public void onBindViewHolder(BottomSheetListItemViewHolder holder, int position) {
            holder.setOption(mOptions.get(position));
        }

        @Override
        public int getItemCount() {
            return mOptions.size();
        }
    }

    private int getLayoutId() {
        return R.layout.list_selection_bottom_sheet_fragment;
    }
}
