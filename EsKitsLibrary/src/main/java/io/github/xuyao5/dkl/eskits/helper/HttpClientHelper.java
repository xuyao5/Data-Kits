package io.github.xuyao5.dkl.eskits.helper;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 22/06/21 22:36
 * @apiNote HttpClientHelper
 * @implNote HttpClientHelper
 */
public final class HttpClientHelper {

    @SneakyThrows
    public void get(@NonNull String uri) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpclient.execute(new HttpGet(uri))) {
                System.out.println(response.getStatusLine());
                HttpEntity entity1 = response.getEntity();
                EntityUtils.consume(entity1);
            }
        }
    }
}