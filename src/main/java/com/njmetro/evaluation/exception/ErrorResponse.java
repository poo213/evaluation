package com.njmetro.evaluation.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 错误 Response
 *
 * @author RCNJTECH
 * @date 2020/4/11 18:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    /**
     * 错误代码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    public ErrorResponse(com.njmetro.evaluation.exception.ErrorEnum errorEnum) {
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMessage();
    }

}
