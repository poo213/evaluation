package com.njmetro.evaluation.exception;

/**
 * @author 牟欢
 * @Classname ConfigException
 * @Description TODO
 * @Date 2020-10-16 11:03
 */
public class ConfigException extends BaseException{
    /**
     *  创建一个无参构造函数
     */
    public ConfigException() {
    }

    /**
     * 有参构造函数
     *
     * @param message 错误信息
     */
    public ConfigException(String message) {
        super(message);
    }
}
