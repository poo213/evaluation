package com.njmetro.evaluation.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njmetro.evaluation.domain.*;
import com.njmetro.evaluation.dto.JudgeInfoDTO;
import com.njmetro.evaluation.dto.StudentResultDTO;
import com.njmetro.evaluation.exception.JudgeApiException;
import com.njmetro.evaluation.exception.PadException;
import com.njmetro.evaluation.service.*;
import com.njmetro.evaluation.util.IpUtil;
import com.njmetro.evaluation.util.SeatUtil;
import com.njmetro.evaluation.vo.PadSeatInfoVO;
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

    /**
     * 轮询接口： 获取 裁判信息，考生赛位号，场次，轮次
     *
     * @return
     */
    @GetMapping("/getJudgeInfo")
    public JudgeInformationVO getJudgeInformation(HttpServletRequest httpServletRequest) {
        Config config = configService.getById(1);
        String ipAddress = IpUtil.getIpAddr(httpServletRequest);
        /**
         *  TODO 去掉 ip值
         */
        ipAddress = "192.168.97.7";
        List<JudgeInfoDTO> judgeInfoDTOList = judgeService.getJudgeInfo(ipAddress);
        if (!judgeInfoDTOList.isEmpty()) {
            JudgeInformationVO judgeInformationVO = new JudgeInformationVO(judgeInfoDTOList.get(0), config.getGameNumber(), config.getGameRound());
            return judgeInformationVO;
        } else {
            throw new JudgeApiException("没有查询到裁判信息");
        }
    }

    /**
     * 上报准备就绪
     *
     * @return
     */
    @GetMapping("/beReady")
    public Boolean getBeReady(HttpServletRequest httpServletRequest) {
        String ipAddress = IpUtil.getIpAddr(httpServletRequest);
        /**
         *  TODO 去掉 ip值
         */
        ipAddress = "192.168.97.7";
        // 根据 ip 查询pad信息
        QueryWrapper<Pad> padQueryWrapper = new QueryWrapper<>();
        padQueryWrapper.eq("ip", ipAddress);
        Pad pad = padService.getOne(padQueryWrapper);
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
    public TestQuestionStandardResultVO getScoringCriteria(HttpServletRequest httpServletRequest) {
        String ipAddress = IpUtil.getIpAddr(httpServletRequest);
        /**
         * TODO : 删除
         */
        ipAddress = "192.168.97.17";
        // 根据 ip 获取 padId
        QueryWrapper<Pad> padQueryWrapper = new QueryWrapper<>();
        padQueryWrapper.eq("ip", ipAddress)
                .eq("type", 2);
        Pad pad = padService.getOne(padQueryWrapper);
        if (pad == null) {
            log.info("ip 错误 ！");
            throw new JudgeApiException("ip 错误 ！没有找到任何结果！！");
        } else {
            Config config = configService.getById(1);
            Integer studentSeatId = SeatUtil.getStudentSeatIdByJudgeSeatId(pad.getSeatId());
            // 根据 场次 和 考生所在位置 在 question 中获取 赛题信息
            QueryWrapper<QuestionDraw> questionDrawQueryWrapper = new QueryWrapper<>();
            questionDrawQueryWrapper.eq("game_number", config.getGameNumber())
                    .eq("seat_id", studentSeatId);
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
                return new TestQuestionStandardResultVO(testQuestionStandardVOList, testName);
            }
        }
    }


    /**
     * 成绩上报成功
     *
     * @return
     */
    @PostMapping("/submitResults")
    public Boolean submitResults(@RequestBody List<StudentResultDTO> list, Integer gameNumber, Integer gameRound, Integer state, Integer studentId) {
        log.info("gameNumber {}", gameNumber);
        log.info("gameRound {}", gameRound);
        log.info("state {}", state);
        log.info("studentId {}", studentId);
        log.info("list {}", list);
        return true;
    }

    /**
     * 根据ip 获取 赛位和赛组信息
     *
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/getPadSeatInfo")
    public PadSeatInfoVO getPadSeatInfo(HttpServletRequest httpServletRequest) {
        String ipAddress = IpUtil.getIpAddr(httpServletRequest);
        ipAddress = "192.168.96.7";
        log.info("ipAddress {}", ipAddress);
        QueryWrapper<Pad> padQueryWrapper = new QueryWrapper<>();
        padQueryWrapper.eq("ip", ipAddress);
        Pad pad = padService.getOne(padQueryWrapper);
        if (pad == null) {
            throw new PadException("根据IP 未能找到匹配的pad");
        } else {
            PadSeatInfoVO padSeatInfoVO = new PadSeatInfoVO();
            padSeatInfoVO.setPadId(pad.getId());
            padSeatInfoVO.setSeatId(pad.getSeatId());
            if (pad.getType() == 1) {
                padSeatInfoVO.setPadType("考生pad");
                padSeatInfoVO.setGroupId(SeatUtil.getGroupIdByStudentSeatId(pad.getSeatId()));
            } else {
                padSeatInfoVO.setPadType("裁判pad");
                padSeatInfoVO.setGroupId(SeatUtil.getGroupIdByJudgeSeatId(pad.getSeatId()));
            }
            return padSeatInfoVO;
        }
    }

}
