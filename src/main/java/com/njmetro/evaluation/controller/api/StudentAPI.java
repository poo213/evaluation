package com.njmetro.evaluation.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.njmetro.evaluation.common.SystemCommon;
import com.njmetro.evaluation.domain.*;
import com.njmetro.evaluation.exception.StudentException;
import com.njmetro.evaluation.exception.TestQuestionException;
import com.njmetro.evaluation.service.*;
import com.njmetro.evaluation.util.IpUtil;
import com.njmetro.evaluation.util.SeatUtil;
import com.njmetro.evaluation.vo.PauseOrStartVO;
import com.njmetro.evaluation.vo.QuestionVO;
import com.njmetro.evaluation.vo.api.StudentInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.njmetro.evaluation.common.SystemCommon.DOWNLOAD_BASE_URL;
import static com.njmetro.evaluation.common.SystemCommon.PDF_URL;

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
//获取缺考状态，seatdraw中获取状态5，表示缺考

    /**
     * @param
     * @return 当前场次和轮次
     */
    @GetMapping("/getConfig")
    public Config getConfig(@RequestAttribute("config") Config config) {
        log.info("getConfig -- 获取到拦截器config {} ", config);
        return config;//获取当前的场次和轮次
    }

    /**
     * 实现考生登录
     *
     * @param QRcode 二维码信息
     * @return 考生信息
     */
    @GetMapping("/getStudentInfo")
    @Transactional
    public StudentInfo getStudentInfo(@RequestParam("QRcode") String QRcode, @RequestAttribute("ip") String ip, @RequestAttribute("config") Config config, @RequestAttribute("pad") Pad pad) {
        log.info("getStudentInfo -- 获取到拦截器pad {} ", pad);
        log.info("getStudentInfo -- 获取到拦截器ip {} ", ip);
        log.info("getStudentInfo -- 获取到拦截器config {} ", config);

        QueryWrapper<SeatDraw> seatDrawQueryWrapper = new QueryWrapper<>();
        seatDrawQueryWrapper.eq("seat_id", pad.getSeatId())
                .eq("game_number", config.getGameNumber()).eq("game_round", config.getGameRound());
        SeatDraw seatDraw = seatDrawService.getOne(seatDrawQueryWrapper);//获取当前场次，轮次，赛位上考生信息
        log.info("赛位信息：{}", seatDraw);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("two_dimensional_code", QRcode);
        Student student = studentService.getOne(queryWrapper);
        log.info("saomade:{}", student);
        if (!student.getId().equals(seatDraw.getStudentId())) {
            throw new StudentException("您不是当前考位的考生！");
        }
        //todo 此处逻辑需要考虑到在考试中的人，可以再次签到
        if (student.getTestDayState() != 2 && student.getTestDayState() != 3) {
            throw new StudentException("备考区签到成功才可以正常登录考试系统！");
        }
        log.info("考生登录信息：{}", student);
        UpdateWrapper<Student> studentUpdateWrapper = new UpdateWrapper<>();
        //登录的时候，考试当天的状态变为3，表示正在考试中
        studentUpdateWrapper.eq("id", student.getId()).set("test_day_state", 3);
        studentService.update(studentUpdateWrapper);
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setName(student.getName());
        studentInfo.setCode(student.getCode());
        studentInfo.setGameNumber(config.getGameNumber());
        studentInfo.setGameRound(config.getGameRound());
        studentInfo.setCompanyName(student.getCompanyName());
        studentInfo.setIdCard(student.getIdCard());
        studentInfo.setUrl(DOWNLOAD_BASE_URL + "idcard/" + student.getIdCard() + ".jpg");
        log.info("考生扫码登录，获取的考试信息：{}", studentInfo);
        return studentInfo;
    }

    /**
     * 获取url 试题的相关信息
     */
    @GetMapping("/getUrl")
    public QuestionVO getUrl(@RequestAttribute("ip") String ip, @RequestAttribute("config") Config config, @RequestAttribute("pad") Pad pad) {
        log.info("getUrl -- 获取到拦截器pad {} ", pad);
        log.info("getUrl -- 获取到拦截器ip {} ", ip);
        log.info("getUrl -- 获取到拦截器config {} ", config);
        if (config.getState().equals(3)) {
            QueryWrapper<QuestionDraw> questionDrawQueryWrapper = new QueryWrapper<>();
            questionDrawQueryWrapper.eq("game_number", config.getGameNumber()).eq("game_type", SeatUtil.getGameTypeByStudentSeatId(pad.getSeatId()));
            log.info("考题id：{}", questionDrawService.getOne(questionDrawQueryWrapper).getQuestionId());//考题Id
            QueryWrapper<TestQuestion> testQuestionQueryWrapper = new QueryWrapper<>();
            testQuestionQueryWrapper.eq("id", questionDrawService.getOne(questionDrawQueryWrapper).getQuestionId());
            UpdateWrapper<SeatDraw> seatDrawUpdateWrapper = new UpdateWrapper<>();
            //得到考题，变成考试中
            seatDrawUpdateWrapper.eq("game_number", config.getGameNumber()).eq("game_round", config.getGameRound()).eq("seat_id", pad.getSeatId()).set("state", 2);
            seatDrawService.update(seatDrawUpdateWrapper);
            TestQuestion testQuestion = testQuestionService.getOne(testQuestionQueryWrapper);
            String url = testQuestion.getUrl();
            //todo
            QuestionVO questionVO = new QuestionVO();
            log.info("获取考题的{}", url);
            questionVO.setUrl(DOWNLOAD_BASE_URL + "pdf/" + url);
            questionVO.setQuestionName(testQuestion.getName());
            questionVO.setReadTime(testQuestion.getReadTime());
            questionVO.setTestTime(testQuestion.getTestTime());
            log.info("获取考题的信息 : {}", questionVO);
            return questionVO;
        } else {
            throw new TestQuestionException("裁判未发题，获取试卷失败");
        }


    }

    /**
     * 获取url 试题的相关信息，暂时不用
     */
    @GetMapping("/getUrlNew")
    public QuestionVO getUrlNew(HttpServletRequest httpServletRequest) {
        //获取当前的场次和轮次
        Config config = configService.getById(1);
        String ipAddress = IpUtil.getIpAddr(httpServletRequest);
        log.info("调用次接口的IP:{}", ipAddress);
        QueryWrapper<Pad> padQueryWrapper = new QueryWrapper<>();
        padQueryWrapper.eq("ip", ipAddress).eq("type", 1);
        //获取对应pad
        Pad pad = padService.getOne(padQueryWrapper);
        QueryWrapper<QuestionDraw> questionDrawQueryWrapper = new QueryWrapper<>();
        questionDrawQueryWrapper.eq("game_number", config.getGameRound()).eq("game_type", SeatUtil.getGameTypeByStudentSeatId(pad.getSeatId()));
        //考题Id
        log.info("考题id：{}", questionDrawService.getOne(questionDrawQueryWrapper).getQuestionId());
        QueryWrapper<TestQuestion> testQuestionQueryWrapper = new QueryWrapper<>();
        testQuestionQueryWrapper.eq("id", questionDrawService.getOne(questionDrawQueryWrapper).getQuestionId());
        TestQuestion testQuestion = testQuestionService.getOne(testQuestionQueryWrapper);
        String url = testQuestion.getUrl();
        log.info("获取考题的{}", url);
        //todo
        QuestionVO questionVO = new QuestionVO();
        questionVO.setUrl(PDF_URL + url);
        questionVO.setQuestionName(testQuestion.getName());
        questionVO.setReadTime(testQuestion.getReadTime());
        questionVO.setTestTime(testQuestion.getTestTime());
        return questionVO;
    }

    /**
     * @param type          0表示暂停，1表示开始
     * @param remainingTime 用时
     * @param
     * @return
     */
    @GetMapping("/pauseOrStart")
    public PauseOrStartVO pauseOrStart(@RequestParam("type") Integer type, @RequestParam("remainingTime") Integer remainingTime, Integer gameNumber, Integer gameRound, @RequestAttribute("config") Config config, @RequestAttribute("pad") Pad pad) {
        log.info("pauseOrStart -- 获取到拦截器pad {} ", pad);
        log.info("pauseOrStart -- 获取到拦截器config {} ", config);
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
            //表示暂停
            pauseOrStartVO.setState(3);
            //返回剩余时间
            return pauseOrStartVO;
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
    @GetMapping("/submit")
    public Boolean finishTest(Integer gameNumber, Integer gameRound, Integer pauseTime,Integer remainingTime, @RequestAttribute("pad") Pad pad, @RequestAttribute("config") Config config) {
        log.info("finishTest -- 获取到拦截器pad {} ", pad);
        log.info("finishTest -- 获取到拦截器config {} ", config);
        UpdateWrapper<SeatDraw> seatDrawUpdateWrapper = new UpdateWrapper<>();
        seatDrawUpdateWrapper.eq("seat_id", pad.getSeatId())
                .eq("game_number", gameNumber)
                .eq("game_round", gameRound)
                .set("use_time", 1200 - remainingTime)
                .set("remaining_time", remainingTime)
                .set("pause_time",pauseTime)
                .set("state", 4);//选手完成考试

        return seatDrawService.update(seatDrawUpdateWrapper);
    }

    /**
     * 考生就绪
     *
     * @param gameNumber 场次
     * @param gameRound  轮次
     * @return
     */
    @GetMapping("/beReady")
    public Boolean beReady(Integer gameNumber, Integer gameRound, @RequestAttribute("pad") Pad pad) {
        Config config = configService.getById(1);
        if (config.getGameNumber().equals(gameNumber) && config.getGameRound().equals(gameRound)) {
            UpdateWrapper<SeatDraw> seatDrawUpdateWrapper = new UpdateWrapper<>();
            seatDrawUpdateWrapper.eq("seat_id", pad.getSeatId())
                    .eq("game_number", gameNumber)
                    .eq("game_round", gameRound)
                    //选手准备就绪
                    .set("state", 1);
            return seatDrawService.update(seatDrawUpdateWrapper);
        } else {
            throw new StudentException("场次和轮次信息与数据库不匹配，核验后再上报就绪");
        }
    }

    @GetMapping("/writeQRcode")
    public Boolean writeQRcode(@RequestParam("qrcode") String qrcode, @RequestAttribute("ip") String ip) {
        log.info("获取到拦截器ip {} ", ip);
        log.info("扫码枪二维码打印 {} ", qrcode);
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("two_dimensional_code", qrcode);
        //此处表示是选手的二维码
        if (studentService.list(studentQueryWrapper).size() > 0) {
            QueryWrapper<CodeState> codeStateQueryWrapper = new QueryWrapper<>();
            //已经扫码，包含确认的和未确认的
            codeStateQueryWrapper.eq("two_dimensional_code", qrcode).eq("ip", ip);
            codeStateQueryWrapper.and(wrapper -> wrapper.eq("state", 0).or().eq("state", 1));
            List<CodeState> codeStateList = codeStateService.list(codeStateQueryWrapper);
            if (codeStateList.size() != 0) {
                log.info("本条扫码信息已存在！");
                return true;
//            throw new StudentException("本条扫码信息已存在！请问重复扫码");
            } else {
                CodeState codeState = new CodeState();
                codeState.setIp(ip);
                codeState.setTwoDimensionalCode(qrcode);
                codeState.setState(0);
                return codeStateService.save(codeState);
            }
        } else {
            log.info("非参赛选手二维码，请勿扫描");
            return false;
        }
    }
}
