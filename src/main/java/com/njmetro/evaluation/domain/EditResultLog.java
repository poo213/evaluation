package com.njmetro.evaluation.domain;

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
 * @since 2020-10-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("edit_result_log")
public class EditResultLog implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 用户自增Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 修改人
     */
    @TableField("edit_user")
    private String editUser;

    /**
     * 该条记录在最终结果表中的id
     */
    @TableField("test_result_id")
    private Integer testResultId;

    /**
     * 修改前得分
     */
    @TableField("cent_before")
    private Double centBefore;

    /**
     * 修改后得分
     */
    @TableField("cent_after")
    private Double centAfter;

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


}
