package com.njmetro.evaluation.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.njmetro.evaluation.domain.*;
import com.njmetro.evaluation.dto.ComputerTestResultExcelDTO;
import com.njmetro.evaluation.param.student.SaveStudentParam;
import com.njmetro.evaluation.param.student.UpdateStudentParam;
import com.njmetro.evaluation.service.*;
import com.njmetro.evaluation.util.SeatUtil;
import com.njmetro.evaluation.vo.SignVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.njmetro.evaluation.util.KnuthUtil.result;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zc
 * @since 2020-09-21
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
@Slf4j
public class StudentController {


    private final StudentService studentService;
    private final CompanyService companyService;
    private final SeatDrawService seatDrawService;
    private final DrawStateService drawStateService;
    private final CodeStateService codeStateService;

    /**
     * 添加考生信息
     *
     * @param saveStudentParam 添加考生参数
     * @return
     */
    @PostMapping("/saveStudent")
    public Boolean saveStudent(@Valid @RequestBody SaveStudentParam saveStudentParam) {
        log.info("saveStudentParam: {}", saveStudentParam);
        return true;
    }

    /**
     * 根据考生ID 删除考生
     *
     * @param id 考生ID
     * @return
     */
    @PostMapping("/delStudent")
    public Boolean delStudent(@Param("id") Integer id) {
       /* if (true){
            throw new StudentException("delStudent 删除失败");
        }*/
        //return studentService.removeById(id);

        return true;
    }

    /**
     * 修改考生信息
     *
     * @param updateStudentParam 考生信息
     * @return
     */
    @PostMapping("/updateStudent")
    public Boolean updateStudent(@Valid @RequestBody UpdateStudentParam updateStudentParam) {
        log.info("updateStudentParam: {}", updateStudentParam);
        return true;
    }

    /**
     * 获取考生列表
     */
    @GetMapping("/getStudentList")
    public List<Student> getStudentList() {
        return studentService.list();
    }
    /**
     * 获取待进入候考区的考生列表，此处针对已报到考生
     */
    @GetMapping("/getStudentListHaveRegister")
    public List<Student> getStudentListHaveRegister() {
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("sign_state",1);
        return studentService.list(studentQueryWrapper);
    }
    /**
     * 获取已进入候考区的考生列表
     */
    @GetMapping("/getStudentListHaveSignOne")
    public List<Student> getStudentListHaveSignOne() {
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("test_day_state",1);
        return studentService.list(studentQueryWrapper);
    }
    /**
     * 获取离开的考生列表
     */
    @GetMapping("/getStudentListHaveSignAway")
    public List<Student> getStudentListHaveSignAway() {
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("test_day_state",4);
        return studentService.list(studentQueryWrapper);
    }
    /**
     * 获取已进入备考区的考生列表,状态为2，主要返回到签到页面用
     */
    @GetMapping("/getStudentListHaveSignTwo")
    public List<SignVO> getStudentListHaveSignTwo() {
        List<SignVO> signVOList = studentService.getSignVOList();
        for (SignVO item:signVOList
        ) {
            item.setSeatInfo(SeatUtil.getSeatNameById(item.getSeatId()));
        }
        return signVOList;
    }
    /**
     * 获取考试中的考生列表
     */
    @GetMapping("/getStudentListHaveInTest")
    public List<Student> getStudentListHaveInTest() {
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("test_day_state",3);
        return studentService.list(studentQueryWrapper);
    }

    /**
     * 考生签到
     */
    @PostMapping("/signIn")
    public String signIn(@RequestBody List<Integer> idList) {
        List<String> errorInfo = new ArrayList<>();
        for (Integer id : idList) {
            UpdateWrapper<Student> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", id).set("sign_state", "1");
            if (!studentService.update(updateWrapper)) {
                errorInfo.add(id + "号，签到失败!");
            }
        }
        if (errorInfo.size() == 0) {
            return "签到成功";
        } else {
            return errorInfo.toString();
        }

    }

