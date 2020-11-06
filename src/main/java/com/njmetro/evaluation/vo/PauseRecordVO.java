package com.njmetro.evaluation.vo;/**
 * @Classname pauseRecordVO
 * @Description TODO
 * @Date 2020-11-5 15:14
 * @Created by zc
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program: evaluation
 * @description: 暂停调整的前台VO
 * @author: zc
 * @create: 2020-11-05 15:14
 **/
@Data
public class PauseRecordVO {
    /**
     * id
     */
    private Integer id;

    /**
     * seat_draw_id id
     */
    private Integer seatDrawId;

    /**
     * 类型 0 :暂停  1：开始
     */
    private Integer type;

    /**
     * 场次
     */
    private Integer gameNumber;

    /**
     * 轮次
     */
    private Integer gameRound;

    /**
     * 学生id
     */
    private Integer studentId;

    /**
     * 学生姓名
     */
    private String studentName;
    /**
     * 公司名称
     */

    private String companyName;
    /**
     * 暂停用时
     */
    private Integer pauseTime;
    /**
     * 是否有效标记
     */
    private Boolean flag;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

}
