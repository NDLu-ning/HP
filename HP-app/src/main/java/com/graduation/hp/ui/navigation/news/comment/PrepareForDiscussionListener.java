package com.graduation.hp.ui.navigation.news.comment;

import com.graduation.hp.widget.dialog.CommentDialog;

public interface PrepareForDiscussionListener {

    void showCommentDialog(String hint, CommentDialog.CommentDialogClickListener listener);

    void dismissCommentDialog();
}
