package com.njmetro.evaluation.param.company;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @program: evaluation
 * @description: update参数
 * @author: zc
 * @create: 2020-09-22 14:14
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateCompanyParam {
    /**
     * 单位自增编号
     */

    private Integer id;

    /**
     * 单位编码
     */
    @NotBlank(message = "单位编码不能为空")
    private String code;

    /**
     * 单位名称
     */
    @NotBlank(message = "单位名称不能为空")
    private String name;

    /**
     * 单位简介
     */
    @NotNull(message = "单位简介不能为空")
    private String introduction;

    /**
     * 领队姓名
     */
    @NotNull(message = "领队姓名不能为空")
    private String leaderName;

    /**
     * 考生手机号
     */
    @NotBlank(message = "领队电话不能为空")
    private String leaderPhone;
}
