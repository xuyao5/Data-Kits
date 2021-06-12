package io.github.xuyao5.datakitsserver.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;

@Slf4j
@RocketMQMessageListener(topic = "${boot.rocketmq.topic}", consumerGroup = "string_consumer", selectorExpression = "${boot.rocketmq.tag}")
public class StringConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        // 重写消息处理方法
        log.info("------- StringConsumer received:{} \n", message);
        // TODO 对消息进行处理，比如写入数据
    }
}
