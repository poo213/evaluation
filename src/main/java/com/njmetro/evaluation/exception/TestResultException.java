package com.njmetro.evaluation.exception;

/**
 * @author 牟欢
 * @Classname TestResultException
 * @Description TODO
 * @Date 2020-11-02 13:48
 */
public class TestResultException extends BaseException{
    /**
     * 创建一个无参构造函数
     */
    public TestResultException() {
    }

    /**
     * 有参构造函数
     *
     * @param message 错误信息
     */
    public TestResultException(String message) {
        super(message);
    }
}
