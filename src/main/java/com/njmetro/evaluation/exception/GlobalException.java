package com.njmetro.evaluation.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 全局异常
 *
 * @author RCNJTECH
 * @date 2020/4/15 14:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class GlobalException extends RuntimeException {

    private HttpStatus httpStatus;

    private com.njmetro.evaluation.exception.ErrorResponse errorResponse;

    public GlobalException(HttpStatus httpStatus, ErrorEnum errorEnum) {
        this.httpStatus = httpStatus;
        this.errorResponse = new com.njmetro.evaluation.exception.ErrorResponse(errorEnum);
    }

    public GlobalException(String message) {
        super(message);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}
