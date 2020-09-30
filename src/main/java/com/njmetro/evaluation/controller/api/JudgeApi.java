package com.njmetro.evaluation.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njmetro.evaluation.domain.Config;
import com.njmetro.evaluation.domain.Judge;
import com.njmetro.evaluation.domain.JudgeDrawResult;
import com.njmetro.evaluation.domain.Pad;
import com.njmetro.evaluation.dto.JudgeInfoDTO;
import com.njmetro.evaluation.dto.ResultDTO;
import com.njmetro.evaluation.exception.JudgeApiException;
import com.njmetro.evaluation.service.ConfigService;
import com.njmetro.evaluation.service.JudgeService;
import com.njmetro.evaluation.service.PadService;
import com.njmetro.evaluation.util.IpUtil;
import com.njmetro.evaluation.vo.api.JudgeInformationVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
            JudgeInformationVO judgeInformationVO = new JudgeInformationVO(judgeInfoDTOList.get(0),gameNumber,gameRound);
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
