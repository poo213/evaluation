package com.njmetro.evaluation.exception;

/**
 * @author 牟欢
 * @Classname ExceptionEnum
 * @Description TODO
 * @Date 2020-09-21 15:22
 */
public enum ExceptionEnum {


    /**
     * 请求方法不受支持
     */
    HttpRequestMethodNotSupportedException("A0101", "请求方法不受支持"),

    /**
     * 请求方法参数类型错误
     */
    MethodArgumentTypeMismatchException("A0102", "请求方法参数类型错误");



    /**
     * 错误码
     */
    private final String errorCode;

    /**
     * 错误信息
     */
    private final String errorMessage;

    ExceptionEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
