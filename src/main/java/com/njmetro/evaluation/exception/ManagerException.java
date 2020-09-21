package com.njmetro.evaluation.exception;

import org.springframework.http.HttpStatus;

/**
 * 管理员异常
 *
 * @author RCNJTECH
 * @date 2020/4/14 16:53
 */
public class ManagerException extends com.njmetro.evaluation.exception.GlobalException {

    public ManagerException(HttpStatus httpStatus, com.njmetro.evaluation.exception.ErrorEnum errorEnum) {
        super(httpStatus, errorEnum);
    }

}
