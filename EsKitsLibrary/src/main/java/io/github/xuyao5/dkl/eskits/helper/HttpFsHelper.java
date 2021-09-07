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
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
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
    public HomeDirectory getHomeDirectory(@NonNull String token) {
        String url = String.format("http://%s:%d/webhdfs/v1/?user.name=%s&op=GETHOMEDIRECTORY", host, port, user);
        try (Response response = httpClient.newCall(new Request.Builder().header("token", token).url(url).build()).execute()) {
            if (response.isSuccessful() && Objects.nonNull(response.body())) {
                return GsonUtilsPlus.json2Obj(response.body().string(), TypeToken.get(HomeDirectory.class));
            }
        }
        return HomeDirectory.of();
    }

    public HomeDirectory getHomeDirectory() {
        return getHomeDirectory(StringUtils.EMPTY);
    }

    @SneakyThrows
    public FileStatuses listStatus(@NonNull String path, @NonNull String token) {
        String url = String.format("http://localhost:14000/webhdfs/v1/user/root?user.name=root&op=LISTSTATUS", host, port, path);
        try (Response response = httpClient.newCall(new Request.Builder().header("", token).url(url).build()).execute()) {
        }
        return FileStatuses.of();
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
    }

    @Data(staticConstructor = "of")
    public static class FileStatuses implements Serializable {
    }

    @Data(staticConstructor = "of")
    public static class HomeDirectory implements Serializable {

        @SerializedName(value = "Path", alternate = {"path"})
        private String path;
    }
}