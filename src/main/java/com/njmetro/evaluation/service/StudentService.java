package com.njmetro.evaluation.service;

import com.njmetro.evaluation.domain.Company;
import com.njmetro.evaluation.domain.Student;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
