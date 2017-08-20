package com.fly.apicloud.core.dto;

import com.fly.apicloud.core.utils.IDUtils;
import org.springframework.context.ApplicationEvent;

/**
 * api请求的dto
 */
public class APIDTO extends ApplicationEvent implements Comparable<APIDTO>{

    public APIDTO() {
        super("APISOURCE");
    }

    public APIDTO(String serviceName) {
        super("APISOURCE");
        this.requestId = IDUtils.getUUIDFromGenerators();
        this.serviceName = serviceName;
    }

    public APIDTO(String serviceName, int priority) {
        super("APISOURCE");
        this.serviceName = serviceName;
        this.priority = priority;
        this.requestId = IDUtils.getUUIDFromGenerators();
    }

    public APIDTO(String serviceName, String requestId) {
        super("APISOURCE");
        this.serviceName = serviceName;
        this.requestId = requestId;
    }

    /**
     * 每次请求的任务ID
     */
    private String requestId;

    /**
     * 服务名
     */
    private String serviceName;

    /**
     * 优先级
     * 0-9级，越小优先级越高
     * 默认4
     */
    private int priority = 4;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        APIDTO apidto = (APIDTO)obj;
        if (apidto.getRequestId().equalsIgnoreCase(this.requestId)
                && apidto.getServiceName().equalsIgnoreCase(this.serviceName))
            return true;
        return false;
    }

    @Override
    public int compareTo(APIDTO o) {
        if (priority < o.priority) {
            return -1;
        } else {
            if (priority > o.priority) {
                return 1;
            } else {
                return 1;
            }
        }
    }

    @Override
    public String toString() {
        return "APIDTO{" +
                "requestId='" + requestId + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", priority=" + priority +
                '}';
    }
}
