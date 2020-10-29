package com.njmetro.evaluation.param.login;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 牟欢
 * @Classname loginParam
 * @Description TODO
 * @Date 2020-10-29 9:53
 */
@Data
public class LoginParam {
    @NotBlank(message = "用户名不能为空")
    public String username;

    @NotBlank(message = "密码不能为空")
    public String password;
}
