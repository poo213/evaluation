package com.njmetro.evaluation.exception;

/**
 * @author 牟欢
 * @Classname StudentException
 * @Description TODO
 * @Date 2020-09-21 15:07
 */
public class StudentException extends BaseException{

    /**
     *  创建一个无参构造函数
     */
    public StudentException() {
    }

    /**
     * 有参构造函数
     *
     * @param message 错误信息
     */
    public StudentException(String message) {
        super(message);
    }
}
