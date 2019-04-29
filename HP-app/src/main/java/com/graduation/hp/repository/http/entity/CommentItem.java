package com.graduation.hp.repository.http.entity;

import java.util.Date;

public class CommentItem {

    private String commentUserId;
    private String commentUserName;
    private String replyUserId;
    private String replyUserName;
    private String content;
    private Date created;

    public CommentItem() {
    }

    public CommentItem(String commentUserId, String commentUserName, String replyUserId, String replyUserName, String content, Date created) {
        this.commentUserId = commentUserId;
        this.commentUserName = commentUserName;
        this.replyUserId = replyUserId;
        this.replyUserName = replyUserName;
        this.content = content;
        this.created = created;
    }

    public String getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(String commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getCommentUserName() {
        return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }

    public String getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(String replyUserId) {
        this.replyUserId = replyUserId;
    }

    public String getReplyUserName() {
        return replyUserName;
    }

    public void setReplyUserName(String replyUserName) {
        this.replyUserName = replyUserName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
