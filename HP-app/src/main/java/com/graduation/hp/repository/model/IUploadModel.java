package com.graduation.hp.repository.model;

import com.graduation.hp.app.event.UploadProfileEvent;

import java.io.File;

import io.reactivex.Single;

public interface IUploadModel {

    /**
     * 200 time：79ms
     * {
     *     "status":200,
     *     "msg":"图片保存成功",
     *     "info":null,
     *     "data":"http://39.106.49.168:8080/-8931885283306bc7a.jpg"
     * }
     * @param file
     * @return
     */
    Single<UploadProfileEvent> uploadFile(File file);

}
