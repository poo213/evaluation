package com.njmetro.evaluation.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @program: evaluation
 * @description: ConfigVO
 * @author: zc
 * @create: 2020-10-01 14:35
 **/
@Data
public class ConfigVO {
    /**
     * 比赛场次（1-7）
     */
    private Integer gameNumber;

    /**
     * 比赛轮次（1，2，3）
     */
    private Integer gameRound;
}
