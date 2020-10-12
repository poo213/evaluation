package com.njmetro.evaluation.vo;

import lombok.Data;

/**
 * @program: evaluation
 * @description: 暂停，开始时返回值
 * @author: zc
 * @create: 2020-10-09 08:48
 **/
@Data
public class PauseOrStartVO {
    private Integer state;
    private Integer remainingTime;
}
