package com.njmetro.evaluation.service;

import com.njmetro.evaluation.domain.TestResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njmetro.evaluation.vo.FinalResultVO;
import com.njmetro.evaluation.vo.TestResultDetailVO;
import com.njmetro.evaluation.vo.TestResultVO;
import com.njmetro.evaluation.vo.api.TestQuestionStandardVO;
import org.apache.poi.util.Internal;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zc
 * @since 2020-09-30
 */
public interface TestResultService extends IService<TestResult> {

    List<TestResultVO> getTempResult(Integer gameNumber, Integer gameRound);

    List<Integer> getStudentIdList(Integer gameNumber, Integer gameRound);

    List<FinalResultVO> getFinalResult();

    /**
     * 获取打分详情，校正得分时候用
     *
     * @return
     */
    List<TestResultDetailVO> getTestResultDetail(Integer gameNumber, Integer gameRound, Integer studentId);

    /**
     * 获取指定场次轮次，学生id的两个裁判
     */
    List<Integer> getJudgeId(Integer gameNumber, Integer gameRound, Integer studentId);
    /**
     * 获取打分详情，校正得分时候用,获取指定场次、学生的某一个裁判的打分详情
     *
     * @return
     */
    List<TestResultDetailVO> getTestResultDetailByJudgeId(Integer gameNumber, Integer gameRound, Integer studentId,Integer judgeId);

    /**
     * 获取手动补录成绩 评分标准
     *
     * @param gameNumber 场次
     * @param gameRound 轮次
     * @param judgeId 裁判Id
     * @return
     */
    List<TestQuestionStandardVO> getWriteResultStandards(Integer gameNumber, Integer gameRound, Integer judgeId);
}
