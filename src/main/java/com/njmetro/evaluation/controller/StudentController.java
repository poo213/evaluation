package com.njmetro.evaluation.controller;


import com.njmetro.evaluation.exception.StudentException;
import com.njmetro.evaluation.param.student.SaveStudentParam;
import com.njmetro.evaluation.param.student.UpdateStudentParam;
import com.njmetro.evaluation.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zc
 * @since 2020-09-21
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
@Slf4j
public class StudentController {

    @Autowired
    StudentService studentService;

    /**
     * 添加考生信息
     *
     * @param saveStudentParam 添加考生参数
     * @return
     */
    @PostMapping("/saveStudent")
    public Boolean saveStudent(@Valid @RequestBody SaveStudentParam saveStudentParam) {
        log.info("saveStudentParam: {}", saveStudentParam);
        return true;
    }

    /**
     * 根据考生ID 删除考生
     *
     * @param id 考生ID
     * @return
     */
    @PostMapping("/delStudent")
    public Boolean delStudent(@Param("id") Integer id) {
       /* if (true){
            throw new StudentException("delStudent 删除失败");
        }*/
        //return studentService.removeById(id);

        return true;
    }

    /**
     * 修改考生信息
     *
     * @param updateStudentParam 考生信息
     * @return
     */
    @PostMapping("/updateStudent")
    public Boolean updateStudent(@Valid @RequestBody UpdateStudentParam updateStudentParam) {
        log.info("updateStudentParam: {}", updateStudentParam);
        return true;
    }
}

