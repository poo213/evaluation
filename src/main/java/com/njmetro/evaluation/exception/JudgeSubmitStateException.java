package com.njmetro.evaluation.exception;

/**
 * @author 牟欢
 * @Classname JudgeSubmitStateException
 * @Description TODO
 * @Date 2020-11-12 18:29
 */
public class JudgeSubmitStateException extends BaseException{
    /**
     *  创建一个无参构造函数
     */
    public JudgeSubmitStateException() {
    }

    /**
     * 有参构造函数
     *
     * @param message 错误信息
     */
    public JudgeSubmitStateException(String message) {
        super(message);
    }
}
