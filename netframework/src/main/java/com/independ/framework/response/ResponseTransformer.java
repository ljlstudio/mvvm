package com.independ.framework.response;

import android.util.Log;

import com.independ.framework.exception.ApiException;
import com.independ.framework.exception.CustomException;
import com.independ.framework.scheduler.RxScheduler;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;


/**
 * Created by 李嘉伦.
 * Date: 2020/5/14
 * Time: 下午 2:12
 * 分离器
 */
public class ResponseTransformer {

    /**
     * 转换本地异常、服务器异常、和抛出正常数据
     *
     * @param <T>
     * @return
     */

    public static <T> ObservableTransformer<BaseResponse<T>, T> handleResult() {
        return upstream -> upstream
                .compose(RxScheduler.getInstance().schedulers())
                .onErrorResumeNext(new ErrorResumeFunction<>())
                .flatMap(new ResponseFunction<>()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends BaseResponse<T>>> {

        @Override
        public ObservableSource<? extends BaseResponse<T>> apply(Throwable throwable) throws Exception {
            return Observable.error(CustomException.handleException(throwable));
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     *
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<BaseResponse<T>, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(BaseResponse<T> response) throws Exception {
            try {
                if (null != response && response.getSuccess()) {
                    return Observable.just(response.getData() == null ? ((T) new Object()) : response.getData());
                } else if (null != response && 401 == response.getErrCode()) {
                    return Observable.error(new ApiException(-2, response == null ? "need login first!" : response.getErrMsg()));
                } else {
                    return Observable.error(new ApiException(-1, response == null ? "Network related errors" : response.getErrMsg()));
                }
            } catch (
                    Exception e) {
                Log.e("error", e.getMessage());
                return Observable.error(new ApiException(-1, "Exception related error"));
            }
        }
    }
}
