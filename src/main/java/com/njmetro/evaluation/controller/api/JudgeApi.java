package com.njmetro.evaluation.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njmetro.evaluation.domain.*;
import com.njmetro.evaluation.dto.JudgeInfoDTO;
import com.njmetro.evaluation.dto.ResultDTO;
import com.njmetro.evaluation.exception.JudgeApiException;
import com.njmetro.evaluation.service.*;
import com.njmetro.evaluation.util.IpUtil;
import com.njmetro.evaluation.vo.api.JudgeInformationVO;
import com.njmetro.evaluation.vo.api.TestQuestionStandardVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.util.LimitedInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
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
@RestController
@RequestMapping("/api/judge")
@Slf4j
public class JudgeApi {

    @Autowired
    JudgeService judgeService;
    @Autowired
    ConfigService configService;
    @Autowired
    PadService padService;

    @Autowired
    JudgeDrawResultService judgeDrawResultService;
    @Autowired
    TestQuestionStandardService testQuestionStandardService;


    /**
     * 轮询接口： 获取 裁判信息，考生赛位号，场次，轮次
     * @param gameNumber
     * @param gameRound
     * @return
     */
    @GetMapping("/getJudgeInfo")
    public JudgeInformationVO getJudgeInformation(Integer gameNumber, Integer gameRound, HttpServletRequest httpServletRequest){
        log.info("gameNumber {}",gameNumber);
        log.info("gameRound {}",gameRound);

        // 获取当前场次轮次
        Config config = configService.getById(1);

        // 根据 ip 获取裁判 id
        String ipAddress = IpUtil.getIpAddr(httpServletRequest);
        if (ipAddress.equals("0:0:0:0:0:0:0:1")) {
            ipAddress = "192.168.96.9";
        }
        log.info("ipAddress {}",ipAddress);
        ipAddress = "192.168.97.7";
        List<JudgeInfoDTO> judgeInfoDTOList = judgeService.getJudgeInfo(ipAddress);
        if(!judgeInfoDTOList.isEmpty()){
            JudgeInformationVO judgeInformationVO = new JudgeInformationVO(judgeInfoDTOList.get(0),config.getGameNumber(),config.getGameRound());
            return judgeInformationVO;
        }else {
            throw new JudgeApiException("查询信息为空");
        }
    }

    /**
     * 上报准备就绪
     *
     * @param gameNumber
     * @param gameRound
     * @return
     */
    @GetMapping("/beReady")
    public Boolean getBeReady(Integer gameNumber, Integer gameRound,HttpServletRequest httpServletRequest){
        log.info("gameNumber {}",gameNumber);
        log.info("gameRound {}",gameRound);
        // 根据 ip 获取裁判 id
        String ipAddress = IpUtil.getIpAddr(httpServletRequest);
        if (ipAddress.equals("0:0:0:0:0:0:0:1")) {
            ipAddress = "192.168.96.9";
        }
        log.info("ipAddress {}",ipAddress);
        ipAddress = "192.168.97.7";
        // ip 获取 pad ID
        QueryWrapper<Pad> padQueryWrapper = new QueryWrapper<>();
        padQueryWrapper.eq("ip",ipAddress);
        Pad pad = padService.getOne(padQueryWrapper);

        QueryWrapper<JudgeDrawResult> judgeDrawResultQueryWrapper = new QueryWrapper<>();
        judgeDrawResultQueryWrapper.eq("pad_id",pad.getId());
        JudgeDrawResult judgeDrawResult = judgeDrawResultService.getOne(judgeDrawResultQueryWrapper);
        // 将状态改为 就绪状态
        judgeDrawResult.setState(1);
        return judgeDrawResultService.updateById(judgeDrawResult);
    }

    /**
     * 获取评分标准
     *
     * @param gameNumber
     * @param gameRound
     * @return
     */
    @GetMapping("/getScoringCriteria")
    public List<TestQuestionStandardVO> getScoringCriteria(Integer gameNumber, Integer gameRound){
        QueryWrapper<TestQuestionStandard> questionStandardQueryWrapper = new QueryWrapper<>();
        questionStandardQueryWrapper.eq("test_question_id",1);
        List<TestQuestionStandard>  testQuestionStandardList = testQuestionStandardService.list(questionStandardQueryWrapper);
        List<TestQuestionStandardVO> testQuestionStandardVOList = new ArrayList<>();
        for(TestQuestionStandard testQuestionStandard : testQuestionStandardList){
            TestQuestionStandardVO testQuestionStandardVO = new TestQuestionStandardVO(testQuestionStandard);
            testQuestionStandardVOList.add(testQuestionStandardVO);
        }
        return testQuestionStandardVOList;
    }


    /**
     * 成绩上报成功
     *
     * @return
     */
    @PostMapping("/submitResults")
    public Boolean submitResults(@RequestBody List<ResultDTO> list,Integer studentId){
        log.info("studentId {}",studentId);
        log.info("resultDTOList {}",list);
        return true;
    }
}
