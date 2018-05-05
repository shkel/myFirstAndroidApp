package com.example.galleryn.galleryn;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.webkit.URLUtil;

import com.example.galleryn.galleryn.disk.DiskUtils;
import com.example.galleryn.galleryn.disk.FilesResourceList;
import com.example.galleryn.galleryn.disk.Link;
import com.example.galleryn.galleryn.disk.Resource;
import com.example.galleryn.galleryn.utils.GalleryImage;
import com.example.galleryn.galleryn.utils.RequestHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImagePreviewLoader {
    private LoaderCallback onLoaded;
    private LoaderError onError;
    private Resources resources;
    private RequestHelper requestHelper;

    public ImagePreviewLoader(Resources resources, RequestHelper requestHelper, @NonNull LoaderCallback onLoaded, @NonNull LoaderError onError) {
        this.onLoaded = onLoaded;
        this.onError = onError;
        this.resources = resources;
        this.requestHelper = requestHelper;
    }

    public interface LoaderCallback {
        void exec(@Nullable GalleryImage image);
    }

    public interface LoaderError {
        void exec(@Nullable String message);
    }

    public void loadPreviewPage(int offset, final int limit) {
        Callback<FilesResourceList> callback = new Callback<FilesResourceList>() {
            @Override
            public void onResponse(@NonNull Call<FilesResourceList> call, @NonNull Response<FilesResourceList> response) {
                if (response.isSuccessful()) {
                    addImages(response.body(), limit);
                } else {
                    raiseError(resources.getString(R.string.filesListError), limit);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FilesResourceList> call, @NonNull Throwable t) {
                raiseError(resources.getString(R.string.filesListFail), limit);
                t.printStackTrace();
            }
        };

        requestHelper.getRetrofitFiles().create(DiskUtils.DiskFilesApi.class)
                .filesList(offset, limit)
                .enqueue(callback);
    }

    private void loadPreview(@NonNull final Resource resource) {
        Callback<Link> callback = new Callback<Link>() {
            @Override
            public void onResponse(@NonNull Call<Link> call, @NonNull Response<Link> response) {
                if (response.isSuccessful()) {
                    addImage(response.body(), resource);
                } else {
                    raiseError(resources.getString(R.string.imageError), 1);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Link> call, @NonNull Throwable t) {
                raiseError(resources.getString(R.string.imageFail), 1);
                t.printStackTrace();
            }
        };

        requestHelper.getImageApi().link(resource.getPath()).enqueue(callback);
    }

    private void addImage(final Link link, @NonNull final Resource resource) {
        if (link != null) {
            final String previewUrl = resource.getPreview();
            final String fullUrl = link.getHref();
            if (URLUtil.isValidUrl(fullUrl) && (previewUrl == null || URLUtil.isValidUrl(previewUrl))) {
                onLoaded.exec(new GalleryImage(resource.getName(), fullUrl, previewUrl));
            } else {
                raiseError(resources.getString(R.string.imageFormat), 1);
            }
        } else {
            raiseError(resources.getString(R.string.imageNull), 1);
        }
    }

    private void addImages(final FilesResourceList result, int requestedFilesCount) {
        if (result != null) {
            List<Resource> files = result.getItems();
            int receivedFilesCount = files.size() > requestedFilesCount ? requestedFilesCount : files.size();

            for (int i = 0; i < receivedFilesCount; i++) {
                Resource resource = files.get(i);
                if (resource != null) {
                    loadPreview(resource);
                } else {
                    raiseError(resources.getString(R.string.fileNull), 1);
                }
            }
            // не делаем никаких флагов, что файлов больше нет,
            // чтобы при многопользовательском режиме можно было читать новое
            raiseError(null, requestedFilesCount - receivedFilesCount);
        } else {
            raiseError(null, requestedFilesCount);
        }
    }

    private void raiseError(@Nullable String message, int countErrors) {
        if (message != null) onError.exec(message);
        for (int i = 0; i < countErrors; i++) onLoaded.exec(null);
    }

}
