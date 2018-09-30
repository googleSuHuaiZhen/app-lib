package com.zhilink.app_middle.net;

/**
 * 返回基本封装
 * @author xiemeng
 * @date 2018-8-24 15:42
 */
public  class BaseResponse<T> {

    private BaseResponse head;

    private String status;

    private String errorMsg;

    private T body;

    public BaseResponse getHead() {
        return head;
    }

    public void setHead(BaseResponse head) {
        this.head = head;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }


}
