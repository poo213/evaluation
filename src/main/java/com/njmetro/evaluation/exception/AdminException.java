package com.njmetro.evaluation.exception;

/**
 * @author 牟欢
 * @Classname AdminException
 * @Description TODO
 * @Date 2020-10-29 10:18
 */
public class AdminException extends  BaseException{
    /**
     *  创建一个无参构造函数
     */
    public AdminException() {
    }

    /**
     * 有参构造函数
     *
     * @param message 错误信息
     */
    public AdminException(String message) {
        super(message);
    }
}
