package com.graduation.hp.core.utils;

import com.graduation.hp.core.repository.http.bean.DataGrid;
import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.repository.http.bean.Result;
import com.graduation.hp.core.repository.http.exception.ApiException;

import java.util.List;

import io.reactivex.MaybeTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ning on 2018/11/15.
 */

public class RxUtils {

    private RxUtils() {
    }

    /**
     * 设置网络加载在异步线程，结果监听在主线程
     *
     * @param <T>
     * @return
     */
    public static <T> SingleTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> MaybeTransformer<T, T> maybeRxSchedulerHelper() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Function<String, Result> mappingResponseToResultWithNoException(final Class<T> clazz) {
        return s -> {
            Result result = Result.formatToPojo(s, clazz);
            return result;
        };
    }

    /**
     * 请求结果数据Json字符串 转成 基础网络请求类---Result
     *
     * @param clazz Result内部数据类型
     * @param <T>
     * @return
     */
    public static <T> Function<String, Result> mappingResponseToResult(final Class<T> clazz) {
        return s -> {
            LogUtils.d(s);
            Result result = Result.formatToPojo(s, clazz);
            if (result.getStatus() != 200) {
                throw new ApiException(result.getStatus(), result.getMsg());
            }
            return result;
        };
    }

    public static <T> Function<String, Result> mappingResponseToResultList(final Class<T> clazz) {
        return s -> {
            Result result = Result.formatToList(s, clazz);
            if (result.getStatus() != 200) {
                throw new ApiException(result.getStatus(), result.getMsg());
            }
            return result;
        };
    }

    public static <T> SingleTransformer<Result, List<T>> mappingResultToListQiang() {
        return upstream -> upstream.map((Function<Result, List<T>>) result -> {
            if (result.getData() != null) {
                List<T> data = (List<T>) result.getData();
                if (data.size() > 0) {
                    return data;
                } else {
                    throw new ApiException(ResponseCode.DATA_EMPTY);
                }
            }
            throw new ApiException(ResponseCode.DATA_EMPTY);
        });
    }

    public static <T> SingleTransformer<String, T> transformResultToData(final Class<T> clazz) {
        return upstream -> upstream.map((Function<String, T>) response -> {
            LogUtils.d(response);
            Result result = Result.formatToPojo(response, clazz);
            if (result.getStatus() != 200) {
                throw new ApiException(result.getStatus(), result.getMsg());
            }
            if (result.getData() != null) {
                return (T) result.getData();
            } else {
                throw new ApiException(ResponseCode.DATA_NULL);
            }
        });
    }

    public static <T> SingleTransformer<String, List<T>> transformResultToList(final Class<T> clazz) {
        return upstream -> upstream.map((Function<String, List<T>>) response -> {
            LogUtils.d(response);
            Result result = Result.formatToPojo(response, DataGrid.class);
            if (result.getStatus() != 200) {
                throw new ApiException(result.getStatus(), result.getMsg());
            }
            if (result.getData() != null) {
                DataGrid<T> dataGrid = (DataGrid<T>) result.getData();
                if (dataGrid.getRows() != null) {
                    if (dataGrid.getRows().size() > 0) {
                        return JsonUtils.jsonToList(dataGrid.getRows().toString(), clazz);
                    } else {
                        throw new ApiException(ResponseCode.DATA_EMPTY);
                    }
                }
            }
            throw new ApiException(ResponseCode.DATA_EMPTY);
        });
    }

    public static <T> SingleTransformer<Result, T> mappingResultToData() {
        return upstream -> upstream.map((Function<Result, T>) result -> {
            if (result.getData() != null) {
                return (T) result.getData();
            } else {
                throw new ApiException(ResponseCode.DATA_NULL);
            }
        });
    }

    public static SingleTransformer<Result, Boolean> mappingResultToCheck() {
        return upstream -> upstream.map((Function<Result, Boolean>) result -> {
            if (result.getStatus() == 200) {
                return Boolean.TRUE;
            } else {
                throw new ApiException(result.getStatus(), result.getMsg());
            }
        });
    }

    public static <T> SingleTransformer<Result, List<T>> mappingResultToList() {
        return upstream -> upstream.map((Function<Result, List<T>>) result -> {
            if (result.getData() != null) {
                DataGrid<T> dataGrid = (DataGrid<T>) result.getData();
                if (dataGrid.getRows() != null) {
                    if (dataGrid.getRows().size() > 0) {
                        return dataGrid.getRows();
                    } else {
                        throw new ApiException(ResponseCode.DATA_EMPTY);
                    }
                }
            }
            throw new ApiException(ResponseCode.DATA_EMPTY);
        });
    }
}
