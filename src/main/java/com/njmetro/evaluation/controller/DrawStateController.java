package com.njmetro.evaluation.controller;

import com.njmetro.evaluation.domain.DrawState;
import com.njmetro.evaluation.exception.DrawStateException;
import com.njmetro.evaluation.service.DrawStateService;
import com.njmetro.evaluation.service.JudgeDrawResultService;
import com.njmetro.evaluation.service.JudgeService;
import com.njmetro.evaluation.service.SeatDrawService;
import com.njmetro.evaluation.vo.DrawStateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zc
 * @since 2020-10-02
 */
@RestController
@RequestMapping("/drawState")
@Slf4j
public class DrawStateController {

    @Autowired
    DrawStateService drawStateService;

    @Autowired
    JudgeService judgeService;

    @Autowired
    JudgeDrawResultService judgeDrawResultService;

    @Autowired
    SeatDrawService seatDrawService;


    /**
     * 返回抽签状态列表
     *
     * @return
     */
    @GetMapping("/getDrawStateList")
    public List<DrawStateVO> getDrawStateList() {
        List<DrawStateVO> result = new ArrayList<>();
        List<DrawState> drawStateList = drawStateService.list();
        for (DrawState drawState : drawStateList) {
            DrawStateVO drawStateVO = new DrawStateVO(drawState);
            result.add(drawStateVO);
        }
        return result;
    }

    /**
     * 将抽签状态重置为可抽签状态
     *
     * @param idList 要重置的ids
     * @return
     */
    @PostMapping("/reset")
    public Boolean reset(@RequestBody List<Integer> idList) {
        log.info("idList :{}", idList);
        List<String> errorInfo = new ArrayList<>();
        for (Integer id : idList) {
            DrawState drawState = drawStateService.getById(id);
            // 重置抽签结果 和状态
            switch (id) {
                case 1:
                    // 更改1 的状态
                    DrawState drawState1 = drawStateService.getById(1);
                    drawState1.setState(true);
                    drawStateService.updateById(drawState1);
                    // 更改 2 的状态
                    seatDrawService.deleteTable();
                    // 更改3 4 状态
                    judgeService.resetTypeAndMaster();
                    // 更改5 的状态
                    judgeDrawResultService.resetJudgeDrawResult();
                    break;
                case 2:
                    // 更改 2 的状态
                    seatDrawService.deleteTable();
                    break;
                case 3:
                    // 更改3 4 状态
                    judgeService.resetTypeAndMaster();
                    // 更改5 的状态
                    judgeDrawResultService.resetJudgeDrawResult();
                    break;
                case 4:
                    // 更改4 状态
                    judgeService.resetMaster();
                    // 更改5 的状态
                    judgeDrawResultService.resetJudgeDrawResult();
                    break;
                case 5:
                    // 更改5 的状态
                    judgeDrawResultService.resetJudgeDrawResult();
                    break;
            }
        }
        if (errorInfo.size() == 0) {
            return true;
        } else {
            throw new DrawStateException("重置失败 ：" + errorInfo);
        }
    }

    /**
     * 根据Id 获取抽签状态
     *
     * @param id id
     * @return
     */
    @GetMapping("/getStateById")
    Integer getStateById(Integer id) {
        log.info("id {}",id);
        Boolean result = drawStateService.getById(id).getState();
        if(result){
            return 1;
        }else {
            return 0;
        }
    }

    /**
     * 获取裁判抽签结果
     *
     * @return
     */
    @GetMapping("/getJudgeState")
    List<Boolean> getJudgeState() {
        List<Boolean> booleanList = new ArrayList<>();
        Boolean state3 = drawStateService.getById(3).getState();
        Boolean state4 = drawStateService.getById(4).getState();
        Boolean state = false;
        if(state3 == true && state4 == true){
            // 都能抽签的时候，说明没有抽签结果可以公示
            state = false;
        }else {
            state = true;
        }
        booleanList.add(state3);
        booleanList.add(state4);
        booleanList.add(state);
        return booleanList;
    }
}

