package io.github.xuyao5.dal.eskitsserver.system;

import io.github.xuyao5.dal.eskitsserver.abstr.AbstractTest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;

@Slf4j
public class WebSocketTest extends AbstractTest {

    @SneakyThrows
    @Test
    void test() {
        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        StompSession session = stompClient.connect("ws://localhost:8080/gs-guide-websocket", new GDAXHandler()).get();
    }

    private class GDAXHandler extends WebSocketHttpHeaders implements StompSessionHandler {

        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            log.info("我Connected");
            String payload = "{\"type\": \"subscribe\",\"channels\":[{\"name\": \"ticker\",\"product_ids\": [\"ETH-EUR\"]}]\"}";
            log.info("Sending [" + payload + "]");
            session.send("/", payload);
        }

        @Override
        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
            log.error("Exception", exception);
        }

        @Override
        public void handleTransportError(StompSession session, Throwable exception) {
            log.error("Transport Error", exception);
        }

        @Override
        public Type getPayloadType(StompHeaders headers) {
            return String.class;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            log.info("Frame [" + payload + "]");
        }
    }
}
