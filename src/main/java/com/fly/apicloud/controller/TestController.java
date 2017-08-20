package com.fly.apicloud.controller;

import com.alibaba.fastjson.JSON;
import com.fly.apicloud.core.context.ApplicationContextHelper;
import com.fly.apicloud.core.dto.APIDTO;
import com.fly.apicloud.core.redis.RedisClient;
import org.redisson.api.RPriorityDeque;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

@Controller
@RequestMapping("/")
public class TestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private RedisClient redisClient;

    /**
     * 模拟并发生产者过来
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/producter",method = RequestMethod.POST)
    public String producter(String serviceName){
        //针对每个服务都需要指定服务名-service1是举例某一个服务请求并发控制容器
        try{
            RPriorityDeque<APIDTO> queue = redisClient.getRPriorityDeque(serviceName);
            if(queue.size() > 10){
                LOGGER.error("服务超限");
                return "service limit";
            }
            Random random = new Random();
            APIDTO apiDto = new APIDTO(serviceName,random.nextInt(9));
            LOGGER.info("入队列:{}",apiDto.toString());
            queue.offer(apiDto);
            //消息通知-通知业务方消费
            ApplicationContextHelper.getContext().publishEvent(apiDto);
        }catch (Exception ex){
            LOGGER.error("error:",ex);
        }
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "/customer",method = RequestMethod.POST)
    public String customer(String serviceName){
        try{
            RPriorityDeque<APIDTO> queue = redisClient.getRPriorityDeque(serviceName);
            if(null==queue || queue.size() == 0){
                return "queue size is null";
            }
            APIDTO apidto = redisClient.pollAPIDTO(serviceName);
            LOGGER.info("出队:{}",apidto.toString());
            return JSON.toJSONString(apidto);
        }catch (Exception ex){
            LOGGER.error("error:",ex);
        }
        return "success";
    }

}
