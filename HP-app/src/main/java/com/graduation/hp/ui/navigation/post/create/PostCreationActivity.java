package com.graduation.hp.ui.navigation.post.create;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.app.di.component.DaggerActivityComponent;
import com.graduation.hp.app.di.module.ActivityModule;
import com.graduation.hp.core.HPApplication;
import com.graduation.hp.core.app.constant.Code;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.repository.entity.BottomSheetOption;
import com.graduation.hp.core.ui.BaseActivity;
import com.graduation.hp.core.ui.bottomsheet.ListSelectionBottomSheetFragment;
import com.graduation.hp.core.utils.DialogUtils;
import com.graduation.hp.core.utils.LogUtils;
import com.graduation.hp.core.utils.UploadAvatarHelper;
import com.graduation.hp.presenter.PostCreationPresenter;
import com.graduation.hp.repository.contact.PostCreationContact;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import am.widget.wraplayout.WrapLayout;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;


public class PostCreationActivity extends BaseActivity<PostCreationPresenter>
        implements PostCreationContact.View,
        ListSelectionBottomSheetFragment.ListSelectionBottomSheetFragmentListener, UploadAvatarHelper.UploadAvatarHelperListener {

    private static final int SHEET_ID_CHOOSE_FROM_GALLERY = 0;
    private static final int SHEET_ID_USE_CAMERA = 1;
    private static final int SHEET_ID_CANCEL = 2;

    private static final int MAX_CONTENT_LENGTH = 120;
    @BindView(R.id.post_picture_container_wl)
    WrapLayout mWrapLayout;

    @BindView(R.id.post_content_et)
    AppCompatEditText mPostContentEt;

    @BindView(R.id.toolbar_title)
    AppCompatTextView mToolbarTitle;

    @BindView(R.id.toolbar_right_tv)
    AppCompatTextView mToolbarRightTv;

    @BindView(R.id.post_content_il)
    TextInputLayout mPostContentIl;
    private ArrayList<BottomSheetOption> options;
    private UploadAvatarHelper mUploadAvatarHelper;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, PostCreationActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_post_creation;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mToolbarTitle.setText(getString(R.string.tips_create_post));
        mUploadAvatarHelper = new UploadAvatarHelper(this, this);
        if (savedInstanceState != null) {
            options = savedInstanceState.getParcelableArrayList(Key.BOTTOM_SHEET_OPTIONS);
            mUploadAvatarHelper.onRestoreInstanceState(savedInstanceState);
        } else {
            options = new ArrayList<>();
            options.add(new BottomSheetOption(SHEET_ID_CHOOSE_FROM_GALLERY,
                    HPApplication.getStringById(com.graduation.hp.core.R.string.tips_choose_gallery)));
            options.add(new BottomSheetOption(SHEET_ID_USE_CAMERA,
                    HPApplication.getStringById(com.graduation.hp.core.R.string.tips_choose_camera)));
            options.add(new BottomSheetOption(SHEET_ID_CANCEL,
                    HPApplication.getStringById(com.graduation.hp.core.R.string.tips_cancel)));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mUploadAvatarHelper.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Key.BOTTOM_SHEET_OPTIONS, options);
        super.onSaveInstanceState(outState);
    }

    @OnTextChanged(callback = OnTextChanged.Callback.TEXT_CHANGED, value = {R.id.post_content_et})
    public void onPostContentTextChanged(CharSequence s, int start, int before, int count) {
        mToolbarRightTv.setEnabled(s.length() > 0);
        if (s.length() > MAX_CONTENT_LENGTH) {
            mPostContentIl.setError(getString(R.string.tips_over_post_maximum));
        } else {
            mPostContentIl.setError(null);
        }
    }

    @OnClick(R.id.toolbar_left_btn)
    @Override
    public void onToolbarLeftClickListener(View v) {
        finish();
    }

    @OnClick(R.id.toolbar_right_tv)
    @Override
    public void onToolbarRightClickListener(View v) {

    }

    @OnClick(R.id.post_add_picture_ll)
    public void addPictureOptions() {
        DialogUtils.showListBottomSelectionOptionSheet(this, options, getString(R.string.tips_choose_method));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList != null && selectList.size() > 0) {
                        for (int i = 0; i < selectList.size(); i++) {
                            LogUtils.d(selectList.get(i).getCompressPath());
                        }
                    }
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
//                    adapter.setList(selectList);
//                    adapter.notifyDataSetChanged();
                    break;
                case Code.ATTACHMENT_NEW_PHOTO:
                case Code.ATTACHMENT_CROP_PHOTO:
                    mUploadAvatarHelper.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        }
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onBottomSheetOptionSelected(BottomSheetOption option) {
        switch (option.getId()) {
            case SHEET_ID_CHOOSE_FROM_GALLERY:
                PictureSelector.create(PostCreationActivity.this)
                        .openGallery(PictureMimeType.ofImage())
                        .theme(R.style.picture_default_style)
                        .minSelectNum(0)
                        .maxSelectNum(3)
                        .imageSpanCount(3)
                        .selectionMode(PictureConfig.MULTIPLE)
                        .previewImage(true)
                        .imageFormat(PictureMimeType.PNG)
                        .isZoomAnim(true)
                        .enableCrop(true)
                        .compress(true)
                        .withAspectRatio(1, 1)
                        .isGif(true)
                        .freeStyleCropEnabled(true)
                        .showCropGrid(true)
                        .scaleEnabled(true)
                        .isDragFrame(true)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case SHEET_ID_USE_CAMERA:
                mUploadAvatarHelper.onBottomSheetOptionSelected(option);
                break;
            case SHEET_ID_CANCEL:
                break;
        }
    }

    @Override
    public void onAvatarUpload(String largeFilePath, String smallFilePath) {

    }
}
