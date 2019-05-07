package com.graduation.hp.repository.http.entity.wrapper;

import com.graduation.hp.repository.http.entity.vo.ArticleVO;

public class ArticleVOWrapper {

    private ArticleVO articleVO;
    private boolean isFocusOn;

    public ArticleVOWrapper(){
    }

    public ArticleVOWrapper(ArticleVO articleVO, boolean isFocusOn) {
        this.articleVO = articleVO;
        this.isFocusOn = isFocusOn;
    }

    public ArticleVO getArticleVO() {
        return articleVO;
    }

    public boolean isFocusOn() {
        return isFocusOn;
    }

    public void setArticleVO(ArticleVO articleVO) {
        this.articleVO = articleVO;
    }

    public void setFocusOn(boolean focusOn) {
        isFocusOn = focusOn;
    }
}
