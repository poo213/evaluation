package com.njmetro.evaluation.exception;

/**
 * @author 牟欢
 * @Classname JudgeApiException
 * @Description TODO
 * @Date 2020-09-30 11:33
 */
public class JudgeApiException extends BaseException{
    /**
     *  创建一个无参构造函数
     */
    public JudgeApiException() {
    }

    /**
     * 有参构造函数
     *
     * @param message 错误信息
     */
    public JudgeApiException(String message) {
        super(message);
    }
}
