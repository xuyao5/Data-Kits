package io.github.xuyao5.dkl.eskits.helper;

import com.google.gson.reflect.TypeToken;
import io.github.xuyao5.dkl.eskits.schema.httpfs.ContentSummaries;
import io.github.xuyao5.dkl.eskits.schema.httpfs.FileStatuses2;
import io.github.xuyao5.dkl.eskits.schema.httpfs.HomeDirectory;
import io.github.xuyao5.dkl.eskits.schema.httpfs.ListStatus;
import io.github.xuyao5.dkl.eskits.util.GsonUtilsPlus;
import lombok.NonNull;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Objects;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 2/09/21 12:38
 * @apiNote HttpFsHelper
 * @implNote HttpFsHelper
 */
public final class HttpFsHelper {

    private final OkHttpClient httpClient;
    private final String host;
    private final int port;
    private final String user;

    public HttpFsHelper(@NonNull String host, int port, @NonNull String user) {
        this.host = host;
        this.port = port;
        this.user = user;
        httpClient = new OkHttpClient();
    }

    @SneakyThrows
    public void open(@NonNull String path) {
        String url = String.format("http://%s:%d/webhdfs/v1%s?user.name=%s&op=OPEN", host, port, getHomeDirectory().getPath(), path);
        try (Response response = httpClient.newCall(new Request.Builder().url(url).build()).execute()) {
        }
    }

    @SneakyThrows
    public FileStatuses2 getFileStatus() {
        String url = String.format("http://%s:%d/webhdfs/v1%s?user.name=%s&op=GETFILESTATUS", host, port, getHomeDirectory().getPath(), user);
        try (Response response = httpClient.newCall(new Request.Builder().url(url).build()).execute()) {
            if (response.isSuccessful() && Objects.nonNull(response.body())) {
                return GsonUtilsPlus.json2Obj(response.body().string(), TypeToken.get(FileStatuses2.class));
            }
        }
        return FileStatuses2.of();
    }

    @SneakyThrows
    public ListStatus listStatus() {
        String url = String.format("http://%s:%d/webhdfs/v1%s?user.name=%s&op=LISTSTATUS", host, port, getHomeDirectory().getPath(), user);
        try (Response response = httpClient.newCall(new Request.Builder().url(url).build()).execute()) {
            if (response.isSuccessful() && Objects.nonNull(response.body())) {
                return GsonUtilsPlus.json2Obj(response.body().string(), TypeToken.get(ListStatus.class));
            }
        }
        return ListStatus.of();
    }

    @SneakyThrows
    public ListStatus listStatus(@NonNull String token) {
        String url = String.format("http://%s:%d/webhdfs/v1%s?user.name=%s&op=LISTSTATUS", host, port, getHomeDirectory(token).getPath(), user);
        try (Response response = httpClient.newCall(new Request.Builder().header("token", token).url(url).build()).execute()) {
            if (response.isSuccessful() && Objects.nonNull(response.body())) {
                return GsonUtilsPlus.json2Obj(response.body().string(), TypeToken.get(ListStatus.class));
            }
        }
        return ListStatus.of();
    }

    @SneakyThrows
    public ContentSummaries getContentSummary() {
        String url = String.format("http://%s:%d/webhdfs/v1%s?user.name=%s&op=GETCONTENTSUMMARY", host, port, getHomeDirectory().getPath(), user);
        try (Response response = httpClient.newCall(new Request.Builder().url(url).build()).execute()) {
            if (response.isSuccessful() && Objects.nonNull(response.body())) {
                return GsonUtilsPlus.json2Obj(response.body().string(), TypeToken.get(ContentSummaries.class));
            }
        }
        return ContentSummaries.of();
    }

    @SneakyThrows
    public ContentSummaries getContentSummary(@NonNull String token) {
        String url = String.format("http://%s:%d/webhdfs/v1%s?user.name=%s&op=GETCONTENTSUMMARY", host, port, getHomeDirectory().getPath(), user);
        try (Response response = httpClient.newCall(new Request.Builder().header("token", token).url(url).build()).execute()) {
            if (response.isSuccessful() && Objects.nonNull(response.body())) {
                return GsonUtilsPlus.json2Obj(response.body().string(), TypeToken.get(ContentSummaries.class));
            }
        }
        return ContentSummaries.of();
    }

    public void getFileChecksum() {
    }

    @SneakyThrows
    public HomeDirectory getHomeDirectory() {
        String url = String.format("http://%s:%d/webhdfs/v1/?user.name=%s&op=GETHOMEDIRECTORY", host, port, user);
        try (Response response = httpClient.newCall(new Request.Builder().url(url).build()).execute()) {
            if (response.isSuccessful() && Objects.nonNull(response.body())) {
                return GsonUtilsPlus.json2Obj(response.body().string(), TypeToken.get(HomeDirectory.class));
            }
        }
        return HomeDirectory.of();
    }

    @SneakyThrows
    public HomeDirectory getHomeDirectory(@NonNull String token) {
        String url = String.format("http://%s:%d/webhdfs/v1/?user.name=%s&op=GETHOMEDIRECTORY", host, port, user);
        try (Response response = httpClient.newCall(new Request.Builder().header("token", token).url(url).build()).execute()) {
            if (response.isSuccessful() && Objects.nonNull(response.body())) {
                return GsonUtilsPlus.json2Obj(response.body().string(), TypeToken.get(HomeDirectory.class));
            }
        }
        return HomeDirectory.of();
    }

    public void getDelegationToken() {
    }

    public void getDelegationTokens() {
    }

    public void create() {
    }

    @SneakyThrows
    public void mkdirs(@NonNull String path) {
        String url = String.format("http://%s:%d/webhdfs/v1%s?user.name=%s&op=MKDIRS&permission=777", host, port, getHomeDirectory().getPath(), path);
        try (Response response = httpClient.newCall(new Request.Builder().url(url).build()).execute()) {
            System.out.println(response.code());
            System.out.println(response.body());
        }
    }

    public void createSymlink() {
    }

    public void rename() {
    }

    public void setReplication() {
    }

    public void setOwner() {
    }

    public void setPermission() {
    }

    public void setTimes() {
    }

    public void renewDelegationToken() {
    }

    public void cancelDelegationToken() {
    }

    public void append() {
    }

    public void concat() {
    }

    public void delete() {
    }
}