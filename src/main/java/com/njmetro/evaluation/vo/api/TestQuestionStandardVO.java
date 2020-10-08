package com.njmetro.evaluation.vo.api;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.njmetro.evaluation.domain.TestQuestionStandard;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 牟欢
 * @Classname TestQuestionStandardVO
 * @Description TODO
 * @Date 2020-09-30 14:15
 */
@Data
public class TestQuestionStandardVO {
    private Integer id;
    private Integer testQuestionId;
    private String text;
    private String point;
    private Double score;
    private String standard;
    private double step;



    public TestQuestionStandardVO(TestQuestionStandard testQuestionStandard) {
        this.id = testQuestionStandard.getId();
        this.testQuestionId = testQuestionStandard.getTestQuestionId();
        this.text = testQuestionStandard.getText();
        this.point = testQuestionStandard.getPoint();
        this.score = testQuestionStandard.getScore();
        this.standard = testQuestionStandard.getStandard();
        this.step = testQuestionStandard.getStep();


    }
}
