package com.njmetro.evaluation.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @program: evaluation
 * @description: 用户表
 * @author: zc
 * @create: 2020-09-19 11:18
 **/
@Data
public class User {
    /**
     * 编号
     */
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * user
     */
    private String userName;
    /**
     * 密码
     */
    private String password;


    /**
     * 角色
     */
    private String role;
}
