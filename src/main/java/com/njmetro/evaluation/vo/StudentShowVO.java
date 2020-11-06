package com.njmetro.evaluation.vo;

import com.njmetro.evaluation.common.SystemCommon;
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
     *  考生照片 url(根据身份证 查找用户照片)
     */
    private String idCard;
    /**
     * 自定义构造函数
     *
     * @param student 学生信息
     */
    public StudentShowVO(Student student,Integer state) {
        /**
         *  读取考生照片地址
         */
        this.setIdCard(SystemCommon.PHOTO_URL+student.getIdCard()+".png");
        this.studentName = student.getName();
        this.studentCode = student.getCode();
        this.setStateColor(NumberToColorUtil.getStudentBackColor(state));

    }

    public StudentShowVO() {}
}
