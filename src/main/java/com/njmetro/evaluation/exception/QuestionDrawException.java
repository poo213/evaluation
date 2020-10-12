package com.njmetro.evaluation.exception;

/**
 * @author 牟欢
 * @Classname QuestionDrawException
 * @Description TODO
 * @Date 2020-10-12 9:08
 */
public class QuestionDrawException extends BaseException {
    /**
     * 创建一个无参构造函数
     */
    public QuestionDrawException() {
    }

    /**
     * 有参构造函数
     *
     * @param message 错误信息
     */
    public QuestionDrawException(String message) {
        super(message);
    }
}
