package com.fly.apicloud.core.listener;

import com.alibaba.fastjson.JSON;
import com.fly.apicloud.core.dto.APIDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * spring 监听器
 * 观察者模式
 */
@Component
public class EventListener implements ApplicationListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(EventListener.class);

    public void onApplicationEvent(ApplicationEvent event) {
        if( event instanceof APIDTO){
            APIDTO apidto = (APIDTO)event ;
            LOGGER.info("APIDTO事件监听器-消息来了:{}", JSON.toJSONString(apidto));
            //此处调用业务方通知有新任务了...
        }
    }
}
