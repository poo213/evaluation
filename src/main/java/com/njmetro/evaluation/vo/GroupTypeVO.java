package com.njmetro.evaluation.vo;

import com.njmetro.evaluation.domain.Judge;
import com.njmetro.evaluation.domain.Student;
import lombok.Data;

import javax.servlet.http.PushBuilder;

/**
 * @author 牟欢
 * @Classname TypeInfoVO
 * @Description TODO
 * @Date 2020-09-30 16:08
 */
@Data
public class GroupTypeVO {
    /**
     * 类型名称
     */
    public String typeName;
    /**
     * 考生展示结果
     */
    public GroupTypeStudentVO groupTypeStudentVO;
    /**
     * 裁判 1
     */
    public GroupTypeJudgeVO  leftGroupTypeJudgeVO;
    /**
     * 裁判 2
     */
    public GroupTypeJudgeVO rightWGroupTypeJudgeVO;
}
