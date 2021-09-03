package io.github.xuyao5.dkl.eskits.helper;

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

    public void download() {
        httpClient.newCall(new Request.Builder().url("").build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {

            }
        });
    }
}
