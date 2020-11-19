package com.njmetro.evaluation.exception;

/**
 * @author 牟欢
 * @Classname BaseException
 * @Description TODO
 * @Date 2020-09-21 15:05
 */
public class BaseException extends RuntimeException {

    /**
     * 自定义错误信息
     */
    private String message;

    public BaseException() {}

    /**
     *
     * @param message 自定义构造函数
     */
    public BaseException(String message) {
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
