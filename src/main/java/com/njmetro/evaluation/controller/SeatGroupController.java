package com.njmetro.evaluation.controller;


import com.njmetro.evaluation.service.SeatGroupService;
import com.njmetro.evaluation.vo.GroupVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zc
 * @since 2020-09-28
 */
@Slf4j
@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class SeatGroupController {


    private final SeatGroupService seatGroupService;

    /*List<GroupVO> getGroups(){

    }*/

}

