package com.liscva.framework.core.connect;

/**
 * @author 李诗诚
 * @date 2020/7/7 14:14
 */
public class FinalConnect<T> extends ConnectData implements IConnect {

    public static final int CODE_SUCCESS = 200;			// 成功状态码
    public static final String CODE_SUCCESS_MSG = "访问成功";          // 成功消息

    public static final int CODE_ERROR = 500;          // 失败状态码

    public static final String CODE_ERROR_MSG = "访问失败";          // 失败消息

    /**
     * 返回的状态码
     */
    protected int code;

    /**
     * 返回的消息
     */
    protected String msg;

    /**
     * 请求是否成功
     */
    protected boolean success;

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
