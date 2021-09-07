package io.github.xuyao5.dkl.eskits.helper;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import io.github.xuyao5.dkl.eskits.util.GsonUtilsPlus;
import lombok.Data;
import lombok.NonNull;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.Serializable;
import java.util.List;
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
    public FileStatus getFileStatus(@NonNull String path, @NonNull String token) {
        String url = String.format("http://%s:%d/webhdfs/v1/%s?op=GETFILESTATUS", host, port, path);
        try (Response response = httpClient.newCall(new Request.Builder().header("", token).url(url).build()).execute()) {
        }
        return FileStatus.of();
    }

    @SneakyThrows
    public void openRead(@NonNull String path, @NonNull String token) {
        String url = String.format("http://%s:%d/webhdfs/v1/%s?op=OPEN", host, port, path);
        try (Response response = httpClient.newCall(new Request.Builder().header("", token).url(url).build()).execute()) {
        }
    }


    @Data(staticConstructor = "of")
    public static class FileStatus implements Serializable {

        @SerializedName(value = "pathSuffix", alternate = {"PathSuffix"})
        private String pathSuffix;

        @SerializedName(value = "type", alternate = {"Type"})
        private String type;

        @SerializedName(value = "length", alternate = {"Length"})
        private long length;

        @SerializedName(value = "owner", alternate = {"Owner"})
        private String owner;

        @SerializedName(value = "group", alternate = {"Group"})
        private String group;

        @SerializedName(value = "permission", alternate = {"Permission"})
        private String permission;

        @SerializedName(value = "accessTime", alternate = {"AccessTime"})
        private long accessTime;

        @SerializedName(value = "modificationTime", alternate = {"ModificationTime"})
        private long modificationTime;

        @SerializedName(value = "blockSize", alternate = {"BlockSize"})
        private long blockSize;

        @SerializedName(value = "replication", alternate = {"Replication"})
        private long replication;
    }

    @Data(staticConstructor = "of")
    public static class FileStatuses implements Serializable {

        @SerializedName(value = "fileStatus", alternate = {"FileStatus"})
        private List<FileStatus> fileStatusList;
    }

    @Data(staticConstructor = "of")
    public static class ListStatus implements Serializable {

        @SerializedName(value = "fileStatuses", alternate = {"FileStatuses"})
        private FileStatuses fileStatuses;
    }

    @Data(staticConstructor = "of")
    public static class HomeDirectory implements Serializable {

        @SerializedName(value = "path", alternate = {"Path"})
        private String path;
    }
}