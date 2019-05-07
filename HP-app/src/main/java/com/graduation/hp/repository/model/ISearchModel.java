package com.graduation.hp.repository.model;

import com.graduation.hp.core.repository.http.bean.Result;
import com.graduation.hp.repository.http.entity.vo.ArticleVO;
import com.graduation.hp.repository.http.entity.pojo.SearchKeyword;

import java.util.List;

import io.reactivex.Single;

public interface ISearchModel {

    Single<Result> saveSearchKeyword(String keyword);

    Single<List<SearchKeyword>> getSearchKeywords();

    Single<List<ArticleVO>> searchKeywords(String keyword, int offset, int limit);
}
