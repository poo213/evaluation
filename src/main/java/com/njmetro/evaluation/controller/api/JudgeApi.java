package com.njmetro.evaluation.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njmetro.evaluation.domain.*;
import com.njmetro.evaluation.dto.JudgeInfoDTO;
import com.njmetro.evaluation.dto.StudentResultDTO;
import com.njmetro.evaluation.exception.JudgeApiException;
import com.njmetro.evaluation.exception.PadException;
import com.njmetro.evaluation.exception.TestQuestionException;
import com.njmetro.evaluation.service.*;
import com.njmetro.evaluation.util.IpUtil;
import com.njmetro.evaluation.util.SeatUtil;
import com.njmetro.evaluation.vo.PadSeatInfoVO;
import com.njmetro.evaluation.vo.StudentStateVO;
import com.njmetro.evaluation.vo.api.JudgeInformationVO;
import com.njmetro.evaluation.vo.api.TestQuestionStandardResultVO;
import com.njmetro.evaluation.vo.api.TestQuestionStandardVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 牟欢
 * @Classname JudgeApi
 * @Description TODO
 * @Date 2020-09-29 9:52
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/judge")
public class JudgeApi {
    private final JudgeService judgeService;
    private final ConfigService configService;
    private final PadService padService;
    private final JudgeDrawResultService judgeDrawResultService;
    private final TestQuestionStandardService testQuestionStandardService;
    private final QuestionDrawService questionDrawService;
    private final TestQuestionService testQuestionService;
    private final TestResultService testResultService;
    private final SeatDrawService seatDrawService;

    /**
     * 轮询接口： 获取 裁判信息，考生赛位号，场次，轮次
     *
     * @return
     */
    @GetMapping("/getJudgeInfo")
    public JudgeInformationVO getJudgeInformation(@RequestAttribute("pad") Pad pad,@RequestAttribute("ip") String ip,@RequestAttribute("config") Config config) {
        log.info("获取到拦截器pad {} ",pad);
        log.info("获取到拦截器ip {} ",ip);
        log.info("获取到拦截器config {} ",config);
        Integer studentSeatId = SeatUtil.getStudentSeatIdByJudgeSeatId(pad.getSeatId());
        // 根据 studentSeatId 场次 和 轮次 在 seat_draw 中查找 studentId
        QueryWrapper<SeatDraw> seatDrawQueryWrapper = new QueryWrapper<>();
        seatDrawQueryWrapper.eq("game_number",config.getGameNumber())
                .eq("game_round",config.getGameRound())
                .eq("seat_id",studentSeatId);
        SeatDraw seatDraw = seatDrawService.getOne(seatDrawQueryWrapper);
        Integer studentId = 0;
        if(seatDraw != null){
            studentId = seatDraw.getStudentId();
        }else {
            log.info("没有找到studentId");
            throw new JudgeApiException("没有找到studentId");
        }
        List<JudgeInfoDTO> judgeInfoDTOList = judgeService.getJudgeInfo(ip);
        if (!judgeInfoDTOList.isEmpty()) {
            JudgeInformationVO judgeInformationVO = new JudgeInformationVO(judgeInfoDTOList.get(0), config.getGameNumber(), config.getGameRound(),studentId);
            return judgeInformationVO;
        } else {
            throw new JudgeApiException("没有查询到裁判信息");
        }
    }


    @GetMapping("/pauseOrStart")
    public StudentStateVO pauseOrStart(@RequestAttribute("pad") Pad pad,@RequestAttribute("config")Config config) {

        Integer studentSeatId = SeatUtil.getStudentSeatIdByJudgeSeatId(pad.getSeatId());
        // 根据 studentSeatId 场次 和 轮次 在 seat_draw 中查找 studentId
        QueryWrapper<SeatDraw> seatDrawQueryWrapper = new QueryWrapper<>();
        seatDrawQueryWrapper.eq("game_number",config.getGameNumber())
                .eq("game_round",config.getGameRound())
                .eq("seat_id",studentSeatId);
        SeatDraw seatDraw = seatDrawService.getOne(seatDrawQueryWrapper);
        if(seatDraw == null){
            log.info("没有找到考生状态信息");
            throw new JudgeApiException("没有找到考生状态信息");
        }else {
            return new StudentStateVO(seatDraw.getState(),seatDraw.getRemainingTime());
        }
        //return new StudentStateVO(2,1000);
    }

