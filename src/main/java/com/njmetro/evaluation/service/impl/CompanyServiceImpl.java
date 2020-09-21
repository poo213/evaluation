package com.njmetro.evaluation.service.impl;

import com.njmetro.evaluation.domain.Company;
import com.njmetro.evaluation.mapper.CompanyMapper;
import com.njmetro.evaluation.service.CompanyService;
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
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

}
