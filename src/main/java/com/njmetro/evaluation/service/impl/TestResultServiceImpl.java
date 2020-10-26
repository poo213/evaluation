package com.njmetro.evaluation.service.impl;

import com.njmetro.evaluation.domain.TestResult;
import com.njmetro.evaluation.mapper.TestResultMapper;
import com.njmetro.evaluation.service.TestResultService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njmetro.evaluation.vo.FinalResultVO;
import com.njmetro.evaluation.vo.TestResultDetailVO;
import com.njmetro.evaluation.vo.TestResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zc
 * @since 2020-09-30
 */
@Service
@RequiredArgsConstructor
public class TestResultServiceImpl extends ServiceImpl<TestResultMapper, TestResult> implements TestResultService {
    private final TestResultMapper testResultMapper;

    @Override
    public List<TestResultVO> getTempResult(Integer gameNumber, Integer gameRound) {
        return testResultMapper.getTempResult(gameNumber, gameRound);
    }

    @Override
    public List<Integer> getStudentIdList(Integer gameNumber, Integer gameRound) {
        return testResultMapper.getStudentIdList(gameNumber, gameRound);
    }


    @Override
    public List<FinalResultVO> getFinalResult() {
        return testResultMapper.getFinalResult();
    }

    @Override
    public List<TestResultDetailVO> getTestResultDetail(Integer gameNumber, Integer gameRound, Integer studentId) {
        return testResultMapper.getTestResultDetail(gameNumber, gameRound, studentId);
    }

    @Override
    public List<Integer> getJudgeId(Integer gameNumber, Integer gameRound, Integer studentId) {
        return testResultMapper.getJudgeId(gameNumber, gameRound, studentId);
    }

    @Override
    public List<TestResultDetailVO> getTestResultDetailByJudgeId(Integer gameNumber, Integer gameRound, Integer studentId,Integer judgeId) {
        return testResultMapper.getTestResultDetailByJudgeId(gameNumber, gameRound, studentId,judgeId);
    }
}
