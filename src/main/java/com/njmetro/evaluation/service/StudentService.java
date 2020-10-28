package com.njmetro.evaluation.service;

import com.njmetro.evaluation.domain.Company;
import com.njmetro.evaluation.domain.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njmetro.evaluation.vo.SignVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zc
 * @since 2020-09-21
 */
public interface StudentService extends IService<Student> {

    List<String> getHaveSighCompanyList();
    List<Integer> getStudentIdList();//获取所有考生id
    List<SignVO> getSignVOList();
    //获取机考成绩求和，如果和为0，表示没有上传成功
    Double checkComputerTestResult();
}
