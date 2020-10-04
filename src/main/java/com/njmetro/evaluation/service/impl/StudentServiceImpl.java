package com.njmetro.evaluation.service.impl;

import com.njmetro.evaluation.domain.Company;
import com.njmetro.evaluation.domain.Student;
import com.njmetro.evaluation.mapper.StudentMapper;
import com.njmetro.evaluation.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zc
 * @since 2020-09-21
 */
@Service
@RequiredArgsConstructor
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    private final StudentMapper studentMapper;
    @Override
    public List<String> getHaveSighCompanyList() {
        return studentMapper.getHaveSighCompanyList();
    }

    @Override
    public List<Integer> getStudentIdList(){
        return studentMapper.getStudentIdList();
    }
}