    /**
     * 赛位绑定信息，包含场次，轮次等信息
     */
    @GetMapping("/drawSeat")
    public void drawSeat() {
        QueryWrapper<Company> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("draw_result", 0).orderByAsc("draw_result");//获取已签到地铁

        List<Company> companyList = companyService.list(queryWrapper);
        log.info("签到地铁数量：" + companyList.size());
        List<SeatDraw> seatDrawList = new ArrayList<>();

        for (Company company : companyList)//遍历每个地铁
        {
            Integer group, game_number;//组，场次
            if (company.getDrawResult() % 6 == 1) {
                group = 1;
            } else if (company.getDrawResult() % 6 == 2) {
                group = 2;
            } else if (company.getDrawResult() % 6 == 3) {
                group = 3;
            } else if (company.getDrawResult() % 6 == 4) {
                group = 4;
            } else if (company.getDrawResult() % 6 == 5) {
                group = 5;
            } else {
                group = 6;
            }
            if (company.getDrawResult() >= 1 && company.getDrawResult() <= 6) {
                game_number = 1;
            } else if (company.getDrawResult() >= 7 && company.getDrawResult() <= 12) {
                game_number = 2;
            } else if (company.getDrawResult() >= 13 && company.getDrawResult() <= 18) {
                game_number = 3;
            } else if (company.getDrawResult() >= 19 && company.getDrawResult() <= 24) {
                game_number = 4;
            } else if (company.getDrawResult() >= 25 && company.getDrawResult() <= 30) {
                game_number = 5;
            } else if (company.getDrawResult() >= 31 && company.getDrawResult() <= 36) {
                game_number = 6;
            } else {
                game_number = 7;
            }

            QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
            //获取考生
            studentQueryWrapper.eq("company_name", company.getName()).eq("sign_state","1");
            List<Student> studentList = studentService.list(studentQueryWrapper);//获取地铁的参赛选手
            log.info("地铁编号：{}", company.getDrawResult());
            log.info("考生数目：{}", studentList.size());
            //对参赛选手进行遍历，分配轮次
            log.info("{}", company.getName() + studentList);
            Integer[] baseArray = new Integer[studentList.size()];//创建数组
            for (int i = 0; i < baseArray.length; i++) {
                baseArray[i] = i + 1;
            }
            baseArray = result(baseArray);//洗牌算法
            int i = 0;
            for (Student student : studentList) {
                for (int j = 0; j <= 2; j++) {
                    SeatDraw seatDraw = new SeatDraw();
                    int m = baseArray[i] + j;
                    if (m > 3) {
                        m = m % 3;
                    }
                    seatDraw.setCompanyId(company.getId());
                    seatDraw.setStudentId(student.getId());
                    seatDraw.setSeatId((group - 1) * 3 + m);
                    seatDraw.setGameNumber(game_number);//场次
                    seatDraw.setGameRound(j + 1);
                    seatDraw.setGroupId(group);
                    seatDraw.setState(0);
                    seatDraw.setDrawName("主裁");
                    seatDraw.setDrawTime(LocalDateTime.now());
                    seatDrawList.add(seatDraw);
                }
                i++;
            }

        }
        // 修改抽签状态
        DrawState drawState = drawStateService.getById(2);
        drawState.setState(false);
        drawStateService.updateById(drawState);

        seatDrawService.cleanAll();
        seatDrawService.saveBatch(seatDrawList);

    }

    /**
     * 签到进入候考区
     * @param idList  选中的选手
     * @return
     */
    @PostMapping("/signInOne")
    @Transactional
    public String signInOne(@RequestBody List<Integer> idList) {

        List<String> errorInfo = new ArrayList<>();
        for (Integer id : idList) {
            UpdateWrapper<Student> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", id).set("test_day_state", "1");
            if (!studentService.update(updateWrapper)) {
                errorInfo.add(id + "号，签到失败!");
            }else {
                Student student = studentService.getById(id);
                log.info("student详情:{}",student);
                if(student !=null){
                    String qrCode= student.getTwoDimensionalCode();
                    UpdateWrapper<CodeState> codeStateUpdateWrapper =new UpdateWrapper<>();
                    codeStateUpdateWrapper.eq("two_dimensional_code",qrCode).set("state",1);
                    codeStateService.update(codeStateUpdateWrapper);
                }
            }
        }
        if (errorInfo.size() == 0) {
            return "签到成功";
        } else {
            return errorInfo.toString();
        }
    }

    /**
     * 签到进入备考区
     * @param idList  选中的选手
     * @return
     */
    @PostMapping("/signInTwo")
    public String signInTwo(@RequestBody List<Integer> idList) {
        List<String> errorInfo = new ArrayList<>();
        for (Integer id : idList) {
            UpdateWrapper<Student> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", id).set("test_day_state", "2");
            if (!studentService.update(updateWrapper)) {
                errorInfo.add(id + "号，签到失败!");
            }else {
                Student student = studentService.getById(id);
                if(student !=null){
                    String qrCode= student.getTwoDimensionalCode();
                    UpdateWrapper<CodeState> codeStateUpdateWrapper =new UpdateWrapper<>();
                    codeStateUpdateWrapper.eq("two_dimensional_code",qrCode).set("state",1);
                    codeStateService.update(codeStateUpdateWrapper);
                }
            }
        }
        if (errorInfo.size() == 0) {
            return "签到成功";
        } else {
            return errorInfo.toString();
        }
    }
    /**
     * 签到进入备考区
     * @param idList  选中的选手
     * @return
     */
    @PostMapping("/signAway")
    public String signAway(@RequestBody List<Integer> idList) {
        List<String> errorInfo = new ArrayList<>();
        for (Integer id : idList) {
            UpdateWrapper<Student> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", id).set("test_day_state", "4");
            if (!studentService.update(updateWrapper)) {
                errorInfo.add(id + "号，签离失败!");
            }else {
                Student student = studentService.getById(id);
                System.out.println(student);
                log.info("student详情:{}",student);
                if(student !=null){
                    String qrCode= student.getTwoDimensionalCode();
                    log.info("qrcode:{}",qrCode);
                    UpdateWrapper<CodeState> codeStateUpdateWrapper =new UpdateWrapper<>();
                    codeStateUpdateWrapper.eq("two_dimensional_code",qrCode).set("state",1);
                    codeStateService.update(codeStateUpdateWrapper);
                }
            }
        }
        if (errorInfo.size() == 0) {
            return "签离成功";
        } else {
            return errorInfo.toString();
        }
    }

    /**
     * 上传考生数据
     */
    @PostMapping("/uploadStudent")
    public void uploadStudent(@RequestParam("file") MultipartFile uploadFile) {
        log.info("上传考生数据！");
        try {
            //存储并解析Excel
            File file = new File("C:/UploadFile/考生信息表.xlsx");
            uploadFile.transferTo(file);
            ImportParams importParams = new ImportParams();
            importParams.setHeadRows(1);
            List<Student> studentList = ExcelImportUtil.importExcel(file, Student.class, importParams);
            studentService.saveBatch(studentList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

