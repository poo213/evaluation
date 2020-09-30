package com.njmetro.evaluation.dto;

import lombok.Data;

import java.security.PublicKey;

/**
 * @author 牟欢
 * @Classname JudgeInfo
 * @Description TODO
 * @Date 2020-09-30 11:10
 */
@Data
public class JudgeInfoDTO {
    public Integer id ;
    public String name;
    public String code;
    public Integer seatId;
    public String phone;
    public String judgeType;
}
