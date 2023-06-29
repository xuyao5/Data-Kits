package io.github.xuyao5.datakitsserver.system;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.github.xuyao5.dkl.eskits.context.boost.DisruptorBoost;
import io.github.xuyao5.dkl.eskits.context.translator.ZeroArgEventTranslator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DisruptorExample {

    public static void main(String[] args) throws InterruptedException {
        //使用disruptor
        int bufferSize = 1024;
        Disruptor<Event> disruptor = new Disruptor<>(Event::new, bufferSize, DaemonThreadFactory.INSTANCE);
        EventHandler<Event> eventHandler = (event, sequence, endOfBatch) -> {
            // 在这里处理事件
            doSomething();
        };
        disruptor.handleEventsWith(eventHandler);
        RingBuffer<Event> ringBuffer = disruptor.start();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < bufferSize; i++) {
            long sequence = ringBuffer.next();
//            Event event = ringBuffer.get(sequence);
            // 设置事件数据
            doSomething();
            ringBuffer.publish(sequence);
        }
        disruptor.shutdown();
        long endTime = System.currentTimeMillis();
        System.out.println("传统Disruptor执行时间: " + (endTime - startTime) + "毫秒");

        //不使用disruptor
        Event[] events = new Event[bufferSize];
        startTime = System.currentTimeMillis();
        for (int i = 0; i < bufferSize; i++) {
            Event event = new Event();
            // 设置事件数据
            doSomething();
            events[i] = event;
        }
        for (int i = 0; i < bufferSize; i++) {
            // 在这里处理事件
            doSomething();
        }
        endTime = System.currentTimeMillis();
        System.out.println("非Disruptor执行时间: " + (endTime - startTime) + "毫秒");

        //改进型
        startTime = System.currentTimeMillis();
        ZeroArgEventTranslator<Event> translator = DisruptorBoost.<Event>context().create().createZeroArgEventTranslator(Event::new,
                //异常处理
                (document, sequence) -> log.info(""),
                //消费者
                (document, sequence, endOfBatch) -> doSomething());
        //生产者
        for (int i = 0; i < bufferSize; i++) {
            translator.translate(((document, sequence) -> {
                doSomething();
            }));
        }
        endTime = System.currentTimeMillis();
        System.out.println("改进为函数式编程执行时间: " + (endTime - startTime) + "毫秒");
    }

    static class Event {
        // 定义事件数据
    }

    @SneakyThrows
    static void doSomething() {
        Thread.sleep(1);
    }
}

