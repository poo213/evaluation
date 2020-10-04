package com.njmetro.evaluation.exception;

/**
 * @author 牟欢
 * @Classname PadException
 * @Description TODO
 * @Date 2020-10-04 11:27
 */
public class PadException extends BaseException{
    /**
     *  创建一个无参构造函数
     */
    public PadException() {
    }

    /**
     * 有参构造函数
     *
     * @param message 错误信息
     */
    public PadException(String message) {
        super(message);
    }
}
