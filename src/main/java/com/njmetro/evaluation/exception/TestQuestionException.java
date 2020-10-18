package com.njmetro.evaluation.exception;

/**
 * @author 牟欢
 * @Classname TestQuestionException
 * @Description TODO
 * @Date 2020-10-18 13:00
 */
public class TestQuestionException extends BaseException{
    /**
     * 创建一个无参构造函数
     */
    public TestQuestionException() {
    }

    /**
     * 有参构造函数
     *
     * @param message 错误信息
     */
    public TestQuestionException(String message) {
        super(message);
    }
}
