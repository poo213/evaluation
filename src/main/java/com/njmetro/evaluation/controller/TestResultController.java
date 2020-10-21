package com.njmetro.evaluation.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.njmetro.evaluation.domain.Config;
import com.njmetro.evaluation.domain.SeatDraw;
import com.njmetro.evaluation.domain.Student;
import com.njmetro.evaluation.domain.TestResult;
import com.njmetro.evaluation.dto.ComputerTestResultExcelDTO;
import com.njmetro.evaluation.exception.StudentException;
import com.njmetro.evaluation.service.ConfigService;
import com.njmetro.evaluation.service.SeatDrawService;
import com.njmetro.evaluation.service.StudentService;
import com.njmetro.evaluation.service.TestResultService;
import com.njmetro.evaluation.util.StatisticUtil;
import com.njmetro.evaluation.vo.FinalResultVO;
import com.njmetro.evaluation.vo.TestResultDetailVO;
import com.njmetro.evaluation.vo.TestResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final SeatDrawService seatDrawService;
    private final ConfigService configService;

    /**
     * 获取校验成绩的结果，两个裁判的打分结果的对比
     *
     * @param gameNumber
     * @param gameRound
     * @return
     */
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
            if (testResultVOArrayList.get(0).getResult().subtract(testResultVOArrayList.get(1).getResult()).abs().compareTo(new BigDecimal("10")) > -1) {
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
     * 获取校验成绩的结果，两个裁判的打分结果的对比
     *
     * @param gameNumber
     * @param gameRound
     * @return
     */
    @GetMapping("/getResultByStudentCode")
    public List<TestResultDetailVO> getResultByStudentCode(@RequestParam("gameNumber") Integer gameNumber, @RequestParam("gameRound") Integer gameRound, @RequestParam("studentId") Integer studentId) {
        System.out.println("game_number = " + gameNumber + ", game_round = " + gameRound + ", student_id = " + studentId);
//        QueryWrapper<TestResult> testResultQueryWrapper = new QueryWrapper<>();
//        testResultQueryWrapper.eq("game_number",gameNumber)
//                              .eq("game_round",gameRound)
//                .eq("student_id",studentId).orderByAsc("question_standard_id");
//
//
//
//        return testResultService.list(testResultQueryWrapper);
        return testResultService.getTestResultDetail(gameNumber, gameRound, studentId);
    }

    /**
     * 主裁校正成绩id
     *
     * @param id
     * @param cent
     * @return
     */
    @GetMapping("/editCent")
    public Boolean getResultByStudentCode(@RequestParam("id") Integer id, @RequestParam("cent") Double cent) {
        log.info("主裁校正成绩id：{}", id);
        log.info("主裁校正成绩分值：{}", cent);
        UpdateWrapper<TestResult> testResultUpdateWrapper = new UpdateWrapper<>();
        testResultUpdateWrapper.eq("id", id).set("cent", cent);
        return testResultService.update(testResultUpdateWrapper);
    }

    /**
     * 最终打分结果汇总
     *
     * @return
     */
    @GetMapping("/getFinalResult")
    public List<FinalResultVO> getFinalResult() throws JsonProcessingException {

        //此处用于只能获取已进行的场次的成绩汇总
        Config config = configService.getById(1);
        int tempNumber = 1;
        if (config.getGameNumber() == 1 && config.getGameRound() != 3) {
            throw new StudentException("一场3轮比赛都结束才能对分数进行统计！");
        }
        if (config.getGameNumber() > 1 && config.getGameRound() != 3) {
            tempNumber = config.getGameNumber() - 1;
        }
        //临时表，得到所有场次和轮次的结果 ，理论上一个考生有3个结果分别对应三轮，在这个list里
        List<FinalResultVO> finalTempResultVOList = new ArrayList<>();

        for (int gameNumber = 1; gameNumber <= tempNumber; gameNumber++) {
            for (int gameRound = 1; gameRound <= 3; gameRound++) {
                log.info("第{}场：", gameNumber);
                log.info("第{}轮：", gameRound);
                // 获取指定场次的所有考生的考试结果,通过裁判区分，包含两个裁判的打分
                List<TestResultVO> testResultVOList = testResultService.getTempResult(gameNumber, gameRound);
                log.info("获取指定场次的所有考生的考试结果：{}", testResultVOList);
                List<Integer> studentIdList = testResultService.getStudentIdList(gameNumber, gameRound);
                log.info("获取指定场次的考生：{}", studentIdList);
                for (Integer num : studentIdList) {
                    log.info("计算得分时遍历考生id：{}", num);
                    //将两个裁判的打分存入该list
                    List<TestResultVO> testResultVOArrayList = new ArrayList<>();
                    for (TestResultVO testResultVO : testResultVOList) {
                        if (testResultVO.getStudentId() == num) {
                            //将两个裁判的打分存入该list，这里不包含时间分
                            QueryWrapper<SeatDraw> seatDrawQueryWrapper = new QueryWrapper<>();
                            seatDrawQueryWrapper.eq("game_number", gameNumber)
                                    .eq("game_round", gameRound).eq("student_id", num);
                            List<SeatDraw> seatDrawList = seatDrawService.list(seatDrawQueryWrapper);
                            log.info("查询考生seatdraw表中的记录，查用时间：", seatDrawList);
                            if (seatDrawList.size() == 0) {
                                throw new StudentException("请检查赛位抽签表" + gameNumber + "场" + gameRound + "轮的考生" + testResultVO.getStudentName() + "没有比赛用时！");
                            }
                            Integer useTime = 0;
                            //获取到指定场次轮次下 的比赛用时
                            if (seatDrawList.size() == 1) {
                                useTime = seatDrawList.get(0).getUseTime();
                                log.info("用时：{}", useTime);
                                //时间分赋值
                                testResultVO.setTimeCent(StatisticUtil.getTimeCent(useTime, testResultVO.getResult()));
                            }
                            testResultVOArrayList.add(testResultVO);
                        }
                    }
                    System.out.println(testResultVOArrayList);
                    //两个裁判打分取中值，包含时间分
                    BigDecimal res = testResultVOArrayList.get(0).getResult().add(testResultVOArrayList.get(0).getTimeCent()).add(testResultVOArrayList.get(1).getResult()).add(testResultVOArrayList.get(1).getTimeCent()).divide(new BigDecimal("2"));
                    //BigDecimal res = new BigDecimal((testResultVOArrayList.get(0).getResult()+ testResultVOArrayList.get(0).getTimeCent()+ testResultVOArrayList.get(1).getResult()+ testResultVOArrayList.get(1).getTimeCent()) / 2;
                    FinalResultVO finalResultVO = new FinalResultVO();
                    finalResultVO.setStudentId(testResultVOArrayList.get(0).getStudentId());
                    finalResultVO.setStudentCode(testResultVOArrayList.get(0).getStudentCode());
                    finalResultVO.setStudentName(testResultVOArrayList.get(0).getStudentName());
                    finalResultVO.setCompanyName(testResultVOArrayList.get(0).getCompanyName());
                    finalResultVO.setResult(res);
                    finalTempResultVOList.add(finalResultVO);
                }
            }
        }
        log.info("最终的临时结果，包含（所有）一个考生的三个项目的成绩：finalTempResultVOList{}", finalTempResultVOList);
        List<FinalResultVO> finaResultVOList = new ArrayList<>();//最终展示到前台
        List<Integer> studentIdList = studentService.getStudentIdList();
        log.info("所有考生列表：{}", studentIdList);
        // 将三个结果汇总
        for (Integer num : studentIdList) {

            List<FinalResultVO> tempList = new ArrayList<>();
            for (var item : finalTempResultVOList) {
                if (item.getStudentId() == num) {
                    tempList.add(item);
                }
            }
            if (tempList.size() > 0) {
                BigDecimal finalResult = new BigDecimal("0");
                for (FinalResultVO item :
                        tempList) {
                    finalResult = finalResult.add(item.getResult());
                }
                log.info("包含指定一个考生的，三个最终临时结果{}", tempList);
                log.info("三项实操之和：{}", finalResult);
                FinalResultVO finalResultVO = new FinalResultVO();
                finalResultVO.setStudentId(tempList.get(0).getStudentId());
                finalResultVO.setStudentCode(tempList.get(0).getStudentCode());
                finalResultVO.setStudentName(tempList.get(0).getStudentName());
                finalResultVO.setCompanyName(tempList.get(0).getCompanyName());
                finalResultVO.setResult(finalResult.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP));
                finaResultVOList.add(finalResultVO);
            }
        }
        //将机考成绩一起汇总进来
        for (FinalResultVO item :
                finaResultVOList) {
            Student student = studentService.getById(item.getStudentId());
            if (student.getComputerTestResult() == null) {
                throw new StudentException(student.getName() + "的机考成绩未导入！");
            }
            //机考成绩
            item.setComputerTestResult(new BigDecimal(student.getComputerTestResult()));
            item.setComprehensiveResult((new BigDecimal(student.getComputerTestResult())).multiply(new BigDecimal("0.3")).add(item.getResult().multiply(new BigDecimal("0.7"))).setScale(2, RoundingMode.HALF_UP));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(finaResultVOList);
        log.info("得分结果json字符串：{}", json);
        return finaResultVOList;
    }    /**
     * 最终打分结果导出
     *
     * @return
     */
    @GetMapping("/exportExcel")
    public ResponseEntity<byte[]> exportFile() throws IOException {
        //此处用于只能获取已进行的场次的成绩汇总
        Config config = configService.getById(1);
        int tempNumber = 1;
        if (config.getGameNumber() == 1 && config.getGameRound() != 3) {
            throw new StudentException("一场3轮比赛都结束才能对分数进行统计！");
        }
        if (config.getGameNumber() > 1 && config.getGameRound() != 3) {
            tempNumber = config.getGameNumber() - 1;
        }
        //临时表，得到所有场次和轮次的结果 ，理论上一个考生有3个结果分别对应三轮，在这个list里
        List<FinalResultVO> finalTempResultVOList = new ArrayList<>();

        for (int gameNumber = 1; gameNumber <= tempNumber; gameNumber++) {
            for (int gameRound = 1; gameRound <= 3; gameRound++) {
                log.info("第{}场：", gameNumber);
                log.info("第{}轮：", gameRound);
                // 获取指定场次的所有考生的考试结果,通过裁判区分，包含两个裁判的打分
                List<TestResultVO> testResultVOList = testResultService.getTempResult(gameNumber, gameRound);
                log.info("获取指定场次的所有考生的考试结果：{}", testResultVOList);
                List<Integer> studentIdList = testResultService.getStudentIdList(gameNumber, gameRound);
                log.info("获取指定场次的考生：{}", studentIdList);
                for (Integer num : studentIdList) {
                    log.info("计算得分时遍历考生id：{}", num);
                    //将两个裁判的打分存入该list
                    List<TestResultVO> testResultVOArrayList = new ArrayList<>();
                    for (TestResultVO testResultVO : testResultVOList) {
                        if (testResultVO.getStudentId() == num) {
                            //将两个裁判的打分存入该list，这里不包含时间分
                            QueryWrapper<SeatDraw> seatDrawQueryWrapper = new QueryWrapper<>();
                            seatDrawQueryWrapper.eq("game_number", gameNumber)
                                    .eq("game_round", gameRound).eq("student_id", num);
                            List<SeatDraw> seatDrawList = seatDrawService.list(seatDrawQueryWrapper);
                            log.info("查询考生seatdraw表中的记录，查用时间：", seatDrawList);
                            if (seatDrawList.size() == 0) {
                                throw new StudentException("请检查赛位抽签表" + gameNumber + "场" + gameRound + "轮的考生" + testResultVO.getStudentName() + "没有比赛用时！");
                            }
                            Integer useTime = 0;
                            //获取到指定场次轮次下 的比赛用时
                            if (seatDrawList.size() == 1) {
                                useTime = seatDrawList.get(0).getUseTime();
                                log.info("用时：{}", useTime);
                                //时间分赋值
                                testResultVO.setTimeCent(StatisticUtil.getTimeCent(useTime, testResultVO.getResult()));
                            }
                            testResultVOArrayList.add(testResultVO);
                        }
                    }
                    System.out.println(testResultVOArrayList);
                    //两个裁判打分取中值，包含时间分
                    BigDecimal res = testResultVOArrayList.get(0).getResult().add(testResultVOArrayList.get(0).getTimeCent()).add(testResultVOArrayList.get(1).getResult()).add(testResultVOArrayList.get(1).getTimeCent()).divide(new BigDecimal("2"));
                    //BigDecimal res = new BigDecimal((testResultVOArrayList.get(0).getResult()+ testResultVOArrayList.get(0).getTimeCent()+ testResultVOArrayList.get(1).getResult()+ testResultVOArrayList.get(1).getTimeCent()) / 2;
                    FinalResultVO finalResultVO = new FinalResultVO();
                    finalResultVO.setStudentId(testResultVOArrayList.get(0).getStudentId());
                    finalResultVO.setStudentCode(testResultVOArrayList.get(0).getStudentCode());
                    finalResultVO.setStudentName(testResultVOArrayList.get(0).getStudentName());
                    finalResultVO.setCompanyName(testResultVOArrayList.get(0).getCompanyName());
                    finalResultVO.setResult(res);
                    finalTempResultVOList.add(finalResultVO);
                }
            }
        }
        log.info("最终的临时结果，包含（所有）一个考生的三个项目的成绩：finalTempResultVOList{}", finalTempResultVOList);
        List<FinalResultVO> finaResultVOList = new ArrayList<>();//最终展示到前台
        List<Integer> studentIdList = studentService.getStudentIdList();
        log.info("所有考生列表：{}", studentIdList);
        // 将三个结果汇总
        for (Integer num : studentIdList) {

            List<FinalResultVO> tempList = new ArrayList<>();
            for (var item : finalTempResultVOList) {
                if (item.getStudentId() == num) {
                    tempList.add(item);
                }
            }
            if (tempList.size() > 0) {
                BigDecimal finalResult = new BigDecimal("0");
                for (FinalResultVO item :
                        tempList) {
                    finalResult = finalResult.add(item.getResult());
                }
                log.info("包含指定一个考生的，三个最终临时结果{}", tempList);
                log.info("三项实操之和：{}", finalResult);
                FinalResultVO finalResultVO = new FinalResultVO();
                finalResultVO.setStudentId(tempList.get(0).getStudentId());
                finalResultVO.setStudentCode(tempList.get(0).getStudentCode());
                finalResultVO.setStudentName(tempList.get(0).getStudentName());
                finalResultVO.setCompanyName(tempList.get(0).getCompanyName());
                finalResultVO.setResult(finalResult.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP));
                finaResultVOList.add(finalResultVO);
            }
        }
        //将机考成绩一起汇总进来
        for (FinalResultVO item :
                finaResultVOList) {
            Student student = studentService.getById(item.getStudentId());
            if (student.getComputerTestResult() == null) {
                throw new StudentException(student.getName() + "的机考成绩未导入！");
            }
            //机考成绩
            item.setComputerTestResult(new BigDecimal(student.getComputerTestResult()));
            item.setComprehensiveResult((new BigDecimal(student.getComputerTestResult())).multiply(new BigDecimal("0.3")).add(item.getResult().multiply(new BigDecimal("0.7"))).setScale(2, RoundingMode.HALF_UP));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(finaResultVOList);
        log.info("得分结果json字符串：{}", json);

        TemplateExportParams params = new TemplateExportParams(
                "c:/结果汇总.xlsx");
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
        for (FinalResultVO item:finaResultVOList
             ) {
            Map<String, String> lm = new HashMap<String, String>();
            lm.put("studentId",item.getStudentId()+"");
            lm.put("studentName",item.getStudentName());
            lm.put("companyName",item.getCompanyName());
            lm.put("result",item.getResult()+"");
            lm.put("computerTestResult",item.getComputerTestResult()+"");
            lm.put("comprehensiveResult",item.getComprehensiveResult()+"");
            listMap.add(lm);
        }

        map.put("maplist", listMap);
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("fileName", "fileName001.xlsx");
        resultMap.put("fileStream", outputStream.toByteArray());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(new String(String.valueOf(resultMap.get("fileName")).getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)).build());
        return ResponseEntity.ok().headers(headers).body((byte[]) resultMap.get("fileStream"));
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
            List<ComputerTestResultExcelDTO> computerTestResultExcelList = ExcelImportUtil.importExcel(file, ComputerTestResultExcelDTO.class, importParams);
            log.info("{}", computerTestResultExcelList);
            for (ComputerTestResultExcelDTO item : computerTestResultExcelList
            ) {
                UpdateWrapper<Student> studentUpdateWrapper = new UpdateWrapper<>();
                studentUpdateWrapper.eq("code", item.getCode()).set("computer_test_result", item.getCent());
                studentService.update(studentUpdateWrapper);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

