package com.njmetro.evaluation.controller;


import com.njmetro.evaluation.domain.DrawState;
import com.njmetro.evaluation.service.DrawStateService;
import com.njmetro.evaluation.vo.DrawStateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zc
 * @since 2020-10-02
 */
@RestController
@RequestMapping("/drawState")
public class DrawStateController {

    @Autowired
    DrawStateService drawStateService;

    /**
     * 返回抽签状态列表
     * @return
     */
    @GetMapping("/getDrawStateList")
    public List<DrawStateVO> getDrawStateList(){
        List<DrawStateVO> result = new ArrayList<>();
        List<DrawState> drawStateList = drawStateService.list();
        for(DrawState drawState : drawStateList){
            DrawStateVO drawStateVO = new DrawStateVO(drawState);
            result.add(drawStateVO);
        }
        return result;
    }

}

