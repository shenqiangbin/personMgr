package com.sqber.personMgr.base;

public class BaseResponse<T> {

    private int code;
    private String msg;
    private T data;

    public BaseResponse(){
        this.code = 200;
    }

    public static BaseResponse fail(String msg) {
        BaseResponse b = new BaseResponse();
        b.setCode(401);
        b.setMsg(msg);
        return b;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
