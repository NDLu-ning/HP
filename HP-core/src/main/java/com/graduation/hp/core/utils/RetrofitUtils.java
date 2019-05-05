package com.graduation.hp.core.utils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RetrofitUtils {

    private RetrofitUtils() {
    }

    public static MultipartBody.Part createMultipart(String fieldName, File file) {
        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part body =
                MultipartBody.Part.createFormData(fieldName, file.getName().indexOf(".") < 0 ? "temp.jpg" : file.getName(), requestFile);
        return body;
    }
}
