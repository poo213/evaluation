package com.njmetro.evaluation.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zc
 * @since 2020-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("company")
public class Company implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 参赛单位 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 单位编码
     */
    @TableField("code")
    @Excel(name="编码")
    private String code;

    /**
     * 单位名称
     */
    @TableField("name")
    @Excel(name="单位名")
    private String name;

    /**
     * 单位简介
     */
    @TableField("introduction")
    @Excel(name="简介")
    private String introduction;

    /**
     * 领队姓名
     */
    @TableField("leader_name")
    @Excel(name="领队姓名")
    private String leaderName;

    /**
     * 领队联系方式 
     */
    @TableField("leader_phone")
    @Excel(name="领队电话")
    private String leaderPhone;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;
    /**
     * 修改时间
     */
    @TableField("draw_result")
    @Excel(name="抽签结果")
    private Integer drawResult;

}
