package com.example.galleryn.galleryn.disk;

import io.reactivex.Observable;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class DiskUtils {
    // TOKEN должен получаться после авторизации. но ее нет
    private static final String TOKEN = "AQAAAAALKMKaAAT6rwmO3aGzh0WmrY1kh5hosbE";
    public static final String diskFilesUrl = "https://cloud-api.yandex.net/v1/disk/resources/files/";
    public static final String diskLinkUrl = "https://cloud-api.yandex.net/v1/disk/resources/download/";


    public interface DiskFilesApi {
        @Headers("Authorization: OAuth " + TOKEN)
        // не будем тянуть ненужную информацию, и сделаем всегда одинаковый порядок
        @GET("?media_type=image&preview_size=x150&sort=created&fields=name,preview,path")
        Call<FilesResourceList> filesList(@Query("offset") int offset, @Query("limit") int limit);
    }

    public interface DiskLinkApi {
        @Headers("Authorization: OAuth " + TOKEN)
        @GET(".")
        Call<Link> link(@Query("path") String path);
    }

    public static Request addHeaderToRequest(Request request) {
        return request.newBuilder()
                .addHeader("Authorization", "OAuth " + TOKEN)
                .build();
    }

    public interface DiskFilesCountApi {
        @Headers("Authorization: OAuth " + TOKEN)
        @GET("?media_type=image&fields=items.name")
        Observable<FilesResourceList> filesList(@Query("limit") int limit);
    }
}
