package io.github.xuyao5.dkl.eskits.helper;

import lombok.Data;
import lombok.NonNull;
import okhttp3.*;

import java.io.IOException;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 2/09/21 12:38
 * @apiNote HttpFsHelper
 * @implNote HttpFsHelper
 */
public final class HttpFsHelper {

    private final OkHttpClient httpClient;

    public HttpFsHelper() {
        httpClient = new OkHttpClient();
    }

    public FileStatus getFileStatus(@NonNull String host, int port, @NonNull String path) {
        String url = String.format("http://%s:%d/webhdfs/v1/%s?op=GETFILESTATUS", host, port, path);
        httpClient.newCall(new Request.Builder().url(url).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {

            }
        });
        return FileStatus.of();
    }

    public FileStatuses listStatus(@NonNull String host, int port, @NonNull String path) {
        String url = String.format("http://%s:%d/webhdfs/v1/%s?op=LISTSTATUS", host, port, path);
        httpClient.newCall(new Request.Builder().url(url).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {

            }
        });
        return FileStatuses.of();
    }

    public void openRead(@NonNull String host, int port, @NonNull String path) {
        String url = String.format("http://%s:%d/webhdfs/v1/%s?op=OPEN", host, port, path);
        httpClient.newCall(new Request.Builder().url(url).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {

            }
        });
    }

    public void getHomeDirectory(@NonNull String host, int port) {
        String url = String.format("http://%s:%d/webhdfs/v1/?op=GETHOMEDIRECTORY", host, port);
        httpClient.newCall(new Request.Builder().url(url).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {

            }
        });
    }

    @Data(staticConstructor = "of")
    public static class FileStatus {
    }

    @Data(staticConstructor = "of")
    public static class FileStatuses {
    }
}