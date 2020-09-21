package com.njmetro.evaluation.service.impl;

import com.njmetro.evaluation.domain.Student;
import com.njmetro.evaluation.mapper.StudentMapper;
import com.njmetro.evaluation.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zc
 * @since 2020-09-21
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
