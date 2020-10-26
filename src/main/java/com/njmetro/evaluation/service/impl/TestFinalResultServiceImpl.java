package com.njmetro.evaluation.service.impl;

import com.njmetro.evaluation.domain.TestFinalResult;
import com.njmetro.evaluation.mapper.TestFinalResultMapper;
import com.njmetro.evaluation.mapper.TestResultMapper;
import com.njmetro.evaluation.service.TestFinalResultService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njmetro.evaluation.vo.TestCompanyResultVO;
import lombok.RequiredArgsConstructor;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zc
 * @since 2020-10-26
 */
@Service
@RequiredArgsConstructor
public class TestFinalResultServiceImpl extends ServiceImpl<TestFinalResultMapper, TestFinalResult> implements TestFinalResultService {

    private final TestFinalResultMapper testFinalResultMapper;


    @Override
    public List<TestCompanyResultVO> getCompanyResult() {
        return testFinalResultMapper.getCompanyResult();
    }
}