    /**
     * 上报准备就绪
     *
     * @return
     */
    @GetMapping("/beReady")
    public Boolean getBeReady(@RequestAttribute("pad") Pad pad) {
        log.info("pad: {}",pad);
        // 通过padId 在 JudgeDrawResult 中找到对应的记录
        QueryWrapper<JudgeDrawResult> judgeDrawResultQueryWrapper = new QueryWrapper<>();
        judgeDrawResultQueryWrapper.eq("pad_id", pad.getId());
        JudgeDrawResult judgeDrawResult = judgeDrawResultService.getOne(judgeDrawResultQueryWrapper);
        // 修改就绪状态为 1
        judgeDrawResult.setState(1);
        return judgeDrawResultService.updateById(judgeDrawResult);
    }

    /**
     * 获取评分标准
     *
     * @return
     */
    @GetMapping("/getScoringCriteria")
    public TestQuestionStandardResultVO getScoringCriteria(@RequestAttribute("pad") Pad pad,@RequestAttribute("config") Config config) {
        if(config.getState().equals(3)){
            // 如果下发试题成功，裁判可以访问到试题评分标准
            Integer studentSeatId = SeatUtil.getStudentSeatIdByJudgeSeatId(pad.getSeatId());
            // 根据 studentSeatId 场次 和 轮次 在 seat_draw 中查找 studentId
            QueryWrapper<SeatDraw> seatDrawQueryWrapper = new QueryWrapper<>();
            seatDrawQueryWrapper.eq("game_number",config.getGameNumber())
                    .eq("game_round",config.getGameRound())
                    .eq("seat_id",studentSeatId);
            SeatDraw seatDraw = seatDrawService.getOne(seatDrawQueryWrapper);
            Integer studentId = 0;
            if(seatDraw != null){
                studentId = seatDraw.getStudentId();
            }else {
                log.info("没有找到studentId");
                throw new JudgeApiException("根据padIP没有找到studentId");
            }
            // 根据padId 获取裁判Id
            QueryWrapper<JudgeDrawResult> judgeDrawResultQueryWrapper = new QueryWrapper<>();
            judgeDrawResultQueryWrapper.eq("pad_id", pad.getId());
            JudgeDrawResult judgeDrawResult = judgeDrawResultService.getOne(judgeDrawResultQueryWrapper);
            Integer judgeId = judgeDrawResult.getJudgeId();
            // 根据 场次 和 考生所在位置 在 question 中获取 赛题信息
            QueryWrapper<QuestionDraw> questionDrawQueryWrapper = new QueryWrapper<>();
            questionDrawQueryWrapper.eq("game_number", config.getGameNumber())
                    .eq("game_type", SeatUtil.getGameTypeByStudentSeatId(studentSeatId));
            QuestionDraw questionDraw = questionDrawService.getOne(questionDrawQueryWrapper);
            if (questionDraw == null) {
                log.info("没有找到考题");
                throw new JudgeApiException("没有找到考题");
            } else {
                // 根据 试题 id 在 test_question_standard 中找到判题标准
                QueryWrapper<TestQuestionStandard> questionStandardQueryWrapper = new QueryWrapper<>();
                questionStandardQueryWrapper.eq("test_question_id", questionDraw.getQuestionId());
                List<TestQuestionStandard> testQuestionStandardList = testQuestionStandardService.list(questionStandardQueryWrapper);
                // 将结果进行封装
                List<TestQuestionStandardVO> testQuestionStandardVOList = new ArrayList<>();
                for (TestQuestionStandard testQuestionStandard : testQuestionStandardList) {
                    TestQuestionStandardVO testQuestionStandardVO = new TestQuestionStandardVO(testQuestionStandard);
                    testQuestionStandardVOList.add(testQuestionStandardVO);
                }
                // 根据试题id 获取 试题名称
                String testName = testQuestionService.getById(questionDraw.getQuestionId()).getName();
                return new TestQuestionStandardResultVO(testQuestionStandardVOList, testName, questionDraw.getQuestionId(), judgeId, studentId);
            }
        }else {
            throw new TestQuestionException("裁判还未发题，获取评分评分标准");
        }
    }


