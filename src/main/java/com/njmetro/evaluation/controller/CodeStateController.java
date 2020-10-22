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
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

    /**
     * 前端轮询调用
     *
     * @param
     */
    @GetMapping("/getInfoAndSign")
    public SignVO getInfoAndSign(HttpServletRequest httpServletRequest) {
        String ipAddress = IpUtil.getIpAddr(httpServletRequest);//获取IP
//        if (ipAddress.equals("0:0:0:0:0:0:0:1")) {
//            ipAddress = "192.168.99.9";
//        }
        //ipAddress = "172.18.1.239";//模拟ip，进入候考区，备考区，离场，3台设备ip
        log.info("调用次接口的IP:{}", ipAddress);
       // log.info("获取到拦截器ip {} ",ip);
        List<String> ipListOne = codeStateService.getIpList(3);
        List<String> ipListTwo = codeStateService.getIpList(4);
        List<String> ipListAway = codeStateService.getIpList(5);
        log.info("候考区扫码枪ip：{}",ipListOne.toString());
        log.info("备考区扫码枪ip：{}",ipListTwo.toString());
        log.info("离开考场扫码枪ip：{}",ipListAway.toString());
        System.out.println(1);
        if (ipListOne.contains(ipAddress)){
            System.out.println(11);
            log.info("进入候考区pad签到:{}", ipAddress);
            QueryWrapper<CodeState> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("ip", ipAddress).eq("state", 0);
            List<CodeState> codeStateList = codeStateService.list(queryWrapper);
            log.info("size:{}", codeStateList.size());
            if (codeStateList.size() > 0)//每次只取一个
            {
                System.out.println(22);
                String qrCode = codeStateList.get(0).getTwoDimensionalCode();//获取二维码
                QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
                studentQueryWrapper.eq("two_dimensional_code", qrCode);
                Student student = studentService.getOne(studentQueryWrapper);//获取考生信息
                if(student==null)
                {
                    return null;
                }
                SignVO signVO = new SignVO();
                signVO.setId(student.getId());
                signVO.setUrl(PHOTO_URL+student.getIdCard()+".png");
                signVO.setName(student.getName());
                signVO.setAge(student.getAge());
                signVO.setIdCard(student.getIdCard());
                signVO.setCode(student.getCode());
                signVO.setCompanyName(student.getCompanyName());
                signVO.setPhone(student.getPhone());
                signVO.setType(1);//进入候考区的pad，状态标记为1
                return signVO;
            } else {
                System.out.println(33);
                return null;
            }
        } else if (ipListTwo.contains(ipAddress)) {
            log.info("进入备考考区pad签到:{}", ipAddress);
            System.out.println(2);
            QueryWrapper<CodeState> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("ip", ipAddress).eq("state", 0);
            List<CodeState> codeStateList = codeStateService.list(queryWrapper);
            if (codeStateList.size() > 0)//每次只取一个
            {
                System.out.println(3);
                String qrCode = codeStateList.get(0).getTwoDimensionalCode();//获取二维码
                QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
                studentQueryWrapper.eq("two_dimensional_code", qrCode);
                Student student = studentService.getOne(studentQueryWrapper);//获取考生信息
                if(student==null)
                {
                    return null;
                }
                SignVO signVO = new SignVO();
                signVO.setId(student.getId());
                signVO.setUrl(PHOTO_URL+student.getIdCard()+".png");
                signVO.setName(student.getName());
                signVO.setAge(student.getAge());
                signVO.setIdCard(student.getIdCard());
                signVO.setCode(student.getCode());
                signVO.setCompanyName(student.getCompanyName());
                signVO.setPhone(student.getPhone());
                signVO.setType(2);//进入候考区的pad，状态标记为2
                return signVO;
            } else {
                return null;
            }
        } else if (ipListAway.contains(ipAddress)) {
            System.out.println(4);
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
                if(student==null)
                {
                    return null;
                }
                SignVO signVO = new SignVO();
                signVO.setUrl(PHOTO_URL+student.getIdCard()+".png");
                signVO.setId(student.getId());
                signVO.setName(student.getName());
                signVO.setAge(student.getAge());
                signVO.setIdCard(student.getIdCard());
                signVO.setCode(student.getCode());
                signVO.setCompanyName(student.getCompanyName());
                signVO.setPhone(student.getPhone());
                signVO.setType(4);//进入候考区的pad，状态标记为3
                return signVO;
            } else {
                return null;
            }
        } else {
            System.out.println(77);
            throw new StudentException("非签到设备");
        }
    }
}

