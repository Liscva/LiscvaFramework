package com.liscva.framework.core.connect;

import com.liscva.framework.core.ThrowStatus;
import lombok.Data;

/**
 * @author 李诗诚
 * @date 2020/7/7 14:14
 */
@Data
public class FinalConnect<T> extends ConnectData implements IConnect {

    protected String code = ThrowStatus.OK.value();

    protected String msg;

    protected boolean isSuccess;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

}