    /**
     * 成绩上报成功
     *
     * @return
     */
    @PostMapping("/submitResults")
    public Boolean submitResults(@RequestBody List<StudentResultDTO> list, Integer gameNumber, Integer gameRound, Integer state, Integer studentId, Integer questionId, Integer judgeId) {
        log.info("gameNumber {}", gameNumber);
        log.info("gameRound {}", gameRound);
        log.info("state {}", state);
        log.info("studentId {}", studentId);
        log.info("questionId {}", questionId);
        log.info("judgeId {}", judgeId);
        log.info("list {}", list);

        // 判断 传入场次和轮次 和 数据库中是否一致
        Config config = configService.getById(1);
        if (config.getGameNumber().equals(gameNumber) && config.getGameRound().equals(gameRound)) {
            // 根据 state 判断能否修改  0 ： 可修改   1：不可修改
            TestResult testResult = new TestResult(gameNumber,gameRound,studentId,judgeId,questionId);
            QueryWrapper<TestResult> testResultQueryWrapper = new QueryWrapper<>();
            testResultQueryWrapper.eq("game_number", gameNumber)
                    .eq("game_round", gameRound)
                    .eq("student_id", studentId)
                    .eq("judge_id", judgeId)
                    .eq("question_id", questionId);
            List<TestResult> testResultList = testResultService.list(testResultQueryWrapper);
            log.info("testResultList {}", testResultList);
            if(testResultList.size() == 0){
                // 没有成绩时 可以直接写入
                for (StudentResultDTO studentResultDTO : list){
                    // 设置更新结果
                    testResult.setState(state);
                    testResult.setQuestionStandardId(studentResultDTO.getId());
                    testResult.setCent(studentResultDTO.getResult());
                    testResultService.save(testResult);
                    log.info("没有成绩时 可以直接写入   ----");
                }
            }else {
                // 允许上传数据
                if(testResultList.get(0).getState() == 0){
                    for (StudentResultDTO studentResultDTO : list){
                        QueryWrapper<TestResult> testResultQueryWrapperSql = new QueryWrapper<>();
                        testResultQueryWrapperSql.eq("game_number", gameNumber)
                                .eq("game_round", gameRound)
                                .eq("student_id", studentId)
                                .eq("judge_id", judgeId)
                                .eq("question_id", questionId)
                                .eq("question_standard_id",studentResultDTO.getId());
                        TestResult testResultSql = testResultService.getOne(testResultQueryWrapperSql);
                        if(testResultSql == null){
                            log.info("没有该记录，插入一条记录");
                            // 设置更新结果
                            testResult.setState(state);
                            testResult.setQuestionStandardId(studentResultDTO.getId());
                            testResult.setCent(studentResultDTO.getResult());
                            testResultService.save(testResult);
                        }else {
                            log.info("存在该记录，根据id更新记录");
                            testResultSql.setState(state);
                            testResultSql.setQuestionStandardId(studentResultDTO.getId());
                            testResultSql.setCent(studentResultDTO.getResult());
                            testResultService.updateById(testResultSql);
                        }
                    }
                }else {
                    log.info("成绩上传截至！请核对后再上传");
                    throw new JudgeApiException("成绩上传截至！请核对后再上传");
                }

            }
            return true;
        } else {
            throw new JudgeApiException("场次或轮次与数据库不匹配，请核对后提交！");
        }
    }

    /**
     * 根据ip 获取 赛位和赛组信息
     *
     * @param pad
     * @return
     */
    @GetMapping("/getPadSeatInfo")
    public PadSeatInfoVO getPadSeatInfo(@RequestAttribute("pad") Pad pad) {
        PadSeatInfoVO padSeatInfoVO = new PadSeatInfoVO();
        padSeatInfoVO.setPadId(pad.getId());
        padSeatInfoVO.setSeatId(pad.getSeatId());
        if (pad.getType() == 1) {
            padSeatInfoVO.setSeatId(pad.getSeatId());
            padSeatInfoVO.setPadType("考生pad");
            padSeatInfoVO.setGroupId(SeatUtil.getGroupIdByStudentSeatId(pad.getSeatId()));
        } else {
            padSeatInfoVO.setPadType("裁判pad");
            padSeatInfoVO.setSeatId(SeatUtil.getStudentSeatIdByJudgeSeatId(pad.getSeatId()));
            padSeatInfoVO.setGroupId(SeatUtil.getGroupIdByJudgeSeatId(pad.getSeatId()));
        }
        return padSeatInfoVO;
    }
}
