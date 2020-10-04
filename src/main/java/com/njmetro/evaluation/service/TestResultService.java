package com.njmetro.evaluation.service;

import com.njmetro.evaluation.domain.TestResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njmetro.evaluation.vo.FinalResultVO;
import com.njmetro.evaluation.vo.TestResultVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zc
 * @since 2020-09-30
 */
public interface TestResultService extends IService<TestResult> {

    List<TestResultVO> getTempResult(Integer gameNumber, Integer gameRound);
    List<Integer> getStudentIdList(Integer gameNumber, Integer gameRound);
    List<FinalResultVO> getFinalResult();
}
