package com.njmetro.evaluation.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program: evaluation
 * @description: 赛位抽签结果VO
 * @author: zc
 * @create: 2020-10-02 15:34
 **/
@Data
public class SeatDrawVO {
    /**
     * 自增id
     */
    private Integer id;

    /**
     * 单位
     */
    private String companyName;

    /**
     * 考生编码
     */
    private String code;
    /**
     * 考生姓名
     */
    private String name;

    /**
     * 赛位号id
     */
    private Integer seatId;


    /**
     * 比赛场次（1-7）
     */
    private Integer gameNumber;

    /**
     * 比赛轮次（1，2，3）
     */
    private Integer gameRound;

    /**
     * 组名（A-F）
     */
    private Integer groupId;

}
