package com.njmetro.evaluation.vo;

import com.njmetro.evaluation.domain.Student;
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
     * 学生就绪状态
     */
    private Boolean state;

    /**
     * 自定义构造函数
     *
     * @param student 学生信息
     */
    public StudentShowVO(Student student) {
        this.studentName = student.getName();
        this.studentCode = student.getCode();
        if(student.getSignState() == "0"){
            this.state = false;
        }else {
            this.state = true;
        }

    }

    public StudentShowVO() {}
}
