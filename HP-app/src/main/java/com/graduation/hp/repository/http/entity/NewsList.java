package com.graduation.hp.repository.http.entity;

import java.util.Date;

public class NewsList {

    private boolean isVideo;
    private String title;
    private String images;
    private Date date;
    private String author;
    private int comment;

    public NewsList() {
    }

    public NewsList(boolean isVideo, String title, String image, Date date, String author, int comment) {
        this.isVideo = isVideo;
        this.title = title;
        this.images = image;
        this.date = date;
        this.author = author;
        this.comment = comment;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String image) {
        this.images = image;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }
}
