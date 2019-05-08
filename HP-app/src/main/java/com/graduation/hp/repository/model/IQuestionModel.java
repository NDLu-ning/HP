package com.graduation.hp.repository.model;

import com.graduation.hp.repository.http.entity.pojo.AnswerPO;
import com.graduation.hp.repository.http.entity.pojo.PhysiquePO;
import com.graduation.hp.repository.http.entity.wrapper.QuestionVOWrapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public interface IQuestionModel {

    Single<ArrayList<QuestionVOWrapper>> getAllQuestions(int type);

    Single<PhysiquePO> commit(List<AnswerPO> list);

}
