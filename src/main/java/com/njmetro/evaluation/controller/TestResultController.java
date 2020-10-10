package com.njmetro.evaluation.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.njmetro.evaluation.domain.Student;
import com.njmetro.evaluation.dto.ComputerTestResultExcel;
import com.njmetro.evaluation.service.StudentService;
import com.njmetro.evaluation.service.TestResultService;
import com.njmetro.evaluation.vo.FinalResultVO;
import com.njmetro.evaluation.vo.TestResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zc
 * @since 2020-09-30
 */
@RestController
@RequestMapping("/test-result")
@RequiredArgsConstructor
@Slf4j
public class TestResultController {
    private final TestResultService testResultService;
    private final StudentService studentService;

    @GetMapping("/getTempResult")
    public List<TestResultVO> getTempResult(@RequestParam("gameNumber") Integer gameNumber, @RequestParam("gameRound") Integer gameRound) {
        List<TestResultVO> testResultVOList = testResultService.getTempResult(gameNumber, gameRound);
        List<Integer> studentIdList = testResultService.getStudentIdList(gameNumber, gameRound);
        for (Integer num : studentIdList) {
            List<TestResultVO> testResultVOArrayList = new ArrayList<>();

            for (TestResultVO testResultVO : testResultVOList) {
                if (testResultVO.getStudentId() == num) {
                    testResultVOArrayList.add(testResultVO);//将两个裁判的打分存入该list
                }
            }
            if (Math.abs(testResultVOArrayList.get(0).getResult() - testResultVOArrayList.get(1).getResult()) >= 10) {
                log.info("考生{}得分差的绝对值>=10", testResultVOArrayList.get(0).getStudentName());
                for (TestResultVO testResultVO : testResultVOList) {
                    if (testResultVO.getStudentId() == testResultVOArrayList.get(0).getStudentId()) {
                        testResultVO.setFlag(1);//标记分差大的项
                    }
                }
            }
        }
        int i = 1;
        for (TestResultVO testResultVO : testResultVOList) {
            testResultVO.setId(i);
            i++;
        }
        return testResultVOList;
    }

    /**
     * 最终打分结果汇总
     *
     * @return
     */
    @GetMapping("/getFinalResult")
    public List<FinalResultVO> getFinalResult() {
        log.info("1");
        List<FinalResultVO> finalTempResultVOList = new ArrayList<>();

        for (int gameNumber = 1; gameNumber <= 7; gameNumber++) {
            for (int gameRound = 1; gameRound <= 3; gameRound++) {
                log.info("第{}场：", gameNumber);
                log.info("第{}轮：", gameRound);
                List<TestResultVO> testResultVOList = testResultService.getTempResult(gameNumber, gameRound);
                log.info("获取指定场次的结果：{}", testResultVOList);
                List<Integer> studentIdList = testResultService.getStudentIdList(gameNumber, gameRound);
                log.info("获取指定场次的考生：{}", studentIdList);
                for (Integer num : studentIdList) {
                    List<TestResultVO> testResultVOArrayList = new ArrayList<>();
                    for (TestResultVO testResultVO : testResultVOList) {
                        if (testResultVO.getStudentId() == num) {
                            //将两个裁判的打分存入该list
                            testResultVOArrayList.add(testResultVO);
                        }
                    }
                    double res = (testResultVOArrayList.get(0).getResult() + testResultVOArrayList.get(1).getResult()) / 2;
                    FinalResultVO finalResultVO = new FinalResultVO();
                    finalResultVO.setStudentId(testResultVOArrayList.get(0).getStudentId());
                    finalResultVO.setStudentCode(testResultVOArrayList.get(0).getStudentCode());
                    finalResultVO.setStudentName(testResultVOArrayList.get(0).getStudentName());
                    finalResultVO.setResult(res);
                    finalTempResultVOList.add(finalResultVO);
                }
            }
        }
        List<FinalResultVO> finaResultVOList = new ArrayList<>();//最终展示到前台
        List<Integer> studentIdList = studentService.getStudentIdList();
        log.info("{}", studentIdList);

        for (Integer num : studentIdList) {

            List<FinalResultVO> tempList = new ArrayList<>();
            for (var item : finalTempResultVOList) {
                if (item.getStudentId() == num) {
                    tempList.add(item);
                }
            }
            if (tempList.size() > 0) {
                double finalResult = 0;
                for (FinalResultVO item :
                        tempList) {
                    finalResult += item.getResult();
                }
                log.info("{}", tempList);
                log.info("{}", finalResult);
                log.info("{}", tempList.get(0));
                FinalResultVO finalResultVO = new FinalResultVO();
                finalResultVO.setStudentId(tempList.get(0).getStudentId());
                finalResultVO.setStudentCode(tempList.get(0).getStudentCode());
                finalResultVO.setStudentName(tempList.get(0).getStudentName());
                finalResultVO.setResult(finalResult);
                finaResultVOList.add(finalResultVO);
            }
        }
        return finaResultVOList;
    }

    /**
     * 上传机考得分
     */
    @PostMapping("/readExcel")
    public void readExcel(@RequestParam("file") MultipartFile uploadFile) {
        log.info("上传机考成绩！");
        try {
            //存储并解析Excel
            File file = new File("C:/UploadFile/机考得分表.xlsx");
            uploadFile.transferTo(file);
            ImportParams importParams = new ImportParams();
            importParams.setHeadRows(1);
            List<ComputerTestResultExcel> computerTestResultExcelList = ExcelImportUtil.importExcel(file, ComputerTestResultExcel.class, importParams);
            log.info("{}", computerTestResultExcelList);
            for (ComputerTestResultExcel item : computerTestResultExcelList
            ) {
                UpdateWrapper<Student> studentUpdateWrapper = new UpdateWrapper<>();
                studentUpdateWrapper.eq("code",item.getCode()).set("computer_test_result",item.getCent());
                studentService.update(studentUpdateWrapper);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

