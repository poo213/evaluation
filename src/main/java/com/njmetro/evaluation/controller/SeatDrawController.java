package com.njmetro.evaluation.controller;


import com.njmetro.evaluation.domain.SeatDraw;
import com.njmetro.evaluation.service.SeatDrawService;
import com.njmetro.evaluation.vo.SeatDrawVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zc
 * @since 2020-09-28
 */
@RestController
@RequestMapping("/seat-draw")
@RequiredArgsConstructor
public class SeatDrawController {
    private final SeatDrawService seatDrawService;

    @GetMapping("/getSeatDrawList")
    public List<SeatDrawVO> getSeatDrawList() {
        return seatDrawService.getSeatDraw();
    }
}

