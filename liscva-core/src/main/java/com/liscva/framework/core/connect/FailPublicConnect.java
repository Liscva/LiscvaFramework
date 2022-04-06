package com.liscva.framework.core.connect;


import com.liscva.framework.core.ThrowStatus;

/**
 * @author 李诗诚
 * @date 2020/7/7 17:06
 */
public class FailPublicConnect extends FinalConnect {

    public FailPublicConnect(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = false;
    }

    public FailPublicConnect() {
        this.code = FinalConnect.CODE_ERROR;
        this.msg = FinalConnect.CODE_ERROR_MSG;
        this.success = false;
    }

    // 构建失败
    public static FailPublicConnect error(ThrowStatus status) {
        return FailPublicConnect.error(status.getCode(), status.getMsg());
    }

    public static FailPublicConnect error() {
        return new FailPublicConnect();
    }


    public static FailPublicConnect error(int code,  String msg) {
        return FailPublicConnect.error(code, msg, null);
    }

    public static FailPublicConnect error(int code, String msg, Object data) {
        return new FailPublicConnect(code, msg, data);
    }


    public static FailPublicConnect defaultError(String msg) {
        return FailPublicConnect.error(CODE_ERROR, msg, null);
    }
}
