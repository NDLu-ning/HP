package com.graduation.hp.repository.model.impl;

import com.graduation.hp.core.HPApplication;
import com.graduation.hp.R;
import com.graduation.hp.core.mvp.BaseModel;
import com.graduation.hp.core.repository.http.HttpHelper;
import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.repository.http.exception.ApiException;
import com.graduation.hp.core.utils.JsonUtils;
import com.graduation.hp.core.utils.RxUtils;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.http.entity.PostItem;
import com.graduation.hp.repository.http.service.PostService;
import com.graduation.hp.repository.model.IPostModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;

public class PostModel extends BaseModel
        implements IPostModel {


    private final RepositoryHelper mRepositoryHelper;

    @Inject
    public PostModel(RepositoryHelper repositoryHelper) {
        this.mRepositoryHelper = repositoryHelper;
    }

    @Override
    public Single<List<PostItem>> getPostListByUserId(long userId, int page, int limit) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return Single.create((SingleOnSubscribe<Map<String, Object>>) emitter -> {
            if (userId < 0) {
                emitter.onError(new ApiException(ResponseCode.ERROR.getStatus(),
                        HPApplication.getStringById(R.string.tips_lack_of_important_params)));
            }
            Map<String, Object> map = new HashMap<>();
            map.put("userId", userId);
            map.put("page", page);
            map.put("limit", limit);
            emitter.onSuccess(map);
        }).flatMap(params -> httpHelper.obtainRetrofitService(PostService.class)
                .getPostListByUserId(JsonUtils.mapToRequestBody(params))
                .compose(RxUtils.transformResultToList(PostItem.class))
                .compose(RxUtils.rxSchedulerHelper()));
    }

    public RepositoryHelper getRepositoryHelper() {
        return mRepositoryHelper;
    }
}
