package com.njmetro.evaluation.vo;

import lombok.Data;

/**
 * @program: evaluation
 * @description: 传到前台的考题url，读题时间，考试时间
 * @author: zc
 * @create: 2020-10-12 17:07
 **/
@Data
public class QuestionVO {
    public String url;
    public String questionName;
    public Integer readTime;
    public Integer testTime;
}
