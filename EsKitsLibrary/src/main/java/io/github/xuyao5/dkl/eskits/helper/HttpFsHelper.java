package io.github.xuyao5.dkl.eskits.helper;

import com.google.gson.reflect.TypeToken;
import io.github.xuyao5.dkl.eskits.schema.httpfs.ContentSummaries;
import io.github.xuyao5.dkl.eskits.schema.httpfs.FileStatuses2;
import io.github.xuyao5.dkl.eskits.schema.httpfs.HomeDirectory;
import io.github.xuyao5.dkl.eskits.schema.httpfs.ListStatus;
import io.github.xuyao5.dkl.eskits.util.GsonUtilsPlus;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 2/09/21 12:38
 * @apiNote HttpFsHelper
 * @implNote HttpFsHelper
 */
@Slf4j
public final class HttpFsHelper {

    private final OkHttpClient httpClient;
    private final String host;
    private final int port;
    private final String user;

    public HttpFsHelper(@NonNull String host, int port, @NonNull String user) {
        this.host = host;
        this.port = port;
        this.user = user;
        httpClient = new OkHttpClient().newBuilder()
                .retryOnConnectionFailure(true)
                .connectTimeout(1000, TimeUnit.SECONDS) //连接超时
                .readTimeout(1000, TimeUnit.SECONDS) //读取超时
                .writeTimeout(1000, TimeUnit.SECONDS) //写超时
                .build();
    }

    @SneakyThrows
    public void open(@NonNull String path) {
        String url = String.format("http://%s:%d/webhdfs/v1%s?user.name=%s&op=OPEN", host, port, getHomeDirectory().getPath(), path);
        log.info("open方法url=[{}]", url);
        try (Response response = httpClient.newCall(new Request.Builder().url(url).build()).execute()) {
        }
    }

    @SneakyThrows
    public FileStatuses2 getFileStatus() {
        String url = String.format("http://%s:%d/webhdfs/v1%s?user.name=%s&op=GETFILESTATUS", host, port, getHomeDirectory().getPath(), user);
        log.info("getFileStatus方法url=[{}]", url);
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
        log.info("listStatus方法url=[{}]", url);
        try (Response response = httpClient.newCall(new Request.Builder().url(url).build()).execute()) {
            if (response.isSuccessful() && Objects.nonNull(response.body())) {
                return GsonUtilsPlus.json2Obj(response.body().string(), TypeToken.get(ListStatus.class));
            }
        }
        return ListStatus.of();
    }

    @SneakyThrows
    public ListStatus listStatus(@NonNull String path) {
        String url = String.format("http://%s:%d/webhdfs/v1%s%s?user.name=%s&op=LISTSTATUS", host, port, getHomeDirectory().getPath(), path, user);
        log.info("listStatus方法url=[{}]", url);
        try (Response response = httpClient.newCall(new Request.Builder().url(url).build()).execute()) {
            if (response.isSuccessful() && Objects.nonNull(response.body())) {
                return GsonUtilsPlus.json2Obj(response.body().string(), TypeToken.get(ListStatus.class));
            }
        }
        return ListStatus.of();
    }

    @SneakyThrows
    public ContentSummaries getContentSummary() {
        String url = String.format("http://%s:%d/webhdfs/v1%s?user.name=%s&op=GETCONTENTSUMMARY", host, port, getHomeDirectory().getPath(), user);
        log.info("getContentSummary方法url=[{}]", url);
        try (Response response = httpClient.newCall(new Request.Builder().url(url).build()).execute()) {
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
        log.info("getHomeDirectory方法url=[{}]", url);
        try (Response response = httpClient.newCall(new Request.Builder().url(url).build()).execute()) {
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

    @SneakyThrows
    public void create(@NonNull String path) {
        String url = String.format("http://%s:%d/webhdfs/v1%s%s?user.name=%s&op=CREATE&&overwrite=true&&permission=777", host, port, getHomeDirectory().getPath(), path, user);
        log.info("create方法url=[{}]", url);
        try (Response response = httpClient.newCall(new Request.Builder().url(url).put(RequestBody.create(null, new byte[]{})).build()).execute()) {
            String location = response.header("Location");
            log.info("location方法url=[{}]", location);
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", "INT_DISRUPTOR_1K_T_20200711_00.txt",
                            RequestBody.create(MediaType.parse("multipart/form-data"), Paths.get("/Users/xuyao/Downloads/INT_DISRUPTOR_1K_T_20200711_00.txt").toFile()))
                    .build();
            //curl -i -X PUT -T /Users/xuyao/Downloads/INT_DISRUPTOR_1K_T_20200711_00.txt "http://localhost:14000/webhdfs/v1/user/root/dir1/INT_DISRUPTOR_1K_T_20200711_00.txt?op=CREATE&user.name=root&overwrite=true&data=true&permission=777" -H "Content-Type:application/octet-stream"
            try (Response response2 = httpClient.newCall(new Request.Builder().url(location).put(requestBody).build()).execute()) {
                System.out.println(response2.code());
                System.out.println(response2.headers());
            }
        }
    }

    @SneakyThrows
    public void mkdirs(@NonNull String path) {
        String url = String.format("http://%s:%d/webhdfs/v1%s%s?user.name=%s&op=MKDIRS&permission=777", host, port, getHomeDirectory().getPath(), path, user);
        log.info("mkdirs方法url=[{}]", url);
        try (Response response = httpClient.newCall(new Request.Builder().url(url).put(RequestBody.create(null, new byte[]{})).build()).execute()) {
//            System.out.println(response.code());
//            System.out.println(response.body().string());
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