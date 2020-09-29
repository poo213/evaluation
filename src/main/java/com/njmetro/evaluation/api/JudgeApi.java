package com.njmetro.evaluation.api;

import com.njmetro.evaluation.domain.Judge;
import com.njmetro.evaluation.dto.ResultDTO;
import com.njmetro.evaluation.service.JudgeService;
import com.njmetro.evaluation.vo.api.JudgeInformationVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 轮询接口： 获取 裁判信息，考生赛位号，场次，轮次
     * @param gameNumber
     * @param gameRound
     * @return
     */
    @GetMapping("/getJudgeInformation")
    public JudgeInformationVO getJudgeInformation(Integer gameNumber, Integer gameRound){
        JudgeInformationVO judgeInformationVO = new JudgeInformationVO();
        Judge judge = judgeService.getById(1);
        judgeInformationVO.setJudge(judge);
        judgeInformationVO.setSeatId(1);
        judgeInformationVO.setGameNumber(2);
        judgeInformationVO.setGameRound(3);
        return judgeInformationVO;
    }

    /**
     * 上报准备就绪
     *
     * @param gameNumber
     * @param gameRound
     * @return
     */
    @GetMapping("/getBeReady")
    public Boolean getBeReady(Integer gameNumber, Integer gameRound){
        return true;
    }

    /**
     * 获取评分标准
     *
     * @param gameNumber
     * @param gameRound
     * @return
     */
    @GetMapping("/getScoringCriteria")
    public List<String> getScoringCriteria(Integer gameNumber, Integer gameRound){
        return null;
    }


    /**
     * 成绩上报成功
     *
     * @return
     */
    @PostMapping("/submitResults")
    public Boolean submitResults(@RequestBody List<ResultDTO> list){
        log.info("resultDTOList {}",list);
        return true;
    }
}
