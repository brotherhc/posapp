package me.ethantu.posapp.interfaces.shared.web;

import java.io.Serializable;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 2015/7/15
 * Time: 2:01
 */
public class ApiResponse implements Serializable {

    private String code;

    private Object data;

    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
