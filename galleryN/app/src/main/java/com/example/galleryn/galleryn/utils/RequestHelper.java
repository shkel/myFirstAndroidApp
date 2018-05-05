package com.example.galleryn.galleryn.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.galleryn.galleryn.disk.DiskUtils;
import com.google.gson.Gson;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestHelper {

    private final GsonConverterFactory gsonFactory = GsonConverterFactory.create(new Gson());
    private final OkHttpClient httpClient = new OkHttpClient();
    private Retrofit retrofitFiles = getRetrofitBuilder(DiskUtils.diskFilesUrl).build();
    private DiskUtils.DiskLinkApi imageApi = getRetrofitBuilder(DiskUtils.diskLinkUrl).build()
            .create(DiskUtils.DiskLinkApi.class);

    public void cancelAll() {
        httpClient.dispatcher().cancelAll();
    }

    public Retrofit getRetrofitFiles() {
        return retrofitFiles;
    }

    public DiskUtils.DiskLinkApi getImageApi() {
        return imageApi;
    }

    Retrofit getRetrofit(@NonNull String url, @NonNull RxJava2CallAdapterFactory adapterFactory) {
        return getRetrofitBuilder(url).addCallAdapterFactory(adapterFactory).build();
    }

    public Picasso getPicasso(Context context) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        Cache cache = new Cache(context.getCacheDir(), 1024 * 1024);
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                return chain.proceed(DiskUtils.addHeaderToRequest(chain.request()));
            }
        };
        // после добавления Header автоматическое кэширование перестает работать
        OkHttp3Downloader okHttp3Downloader = new OkHttp3Downloader(
            okHttpClientBuilder.addInterceptor(interceptor).cache(cache).build()
        );

        return new Picasso.Builder(context).downloader(okHttp3Downloader).build();
    }

    private Retrofit.Builder getRetrofitBuilder(@NonNull String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(httpClient)
                .addConverterFactory(gsonFactory);
    }
}
