package com.njmetro.evaluation.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njmetro.evaluation.domain.*;
import com.njmetro.evaluation.exception.StudentException;
import com.njmetro.evaluation.service.*;
import com.njmetro.evaluation.util.IpUtil;
import com.njmetro.evaluation.vo.api.StudentInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: evaluation
 * @description: 考生对外接口
 * @author: zc
 * @create: 2020-09-29 08:42
 **/
@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
@Slf4j
public class StudentAPI {
    private final StudentService studentService;
    private final ConfigService configService;
    private final PadService padService;
    private final SeatDrawService seatDrawService;
    private final QuestionDrawService questionDrawService;

    /**
     * @param
     * @return 当前场次和轮次
     */
    @GetMapping("/getConfig")
    public Config getConfig() {
        return configService.getById(1);//获取当前的场次和轮次
    }

    /**
     * @param QRcode 二维码信息
     * @return 考生信息
     */
    @GetMapping("/getStudentInfo")
    public StudentInfo getStudentInfo(@RequestParam("QRcode") String QRcode, HttpServletRequest httpServletRequest) {
        Config config = configService.getById(1);//获取当前的场次和轮次
        String ipAddress = IpUtil.getIpAddr(httpServletRequest);
        if (ipAddress.equals("0:0:0:0:0:0:0:1")) {
            ipAddress = "192.168.96.9";
        }
        QueryWrapper<Pad> padQueryWrapper = new QueryWrapper<>();
        padQueryWrapper.eq("ip", ipAddress);

        Pad pad = padService.getOne(padQueryWrapper);

        log.info("调用次接口的IP:{}", ipAddress);
        QueryWrapper<SeatDraw> seatDrawQueryWrapper = new QueryWrapper<>();
        seatDrawQueryWrapper.eq("seat_id", pad.getSeatId())
                .eq("game_number", config.getGameNumber()).eq("game_round", config.getGameRound());
        SeatDraw seatDraw = seatDrawService.getOne(seatDrawQueryWrapper);//获取当前场次，轮次，赛位上考生信息
        log.info("seatdraw：{}", seatDraw);


        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("two_dimensional_code", QRcode);
        Student student = studentService.getOne(queryWrapper);

        if (student.getId() != seatDraw.getStudentId()) {
            throw new StudentException("您不是当前考位的考生！");
        }
        log.info("考生信息：{}", student);
        StudentInfo studentInfo = new StudentInfo();

        studentInfo.setName(student.getName());
        studentInfo.setCode(student.getCode());
        studentInfo.setGameNumber(config.getGameNumber());
        studentInfo.setGameRound(config.getGameRound());
        return studentInfo;
    }

    /**
     * 获取url
     */
    @GetMapping("/getUrl")
    public String getUrl(@RequestParam("type") Integer type, HttpServletRequest httpServletRequest) {
        Config config = configService.getById(1);//获取当前的场次和轮次
        String ipAddress = IpUtil.getIpAddr(httpServletRequest);
        if (ipAddress.equals("0:0:0:0:0:0:0:1")) {
            ipAddress = "192.168.96.9";
        }
        QueryWrapper<Pad> padQueryWrapper = new QueryWrapper<>();
        padQueryWrapper.eq("ip", ipAddress);
        Pad pad = padService.getOne(padQueryWrapper);//获取对应pad
        QueryWrapper<QuestionDraw> questionDrawQueryWrapper = new QueryWrapper<>();
        questionDrawQueryWrapper.eq("game_number", config.getGameRound()).eq("seat_id", pad.getSeatId());
        questionDrawService.getOne(questionDrawQueryWrapper).getQuestionId();//考题Id
        //todo
        return questionDrawService.getOne(questionDrawQueryWrapper).getQuestionId().toString();

    }

    /**
     *
     * @param type 0表示暂停，1表示开始
     * @param useTime  用时
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/pauseOrStart")
    public Boolean pauseOrStart(@RequestParam("type") Integer type, @RequestParam("useTime") Integer useTime, HttpServletRequest httpServletRequest)
    {
        return true;
    }

    /**
     *
     * @param id   学生ID
     * @param gameNumber  场次
     * @param gameRound   轮次
     * @param useTime     用时
     * @return
     */
    @GetMapping("/finishTest")
    public Boolean finishTest(Integer id,Integer gameNumber,Integer gameRound,Integer useTime)
    {
        return true;
    }

    /**
     * 考生就绪
     * @param id 学生ID
     * @param gameNumber 场次
     * @param gameRound  轮次
     * @return
     */
    @GetMapping("/beReday")
    public Boolean beReday(Integer id,Integer gameNumber,Integer gameRound)
    {
        return true;
    }
}
