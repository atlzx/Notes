package com.springboot.example.eventandlistener.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class EventListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("事件------------------------------:"+event);
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
