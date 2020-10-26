package com.njmetro.evaluation.service;

import com.njmetro.evaluation.domain.TestFinalResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njmetro.evaluation.vo.TestCompanyResultVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zc
 * @since 2020-10-26
 */
public interface TestFinalResultService extends IService<TestFinalResult> {
    List<TestCompanyResultVO> getCompanyResult();
}
