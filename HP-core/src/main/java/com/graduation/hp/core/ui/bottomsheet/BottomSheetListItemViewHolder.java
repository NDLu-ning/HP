package com.graduation.hp.core.ui.bottomsheet;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.graduation.hp.core.R;
import com.graduation.hp.core.repository.entity.BottomSheetOption;

public class BottomSheetListItemViewHolder extends RecyclerView.ViewHolder {

    public static final int LAYOUT_ID = R.layout.list_selection_bottom_sheet_item;

    private TextView mOptionTextView;
    private ImageView mHighlightBadgeImageView;
    private BottomSheetOption mOption;
    private TextView mSecondaryTextView;

    public interface BottomSheetListItemViewHolderListener {
        void onOptionSelected(BottomSheetOption option);
    }

    public BottomSheetListItemViewHolder(View itemView,
                                         final BottomSheetListItemViewHolderListener listener) {
        super(itemView);
        mOptionTextView = (TextView) itemView.findViewById(R.id.text_view_option);
        mHighlightBadgeImageView = (ImageView) itemView.findViewById(R.id.imageview_highlight_badge);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOption != null) {
                    listener.onOptionSelected(mOption);
                }
            }
        });
        mSecondaryTextView = itemView.findViewById(R.id.text_view_secondary_text);
    }

    public void setOption(final BottomSheetOption option) {
        mOption = option;
        mOptionTextView.setText(option.getText());
        mHighlightBadgeImageView.setVisibility(option.shouldShowHighlightBadge() ? View.VISIBLE : View.GONE);
        final String secondaryText = option.getSecondaryText();
        if (secondaryText == null) {
            mSecondaryTextView.setVisibility(View.GONE);
        } else {
            mSecondaryTextView.setVisibility(View.VISIBLE);
            mSecondaryTextView.setText(secondaryText);
        }
    }
}
