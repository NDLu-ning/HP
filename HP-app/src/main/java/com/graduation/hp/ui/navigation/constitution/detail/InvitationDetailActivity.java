package com.graduation.hp.ui.navigation.constitution.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.app.event.TokenInvalidEvent;
import com.graduation.hp.core.ui.SingleFragmentActivity;
import com.graduation.hp.ui.auth.AuthActivity;
import com.graduation.hp.ui.navigation.article.comment.PrepareForDiscussionListener;
import com.graduation.hp.widget.dialog.CommentDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class InvitationDetailActivity extends SingleFragmentActivity
        implements PrepareForDiscussionListener {

    private CommentDialog mCommentDialog;

    public static Intent createIntent(Context context, long newsId) {
        Intent intent = new Intent(context, InvitationDetailActivity.class);
        intent.putExtra(Key.NEWS_ID, newsId);
        return intent;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        findViewById(R.id.toolbar).setVisibility(View.GONE);
    }

    @Override
    protected Fragment createMainContentFragment() {
        Intent intent = getIntent();
        long newsId = intent.getLongExtra(Key.NEWS_ID, 0L);
        return InvitationDetailFragment.newInstance(newsId);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 2)
    @Override
    public void skipToLoginPage(TokenInvalidEvent event) {
        EventBus.getDefault().cancelEventDelivery(event);
        runOnUiThread(() -> startActivity(AuthActivity.createIntent(this)));
    }

    @Override
    public void showCommentDialog(String hint, CommentDialog.CommentDialogClickListener listener) {
        if (mCommentDialog == null || !mCommentDialog.isShowing()) {
            mCommentDialog = new CommentDialog(hint, listener);
            mCommentDialog.show(getSupportFragmentManager(), "CommentDialog");
        }
    }

    @Override
    public void dismissCommentDialog() {
        if (mCommentDialog != null && mCommentDialog.isShowing()) {
            mCommentDialog.dismiss();
            getSupportFragmentManager().beginTransaction().remove(mCommentDialog).commit();
            mCommentDialog = null;
        }
    }

    @Override
    public void onToolbarLeftClickListener(View v) {
        dismissCommentDialog();
        finish();
    }

    @Override
    public boolean useEventBus() {
        return true;
    }
}
