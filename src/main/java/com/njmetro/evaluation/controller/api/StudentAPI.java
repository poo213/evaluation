package com.njmetro.evaluation.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.njmetro.evaluation.common.SystemCommon;
import com.njmetro.evaluation.domain.*;
import com.njmetro.evaluation.exception.StudentException;
import com.njmetro.evaluation.service.*;
import com.njmetro.evaluation.util.IpUtil;
import com.njmetro.evaluation.util.SeatUtil;
import com.njmetro.evaluation.vo.PauseOrStartVO;
import com.njmetro.evaluation.vo.api.StudentInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.njmetro.evaluation.common.SystemCommon.DOWNLOAD_BASE_URL;

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
    private final TestQuestionService testQuestionService;
    private final PauseRecordService pauseRecordService;
    private final CodeStateService codeStateService;

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
    @Transactional
    public StudentInfo getStudentInfo(@RequestParam("QRcode") String QRcode, HttpServletRequest httpServletRequest) {
        Config config = configService.getById(1);//获取当前的场次和轮次
        String ipAddress = IpUtil.getIpAddr(httpServletRequest);
        if (ipAddress.equals("0:0:0:0:0:0:0:1")) {
            ipAddress = "192.168.96.9";
        }
        ipAddress = "192.168.96.9";
        QueryWrapper<Pad> padQueryWrapper = new QueryWrapper<>();
        padQueryWrapper.eq("ip", ipAddress).eq("type", 1);

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
        studentInfo.setCompanyName(student.getCompanyName());
        studentInfo.setIdCard(student.getIdCard());
        studentInfo.setUrl(DOWNLOAD_BASE_URL+"idcard/"+student.getIdCard()+".jpg");
        System.out.println(DOWNLOAD_BASE_URL+"idcard/"+student.getIdCard()+".jpg");
        return studentInfo;
    }

    /**
     * 获取url
     */
    @GetMapping("/getUrl")
    public String getUrl(HttpServletRequest httpServletRequest) {
        Config config = configService.getById(1);//获取当前的场次和轮次
        String ipAddress = IpUtil.getIpAddr(httpServletRequest);
        if (ipAddress.equals("0:0:0:0:0:0:0:1")) {
            ipAddress = "192.168.96.9";
        }
        ipAddress = "192.168.96.9";
        log.info("调用次接口的IP:{}", ipAddress);
        QueryWrapper<Pad> padQueryWrapper = new QueryWrapper<>();
        padQueryWrapper.eq("ip", ipAddress).eq("type", 1);
        Pad pad = padService.getOne(padQueryWrapper);//获取对应pad
        System.out.println(pad);
        QueryWrapper<QuestionDraw> questionDrawQueryWrapper = new QueryWrapper<>();
        questionDrawQueryWrapper.eq("game_number", config.getGameRound()).eq("game_type", SeatUtil.getGameTypeByStudentSeatId(pad.getSeatId()));
        log.info("考题id：{}", questionDrawService.getOne(questionDrawQueryWrapper).getQuestionId());//考题Id
        QueryWrapper<TestQuestion> testQuestionQueryWrapper = new QueryWrapper<>();
        testQuestionQueryWrapper.eq("id", questionDrawService.getOne(questionDrawQueryWrapper).getQuestionId());
        String url = testQuestionService.getOne(testQuestionQueryWrapper).getUrl();
        System.out.println(url);
        //todo
        return DOWNLOAD_BASE_URL + url;
    }

    /**
     * @param type               0表示暂停，1表示开始
     * @param remainingTime      用时
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/pauseOrStart")
    public PauseOrStartVO pauseOrStart(@RequestParam("type") Integer type, @RequestParam("remainingTime") Integer remainingTime, Integer gameNumber, Integer gameRound, HttpServletRequest httpServletRequest) {
        //获取当前的场次和轮次
        //Config config = configService.getById(1);
        String ipAddress = IpUtil.getIpAddr(httpServletRequest);
        if (ipAddress.equals("0:0:0:0:0:0:0:1")) {
            ipAddress = "192.168.96.9";
        }
        ipAddress = "192.168.96.9";
        QueryWrapper<Pad> padQueryWrapper = new QueryWrapper<>();
        padQueryWrapper.eq("ip", ipAddress).eq("type", 1);
        Pad pad = padService.getOne(padQueryWrapper);
        log.info("调用暂停or开始接口的IP:{}", ipAddress);
        QueryWrapper<SeatDraw> seatDrawQueryWrapper = new QueryWrapper<>();
        seatDrawQueryWrapper.eq("seat_id", pad.getSeatId())
                .eq("game_number", gameNumber)
                .eq("game_round", gameRound);
        SeatDraw seatDraw = seatDrawService.getOne(seatDrawQueryWrapper);
        //暂停
        if (type == 0) {
            seatDraw.setRemainingTime(remainingTime);
            //比赛暂停，记录比赛剩余时间
            seatDraw.setState(3);
            PauseRecord pauseRecord = new PauseRecord();
            pauseRecord.setSeatDrawId(seatDraw.getId());
            pauseRecord.setType(0);
            pauseRecordService.save(pauseRecord);
            seatDrawService.updateById(seatDraw);
            PauseOrStartVO pauseOrStartVO = new PauseOrStartVO();
            pauseOrStartVO.setRemainingTime(remainingTime);
            pauseOrStartVO.setState(3);//表示暂停
            return pauseOrStartVO;//返回剩余时间
        } else {//开始
            PauseRecord pauseRecord = new PauseRecord();
            pauseRecord.setSeatDrawId(seatDraw.getId());
            pauseRecord.setType(1);
            pauseRecordService.save(pauseRecord);
            //恢复比赛，切换为考试中 状态2
            seatDraw.setState(2);
            seatDrawService.updateById(seatDraw);

            PauseOrStartVO pauseOrStartVO = new PauseOrStartVO();
            //返回剩余时间
            pauseOrStartVO.setRemainingTime(seatDraw.getRemainingTime());
            pauseOrStartVO.setState(2);
            return pauseOrStartVO;
        }
    }

    /**
     * @param gameNumber 场次
     * @param gameRound  轮次
     * @return
     */
    @GetMapping("/finishTest")
    public Boolean finishTest(Integer gameNumber, Integer gameRound, Integer remainingTime, HttpServletRequest httpServletRequest) {
        String ipAddress = IpUtil.getIpAddr(httpServletRequest);
        if (ipAddress.equals("0:0:0:0:0:0:0:1")) {
            ipAddress = "192.168.96.9";
        }
        ipAddress = "192.168.96.9";
        QueryWrapper<Pad> padQueryWrapper = new QueryWrapper<>();
        padQueryWrapper.eq("ip", ipAddress).eq("type", 1);
        Pad pad = padService.getOne(padQueryWrapper);
        log.info("调用次接口的IP:{}", ipAddress);
        UpdateWrapper<SeatDraw> seatDrawUpdateWrapper = new UpdateWrapper<>();
        seatDrawUpdateWrapper.eq("seat_id", pad.getSeatId())
                .eq("game_number", gameNumber)
                .eq("game_round", gameRound)
                .set("use_time", 1200 - remainingTime)
                .set("state", 4);//选手准备就绪
        return seatDrawService.update(seatDrawUpdateWrapper);
    }

    /**
     * 考生就绪
     *
     * @param id         学生ID
     * @param gameNumber 场次
     * @param gameRound  轮次
     * @return
     */
    @GetMapping("/beReday")
    public Boolean beReday(Integer id, Integer gameNumber, Integer gameRound, HttpServletRequest httpServletRequest) {
//        Config config = configService.getById(1);//获取当前的场次和轮次
        String ipAddress = IpUtil.getIpAddr(httpServletRequest);
        if (ipAddress.equals("0:0:0:0:0:0:0:1")) {
            ipAddress = "192.168.96.9";
        }
        ipAddress = "192.168.96.9";
        QueryWrapper<Pad> padQueryWrapper = new QueryWrapper<>();
        padQueryWrapper.eq("ip", ipAddress).eq("type", 1);

        Pad pad = padService.getOne(padQueryWrapper);

        log.info("调用次接口的IP:{}", ipAddress);
        UpdateWrapper<SeatDraw> seatDrawUpdateWrapper = new UpdateWrapper<>();
        seatDrawUpdateWrapper.eq("seat_id", pad.getSeatId())
                .eq("game_number", gameNumber)
                .eq("game_round", gameRound)
                .set("state", 1);//选手准备就绪
        return seatDrawService.update(seatDrawUpdateWrapper);
    }

    @GetMapping("/writeQRcode")
    public Boolean writeQRcode(@RequestParam("qrcode") String qrcode, HttpServletRequest httpServletRequest) {
        String ipAddress = IpUtil.getIpAddr(httpServletRequest);
//        if (ipAddress.equals("0:0:0:0:0:0:0:1")) {
//            ipAddress = "192.168.96.9";
//        }
//        ipAddress = "192.168.96.9";
        QueryWrapper<CodeState> codeStateQueryWrapper = new QueryWrapper<>();
        codeStateQueryWrapper.eq("two_dimensional_code", qrcode).eq("state", 0).eq("ip", ipAddress);
        List<CodeState> codeStateList = codeStateService.list(codeStateQueryWrapper);
        if (codeStateList.size() != 0) {
            log.info("本条扫码信息已存在！");
            return true;
        } else {
            CodeState codeState = new CodeState();
            codeState.setIp(ipAddress);
            codeState.setTwoDimensionalCode(qrcode);
            codeState.setState(0);
            return codeStateService.save(codeState);
        }

    }
}
