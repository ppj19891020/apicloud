package com.fly.apicloud.core.redis;

import com.fly.apicloud.core.contants.APIContants;
import com.fly.apicloud.core.dto.APIDTO;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

/**
 * get redis client
 */
public class RedisClient implements FactoryBean<org.redisson.api.RedissonClient>,InitializingBean {

    private org.redisson.api.RedissonClient redisson;
    private String host = "localhost";
    private int port = 6379;
    private int database;
    private String password;
    private boolean isSingle;
    private String[] addresses;

    public org.redisson.api.RedissonClient getObject() throws Exception {
        return redisson;
    }

    public Class<?> getObjectType() {
        return org.redisson.api.RedissonClient.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public void afterPropertiesSet() throws Exception {
        Config config = new Config();
        if(isSingle){
            if(StringUtils.isEmpty(password)){
                config.useSingleServer().setAddress(host + ":" + port).setTimeout(60000);
            }else {
                config.useSingleServer().setAddress(host + ":" + port).setPassword(password).setTimeout(60000);
            }
        }else{
            config.useClusterServers().addNodeAddress(addresses);
        }
        redisson = Redisson.create(config);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public void setSingle(boolean single) {
        isSingle = single;
    }

    public String[] getAddresses() {
        return addresses;
    }

    public void setAddresses(String[] addresses) {
        this.addresses = addresses;
    }

    /**
     * 获取阻塞队列
     * @param serviceName 服务名
     * @return
     */
     public <V> RPriorityDeque<V> getRPriorityDeque(String serviceName){
         String key = String.format(APIContants.APIQUEUE,serviceName);
         RPriorityDeque rb=redisson.getPriorityDeque(key);
         return rb;
     }

    /**
     * 删除队列中的requestid
     * @param serviceName 服务名
     * @return
     */
    public APIDTO pollAPIDTO(String serviceName){
        String key = String.format(APIContants.APIQUEUE,serviceName);
        RPriorityDeque rb = redisson.getPriorityDeque(key);
        APIDTO apidto = (APIDTO)rb.poll();
        return apidto;
    }

}
