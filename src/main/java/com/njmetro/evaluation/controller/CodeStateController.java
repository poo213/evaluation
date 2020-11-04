package com.njmetro.evaluation.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njmetro.evaluation.domain.CodeState;
import com.njmetro.evaluation.domain.SeatDraw;
import com.njmetro.evaluation.domain.Student;
import com.njmetro.evaluation.exception.StudentException;
import com.njmetro.evaluation.service.CodeStateService;
import com.njmetro.evaluation.service.ConfigService;
import com.njmetro.evaluation.service.SeatDrawService;
import com.njmetro.evaluation.service.StudentService;
import com.njmetro.evaluation.util.IpUtil;
import com.njmetro.evaluation.util.SeatUtil;
import com.njmetro.evaluation.vo.SignVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.njmetro.evaluation.common.SystemCommon.PHOTO_URL;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zc
 * @since 2020-10-08
 */
@RestController
@RequestMapping("/code-state")
@RequiredArgsConstructor
@Slf4j
public class CodeStateController {

    private final CodeStateService codeStateService;
    private final StudentService studentService;
    private final SeatDrawService seatDrawService;
    private final ConfigService configService;

    /**
     * 前端轮询调用
     *
     * @param
     */
    @GetMapping("/getInfoAndSign")
    public SignVO getInfoAndSign(HttpServletRequest httpServletRequest) {
        String ipAddress = IpUtil.getIpAddr(httpServletRequest);//获取IP
        //ipAddress="172.18.10.56";
        log.info("调用次接口的IP:{}", ipAddress);
        List<String> ipListOne = codeStateService.getIpList(3);
        List<String> ipListTwo = codeStateService.getIpList(4);
        List<String> ipListAway = codeStateService.getIpList(5);
        log.info("候考区扫码枪ip：{}", ipListOne.toString());
        log.info("备考区扫码枪ip：{}", ipListTwo.toString());
        log.info("离开考场扫码枪ip：{}", ipListAway.toString());
        if (ipListOne.contains(ipAddress)) {
            log.info("进入候考区pad签到:{}", ipAddress);
            QueryWrapper<CodeState> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("ip", ipAddress).eq("state", 0);
            List<CodeState> codeStateList = codeStateService.list(queryWrapper);
            if (codeStateList.size() > 0)//每次只取一个
            {
                String qrCode = codeStateList.get(0).getTwoDimensionalCode();//获取二维码
                QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
                studentQueryWrapper.eq("two_dimensional_code", qrCode);
                Student student = studentService.getOne(studentQueryWrapper);//获取考生信息
                if (student == null) {
                    throw new StudentException("没有此考生信息");
                }
                //必须是状态为0的考生，才能签到进入候考区
                if (student.getTestDayState() != 0) {
                    throw new StudentException("此考生必须为第一次进场的考生，才可以扫码！");
                }
                SignVO signVO = new SignVO();
                signVO.setId(student.getId());
                signVO.setUrl(PHOTO_URL + student.getIdCard() + ".png");
                signVO.setName(student.getName());
                signVO.setAge(student.getAge());
                signVO.setIdCard(student.getIdCard());
                signVO.setCode(student.getCode());
                signVO.setCompanyName(student.getCompanyName());
                signVO.setPhone(student.getPhone());
                signVO.setCodeId(codeStateList.get(0).getId());
                signVO.setType(1);//进入候考区的pad，状态标记为1
                return signVO;
            } else {
                System.out.println(11);
                SignVO signVO = new SignVO();
                signVO.setType(1);
                return signVO;
            }
        } else if (ipListTwo.contains(ipAddress)) {
            log.info("进入备考考区pad签到:{}", ipAddress);
            QueryWrapper<CodeState> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("ip", ipAddress).eq("state", 0);
            List<CodeState> codeStateList = codeStateService.list(queryWrapper);
            //每次只取一个
            if (codeStateList.size() > 0) {
                Integer gameNumber = configService.getById(1).getGameNumber();
                //获取二维码
                String qrCode = codeStateList.get(0).getTwoDimensionalCode();
                QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
                studentQueryWrapper.eq("two_dimensional_code", qrCode);
                //获取考生信息
                Student student = studentService.getOne(studentQueryWrapper);
                if (student == null) {
                    throw new StudentException("没有此考生信息");
                }
                if (student.getTestDayState() != 1) {
                    throw new StudentException("必须先签到进会场，才能在此设备上扫码进入备考区！");
                }
                QueryWrapper<SeatDraw> seatDrawQueryWrapper = new QueryWrapper<>();
                //查询下一场，应该 出席的人员
                seatDrawQueryWrapper.eq("student_id", student.getId())
                        .eq("game_round", 1).eq("game_Number", gameNumber + 1);
                //获取考生赛位信息(只需要第一轮的信息)
                SeatDraw seatDraw = seatDrawService.getOne(seatDrawQueryWrapper);
                if (seatDraw == null) {
                    codeStateService.removeById(codeStateList.get(0).getId());
                    throw new StudentException("非本场比赛的考生，请检查自己的考试场次！");
                }
                Integer seatId = seatDraw.getSeatId();

                Integer groupId = seatDraw.getGroupId();
                SignVO signVO = new SignVO();
                signVO.setId(student.getId());
                signVO.setUrl(PHOTO_URL + student.getIdCard() + ".png");
                signVO.setName(student.getName());
                signVO.setAge(student.getAge());
                signVO.setIdCard(student.getIdCard());
                signVO.setCode(student.getCode());
                signVO.setCompanyName(student.getCompanyName());
                signVO.setPhone(student.getPhone());
                signVO.setCodeId(codeStateList.get(0).getId());
                signVO.setType(2);//进入候考区的pad，状态标记为2
                signVO.setSeatInfo(SeatUtil.exchangeGroup(groupId) + "-" + SeatUtil.getTypeNumByStudentSeatId(seatId));
                return signVO;
            } else {
                System.out.println(2);
                SignVO signVO = new SignVO();
                signVO.setType(2);
                return signVO;
            }
        } else if (ipListAway.contains(ipAddress)) {
            log.info("离开考区pad签出:{}", ipAddress);
            QueryWrapper<CodeState> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("ip", ipAddress).eq("state", 0);
            List<CodeState> codeStateList = codeStateService.list(queryWrapper);
            if (codeStateList.size() > 0)//每次只取一个
            {
                String qrCode = codeStateList.get(0).getTwoDimensionalCode();//获取二维码
                QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
                studentQueryWrapper.eq("two_dimensional_code", qrCode);
                Student student = studentService.getOne(studentQueryWrapper);//获取考生信息
                if (student == null) {
                    throw new StudentException("没有此考生信息");
                }
                if (student.getTestDayState() != 3) {
                    throw new StudentException("必须是考试中的考生，才能在此设备上扫码进入候考区！");
                }
                SignVO signVO = new SignVO();
                signVO.setUrl(PHOTO_URL + student.getIdCard() + ".png");
                signVO.setId(student.getId());
                signVO.setName(student.getName());
                signVO.setAge(student.getAge());
                signVO.setIdCard(student.getIdCard());
                signVO.setCode(student.getCode());
                signVO.setCompanyName(student.getCompanyName());
                signVO.setPhone(student.getPhone());
                signVO.setCodeId(codeStateList.get(0).getId());
                signVO.setType(4);//进入候考区的pad，状态标记为3
                return signVO;
            } else {
                System.out.println(4);
                SignVO signVO = new SignVO();
                signVO.setType(4);
                return signVO;
            }
        } else {
            throw new StudentException("非签到设备,扫码设备只包括" + ipListOne.toString() + ipListTwo.toString() + ipListAway.toString());
        }
    }

    @DeleteMapping("/deleteCodeById")
    public Integer deleteCodeById(@RequestParam("id") Integer id) {
        log.info("删除二维码扫码记录通过Id：{}", id);
        if (codeStateService.removeById(id)) {
            //删除成功
            return 1;
        } else {
            return 0;
        }
    }

}

