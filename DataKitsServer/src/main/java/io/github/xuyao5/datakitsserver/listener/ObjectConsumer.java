package io.github.xuyao5.datakitsserver.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;

@Slf4j
@RocketMQMessageListener(topic = "${boot.rocketmq.topic}", consumerGroup = "string_consumer", selectorExpression = "${boot.rocketmq.tag}")
public class ObjectConsumer implements RocketMQListener<Object> {

    @Override
    public void onMessage(Object message) {
        log.info("Consumer received:{}", message);
    }
}
