package com.graduation.hp.ui.navigation.article.comment;

import com.graduation.hp.widget.dialog.CommentDialog;

public interface PrepareForDiscussionListener {

    void showCommentDialog(String hint, CommentDialog.CommentDialogClickListener listener);

    void dismissCommentDialog();
}
