package com.njmetro.evaluation.exception;

import org.springframework.http.HttpStatus;

/**
 * 职工异常
 *
 * @author RCNJTECH
 * @date 2020/4/11 18:29
 */
public class EmployeeException extends GlobalException {

    public EmployeeException(HttpStatus httpStatus, ErrorEnum errorEnum) {
        super(httpStatus, errorEnum);
    }

}
