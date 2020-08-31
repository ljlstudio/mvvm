package com.independ.framework.client;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @Package: com.independ.framework
 * @ClassName: RetrofitClient
 * @Description: Retrofit Client 封装单例类
 * @Author: 李嘉伦
 * @CreateDate: 2020/5/13 23:44
 * @Email: 86152
 */
public class RetrofitClient {

    private static volatile RetrofitClient retrofitClient;
    private static final int DEFAULT_TIME_OUT = 15;

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private String url;


    public void initRetrofit(String url) {
        this.url = url;
    }

    /**
     * retrofit 初始化build
     */
    private RetrofitClient() {
    }

    /**
     * 单例
     *
     * @return
     */
    public static synchronized RetrofitClient getInstance() {
        if (retrofitClient == null) {
            synchronized (RetrofitClient.class) {
                retrofitClient = new RetrofitClient();
                return retrofitClient;
            }
        }
        return retrofitClient;
    }

    /**
     * 创建httpClient
     * 支持https
     *
     * @return
     */
    private OkHttpClient createOkHttpClient() {
        //设置请求头拦截器
        //设置日志拦截器
        if (null == okHttpClient) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT);
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


            //根据需求添加不同的拦截器
            okHttpClient = new OkHttpClient
                    .Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                    .connectionPool(new ConnectionPool(8, 10, TimeUnit.SECONDS))
                    .retryOnConnectionFailure(true)
                    .build();
        }
        return okHttpClient;
    }


    /**
     * Retrofit Client create
     */
    public <T> T create(Class<T> interfaceServer) {
        if (null == retrofit) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(createOkHttpClient())
                    .build();
        }
        if (interfaceServer == null) {
            throw new RuntimeException("The Api InterfaceServer is null!");
        }
        return retrofit.create(interfaceServer);
    }
}
