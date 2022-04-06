package com.liscva.framework.core.connect;

/**
 * @author
 * @date 2020/7/7 17:06
 */
public class DefaultPublicConnect<T> extends AbstractPublicConnect {


    protected DefaultPublicConnect() {
        super();
        this.code = FinalConnect.CODE_SUCCESS;
        this.msg = FinalConnect.CODE_SUCCESS_MSG;
        this.success = true;
    }

    protected DefaultPublicConnect(T data) {
        this();
        this.data = data;
    }

    protected DefaultPublicConnect(String msg) {
        this();
        this.msg = msg;
    }

    public static <T> FinalConnect<T> of(T data) {
        return new DefaultPublicConnect<T>(data);
    }

    public static <T> FinalConnect<T> ok(String msg) {
        return new DefaultPublicConnect<T>(msg);
    }

    public static <T> FinalConnect<T> ok() {
        return new DefaultPublicConnect<T>();
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
