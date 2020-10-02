package com.njmetro.evaluation.controller;

import com.njmetro.evaluation.domain.DrawState;
import com.njmetro.evaluation.exception.DrawStateException;
import com.njmetro.evaluation.service.DrawStateService;
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
            // 设置为允许重新抽签
            drawState.setState(true);
            if (!drawStateService.updateById(drawState)) {
                errorInfo.add(id + "号重置失败!");
            }
        }
        if (errorInfo.size() == 0) {
            return true;
        } else {
            throw new DrawStateException("重置失败 ：" + errorInfo);
        }

    }

}

