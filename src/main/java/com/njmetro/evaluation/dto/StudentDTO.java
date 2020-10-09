package com.njmetro.evaluation.dto;

import lombok.Data;

/**
 * @author 牟欢
 * @Classname StudentDTO
 * @Description TODO
 * @Date 2020-10-08 10:43
 */

@Data
public class StudentDTO {
    public StudentResultDTO  studentResultDTO;
    public Integer gameNumber;
    public Integer gameRound;
    public Integer studentId;
}
