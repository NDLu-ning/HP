package com.graduation.hp.repository.http.entity;

import java.util.Date;
import java.util.List;

public class PostItem {

    private String author;
    private String authorIcon;
    private String constitution;
    private String content;
    private String images;
    private int likeCount;
    private int views;
    private Date created;
    private List<CommentItem> commentItems;

    public PostItem() {
    }

    public PostItem(String author, String authorIcon, String constitution, String content, String images, int likeCount, int views, Date created, List<CommentItem> commentItems) {
        this.author = author;
        this.authorIcon = authorIcon;
        this.constitution = constitution;
        this.content = content;
        this.images = images;
        this.likeCount = likeCount;
        this.views = views;
        this.commentItems = commentItems;
        this.created = created;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorIcon() {
        return authorIcon;
    }

    public void setAuthorIcon(String authorIcon) {
        this.authorIcon = authorIcon;
    }

    public String getContent() {
        return content;
    }

    public String getConstitution() {
        return constitution;
    }

    public void setConstitution(String constitution) {
        this.constitution = constitution;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<CommentItem> getCommentItems() {
        return commentItems;
    }

    public void setCommentItems(List<CommentItem> commentItems) {
        this.commentItems = commentItems;
    }
}
