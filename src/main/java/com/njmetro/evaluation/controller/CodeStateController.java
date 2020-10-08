package com.njmetro.evaluation.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njmetro.evaluation.domain.CodeState;
import com.njmetro.evaluation.domain.Student;
import com.njmetro.evaluation.exception.StudentException;
import com.njmetro.evaluation.service.CodeStateService;
import com.njmetro.evaluation.service.StudentService;
import com.njmetro.evaluation.util.IpUtil;
import com.njmetro.evaluation.vo.SignVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    /**
     * 前端轮询调用
     *
     * @param httpServletRequest
     */
    @GetMapping("/getInfoAndSign")
    public SignVO getInfoAndSign(HttpServletRequest httpServletRequest) {
        String ipAddress = IpUtil.getIpAddr(httpServletRequest);//获取IP
        if (ipAddress.equals("0:0:0:0:0:0:0:1")) {
            ipAddress = "192.168.99.9";
        }
        ipAddress = "192.168.99.9";//模拟ip，进入候考区，备考区，离场，3台设备ip
        log.info("调用次接口的IP:{}", ipAddress);
        if (ipAddress.equals("192.168.99.9")) {
            log.info("进入候考区pad签到:{}", ipAddress);
            QueryWrapper<CodeState> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("type", 1).eq("state", 0);
            List<CodeState> codeStateList = codeStateService.list(queryWrapper);
            if (codeStateList.size() > 0)//每次只取一个
            {
                String qrCode = codeStateList.get(0).getTwoDimensionalCode();//获取二维码
                QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
                studentQueryWrapper.eq("two_dimensional_code", qrCode);
                Student student = studentService.getOne(studentQueryWrapper);//获取考生信息
                SignVO signVO = new SignVO();
                signVO.setId(student.getId());
                signVO.setName(student.getName());
                signVO.setAge(student.getAge());
                signVO.setIdCard(student.getIdCard());
                signVO.setCode(student.getCode());
                signVO.setCompanyName(student.getCompanyName());
                signVO.setPhone(student.getPhone());
                signVO.setType(1);//进入候考区的pad，状态标记为1
                return signVO;
            } else {
                return null;
            }
        } else if (ipAddress.equals("192.168.99.10")) {
            log.info("进入备考区pad签到:{}", ipAddress);
            log.info("进入候考区pad签到:{}", ipAddress);
            QueryWrapper<CodeState> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("type", 1).eq("state", 0);
            List<CodeState> codeStateList = codeStateService.list(queryWrapper);
            if (codeStateList.size() > 0)//每次只取一个
            {
                String qrCode = codeStateList.get(0).getTwoDimensionalCode();//获取二维码
                QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
                studentQueryWrapper.eq("two_dimensional_code", qrCode);
                Student student = studentService.getOne(studentQueryWrapper);//获取考生信息
                SignVO signVO = new SignVO();
                signVO.setId(student.getId());
                signVO.setName(student.getName());
                signVO.setAge(student.getAge());
                signVO.setIdCard(student.getIdCard());
                signVO.setCode(student.getCode());
                signVO.setCompanyName(student.getCompanyName());
                signVO.setPhone(student.getPhone());
                signVO.setType(2);//进入候考区的pad，状态标记为1
                return signVO;
            } else {
                return null;
            }
        } else if (ipAddress.equals("192.168.99.11")) {
            log.info("离开考区pad签出:{}", ipAddress);
            log.info("进入候考区pad签到:{}", ipAddress);
            QueryWrapper<CodeState> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("type", 1).eq("state", 0);
            List<CodeState> codeStateList = codeStateService.list(queryWrapper);
            if (codeStateList.size() > 0)//每次只取一个
            {
                String qrCode = codeStateList.get(0).getTwoDimensionalCode();//获取二维码
                QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
                studentQueryWrapper.eq("two_dimensional_code", qrCode);
                Student student = studentService.getOne(studentQueryWrapper);//获取考生信息
                SignVO signVO = new SignVO();
                signVO.setId(student.getId());
                signVO.setName(student.getName());
                signVO.setAge(student.getAge());
                signVO.setIdCard(student.getIdCard());
                signVO.setCode(student.getCode());
                signVO.setCompanyName(student.getCompanyName());
                signVO.setPhone(student.getPhone());
                signVO.setType(3);//进入候考区的pad，状态标记为1
                return signVO;
            } else {
                return null;
            }
        } else {
            throw new StudentException("非签到设备");
        }
    }
}

