package com.njmetro.evaluation.vo;

import com.njmetro.evaluation.domain.Student;
import com.njmetro.evaluation.util.NumberToColorUtil;
import lombok.Data;

/**
 * @author 牟欢
 * @Classname StudentShowVO
 * @Description TODO
 * @Date 2020-10-06 9:51
 */
@Data
public class StudentShowVO {

    /**
     * 学生姓名
     */
    public String studentName;

    /**
     * 学生考号
     */
    public String studentCode;

    /**
     *  考生比赛状态
     */
    private String stateColor;

    /**
     * 自定义构造函数
     *
     * @param student 学生信息
     */
    public StudentShowVO(Student student,Integer state) {
        this.studentName = student.getName();
        this.studentCode = student.getCode();
        switch (state){
            case 1 :
                this.setStateColor(NumberToColorUtil.getBackColor(1));
                break;
            case 2 :
                this.setStateColor(NumberToColorUtil.getBackColor(2));
                break;
            case 3 :
                this.setStateColor(NumberToColorUtil.getBackColor(3));
                break;
            case 4 :
                this.setStateColor(NumberToColorUtil.getBackColor(4));
                break;
        }

    }

    public StudentShowVO() {}
}
