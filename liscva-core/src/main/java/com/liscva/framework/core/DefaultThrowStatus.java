package com.liscva.framework.core;

import com.liscva.framework.core.connect.FinalConnect;

public class DefaultThrowStatus implements ThrowStatus {

    private static ThrowStatus instance = new DefaultThrowStatus();

    private DefaultThrowStatus() {
    }

    public static ThrowStatus getInstance() {
        return instance;
    }

    @Override
    public int getCode() {
        return FinalConnect.CODE_ERROR;
    }

    @Override
    public String getMsg() {
        return FinalConnect.CODE_ERROR_MSG;
    }
}
