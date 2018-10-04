package com.lxs.util;

import org.codehaus.jackson.map.ObjectMapper;

public class PlanResult {
    //定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();
    //响应业务状态
    private boolean status;
    //相应消息
    private String msg;
    //相应数据
    private Object data;

    public static ObjectMapper getMapper(){
        return MAPPER;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
