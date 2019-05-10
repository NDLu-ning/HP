package com.graduation.hp.repository.http.service;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 上传图片接口
 */
public interface UploadService {

    @Headers({"Domain-Name: UPLOAD_URL"})
    @POST("upload")
    @Multipart
    Single<String> uploadFile(@Part MultipartBody.Part file);
}
