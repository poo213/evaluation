package com.njmetro.evaluation.exception;

/**
 * @author 牟欢
 * @Classname SeatException
 * @Description TODO
 * @Date 2020-09-23 14:51
 */
public class SeatException extends BaseException {
    /**
     * 创建一个无参构造函数
     */
    public SeatException() {
    }

    /**
     * 有参构造函数
     *
     * @param message 错误信息
     */
    public SeatException(String message) {
        super(message);
    }
}
