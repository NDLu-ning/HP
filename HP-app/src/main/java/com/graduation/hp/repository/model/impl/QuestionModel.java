package com.graduation.hp.repository.model.impl;

import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.mvp.BaseModel;
import com.graduation.hp.core.repository.http.HttpHelper;
import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.repository.http.exception.ApiException;
import com.graduation.hp.core.utils.JsonUtils;
import com.graduation.hp.core.utils.RxUtils;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.http.entity.pojo.AnswerPO;
import com.graduation.hp.repository.http.entity.pojo.PhysiquePO;
import com.graduation.hp.repository.http.entity.vo.QuestionVO;
import com.graduation.hp.repository.http.entity.wrapper.QuestionVOWrapper;
import com.graduation.hp.repository.http.service.QuestionService;
import com.graduation.hp.repository.model.IQuestionModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class QuestionModel extends BaseModel
        implements IQuestionModel {

    private final RepositoryHelper mRepositoryHelper;

    @Inject
    public QuestionModel(RepositoryHelper repositoryHelper) {
        this.mRepositoryHelper = repositoryHelper;
    }

    @Override
    public Single<ArrayList<QuestionVOWrapper>> getAllQuestions(int type) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return Single.<Map<String, Object>>create(emitter -> {
            Map<String, Object> map = new HashMap<>();
            map.put(Key.TYPE, type);
            emitter.onSuccess(map);
        }).flatMap(params -> httpHelper.obtainRetrofitService(QuestionService.class)
                .getAllQuestion(type)
                .map(RxUtils.mappingResponseToResultList(QuestionVO.class))
                .map(result -> {
                    List<QuestionVO> data = (List<QuestionVO>) result.getData();
                    if (data != null && data.size() > 0) {
                        return data;
                    }
                    throw new ApiException(ResponseCode.DATA_EMPTY);
                })
                .map(questionVOS -> {
                    ArrayList<QuestionVOWrapper> wrappers = new ArrayList<>();
                    for (int i = 0; i < questionVOS.size(); i++) {
                        QuestionVO questionVO = questionVOS.get(i);
                        if (i == 0) {
                            wrappers.add(new QuestionVOWrapper(i + 1, questionVO, null, true, true));
                        } else {
                            wrappers.add(new QuestionVOWrapper(i + 1, questionVO));
                        }
                    }
                    return wrappers;
                })
                .compose(RxUtils.rxSchedulerHelper()));
    }

    @Override
    public Single<PhysiquePO> commit(List<AnswerPO> list) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return Single.<List<AnswerPO>>create(emitter -> {
            if (list == null || list.size() == 0) {
                emitter.onError(new ApiException(ResponseCode.ILLEGAL_ARGUMENT));
            }
//            Map<String, Object> map = new HashMap<>();
//            map.put(Key.ALIST, list);
            emitter.onSuccess(list);
        }).flatMap(params -> httpHelper.obtainRetrofitService(QuestionService.class)
                .commit(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                        JsonUtils.objectToJson(params)))
                .compose(RxUtils.transformResultToData(PhysiquePO.class))
                .compose(RxUtils.rxSchedulerHelper()));
    }

    public RepositoryHelper getRepositoryHelper() {
        return mRepositoryHelper;
    }
}
