package com.njmetro.evaluation.exception;

import lombok.Data;

/**
 * @author 牟欢
 * @Classname DrawStateException
 * @Description TODO
 * @Date 2020-10-02 14:25
 */
public class DrawStateException extends BaseException{
    /**
     *  创建一个无参构造函数
     */
    public DrawStateException() {
    }

    /**
     * 有参构造函数
     *
     * @param message 错误信息
     */
    public DrawStateException(String message) {
        super(message);
    }
}
