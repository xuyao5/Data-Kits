package io.github.xuyao5.datakitsserver.system;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

public class DisruptorExample {

    public static void main(String[] args) throws InterruptedException {
        //使用disruptor
        int bufferSize = 1024;
        Disruptor<Event> disruptor = new Disruptor<>(Event::new, bufferSize, DaemonThreadFactory.INSTANCE);
        EventHandler<Event> eventHandler = (event, sequence, endOfBatch) -> {
            // 在这里处理事件
            Thread.sleep(2);
        };
        disruptor.handleEventsWith(eventHandler);
        RingBuffer<Event> ringBuffer = disruptor.start();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < bufferSize; i++) {
            long sequence = ringBuffer.next();
            Event event = ringBuffer.get(sequence);
            // 设置事件数据
            Thread.sleep(2);
            ringBuffer.publish(sequence);
        }
        disruptor.shutdown();
        long endTime = System.currentTimeMillis();
        System.out.println("Disruptor执行时间: " + (endTime - startTime) + "毫秒");

        //不使用disruptor
        Event[] events = new Event[bufferSize];
        startTime = System.currentTimeMillis();
        for (int i = 0; i < bufferSize; i++) {
            Event event = new Event();
            // 设置事件数据
            Thread.sleep(2);
            events[i] = event;
        }
        for (int i = 0; i < bufferSize; i++) {
            Event event = events[i];
            // 在这里处理事件
            Thread.sleep(2);
        }
        endTime = System.currentTimeMillis();
        System.out.println("非Disruptor执行时间: " + (endTime - startTime) + "毫秒");
    }

    static class Event {
        // 定义事件数据
    }
}

