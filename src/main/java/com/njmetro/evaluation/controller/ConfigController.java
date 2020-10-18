package com.njmetro.evaluation.controller;


import com.njmetro.evaluation.domain.Config;
import com.njmetro.evaluation.domain.JudgeDrawResult;
import com.njmetro.evaluation.exception.ConfigException;
import com.njmetro.evaluation.service.ConfigService;
import com.njmetro.evaluation.service.JudgeDrawResultService;
import com.njmetro.evaluation.vo.ConfigVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 主裁配置场次和轮次
 * </p>
 *
 * @author zc
 * @since 2020-09-27
 */
@RestController
@RequestMapping("/config")
@RequiredArgsConstructor
@Slf4j
public class ConfigController {
    private static Integer INDEX_OUT_ROUND = 4;
    private static Integer INDEX_OUT_NUMBER = 8;
    private final ConfigService configService;
    private final JudgeDrawResultService judgeDrawResultService;


    /**
     * 获取本轮比赛状态
     * @return
     */
    @GetMapping("/getState")
    public Integer getState(){
        return configService.getById(1).getState();

    }

    /**
     * 切换下一场
     * @return
     */
    @GetMapping("/doNext")
    public Boolean doNext() {
        Config config = configService.getById(1);
        Integer gameRound = config.getGameRound() + 1;
        if (gameRound.equals(INDEX_OUT_ROUND)) {
            Integer gameNumber = config.getGameNumber() + 1 ;
            if(gameNumber.equals(INDEX_OUT_NUMBER)){
                log.info("全部考试已结束");
                throw new ConfigException("全部考试已结束");
            }else {
                config.setGameNumber(gameNumber);
                config.setGameRound(1);
            }
        }else {
            config.setGameRound(gameRound);
        }
        config.setState(0);
        // 将裁判抽签结果表中的就绪状态全部设为 0
        List<JudgeDrawResult> judgeDrawResultList = judgeDrawResultService.list();
        for(JudgeDrawResult judgeDrawResult : judgeDrawResultList){
            judgeDrawResult.setState(0);
            judgeDrawResultService.updateById(judgeDrawResult);
        }
        return configService.updateById(config);
    }

    /**
     * 下发试题
     * @return
     */
    @GetMapping("/doIssue")
    public Boolean doIssue(){
        Config config = configService.getById(1);
        config.setState(3);
        return configService.updateById(config);
    }

    /**
     * 获取场次和轮次信息
     *
     * @return
     */
    @GetMapping("/getInfo")
    public ConfigVO getInfo() {
        Config config = configService.getById(1);
        ConfigVO configVO = new ConfigVO();
        configVO.setGameNumber(config.getGameNumber());
        configVO.setGameRound(config.getGameRound());
        return configVO;
    }

    /**
     * 配置场次和轮次信息
     *
     * @return
     */
    @PostMapping("/setInfo")
    public Boolean setInfo(@RequestBody Config config) {
        log.info(config.toString());
        return configService.updateById(config);
    }

}

