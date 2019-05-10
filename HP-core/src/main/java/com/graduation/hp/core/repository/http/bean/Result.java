package com.graduation.hp.core.repository.http.bean;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.graduation.hp.core.utils.CommonUtils;
import com.graduation.hp.core.utils.LogUtils;

/**
 * 响应序列化类
 */
public class Result {

    /**
     * 响应业务状态
     */
    private Integer status;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 暂留
     */
    private String info;

    /**
     * 响应中的数据
     */
    private Object data;


    public Result() {
    }

    private Result(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private Result(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public static Result build(Integer status, String msg) {
        return new Result(status, msg, null);
    }

    public static Result build(Integer status, String msg, Object data) {
        return new Result(status, msg, data);
    }

    public static Result build(ResponseCode code) {
        return build(code, null);
    }

    public static Result build(ResponseCode code, Object data) {
        return new Result(code.getStatus(), code.getMsg(), data);
    }

    public static Result ok(Object data) {
        return new Result(data);
    }

    public static Result error() {
        return new Result(500, "操作失败", "");
    }

    public static Result ok() {
        return new Result(null);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 将json结果集转化为ResponseResult对象
     *
     * @param jsonData json数据
     * @param clazz    ResponseResult中的object类型
     * @return
     */
    public static Result formatToPojo(String jsonData, Class<?> clazz) {
        try {
            Result result = JSON.parseObject(jsonData, new
                    TypeReference<Result>() {
                    });
            if (result.getData() != null && !TextUtils.isEmpty(result.getData().toString()) && !CommonUtils.isBaseType(clazz))
                result.setData(JSON.parseObject(result.getData().toString(), clazz));
            return result;
        } catch (Exception e) {
            return Result.build(0000, "类型转换错误：" + e.toString());
        }
    }

    /**
     * 没有object对象的转化
     *
     * @param json
     * @return
     */
    public static Result format(String json) {
        try {
            return JSON.parseObject(json, Result.class);
        } catch (Exception e) {
            LogUtils.d(e.toString());
            return Result.build(0000, "类型转换错误：" + e.toString());
        }
    }

    /**
     * Object是集合转化
     *
     * @param jsonData json数据
     * @param clazz    集合中的类型
     * @return
     */
    public static Result formatToList(String jsonData, Class<?> clazz) {
        try {
            Result result = JSON.parseObject(jsonData, new
                    TypeReference<Result>() {
                    });
            if (result.getData() != null && !TextUtils.isEmpty(result.getData().toString())) {
                result.setData(JSON.parseArray(result.getData().toString(), clazz));
            }
            return result;
        } catch (Exception e) {
            LogUtils.d("类型转换错误：" + e.toString());
            return Result.build(0000, "类型转换错误：" + e.toString());
        }
    }

    public static String toJsonString(Result result) {
        return JSON.toJSONString(result);
    }

}
