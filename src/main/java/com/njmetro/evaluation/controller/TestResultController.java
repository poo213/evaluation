package com.njmetro.evaluation.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.njmetro.evaluation.domain.*;
import com.njmetro.evaluation.dto.ComputerTestResultExcelDTO;
import com.njmetro.evaluation.dto.DetailTempDTO;
import com.njmetro.evaluation.exception.StudentException;
import com.njmetro.evaluation.exception.TestResultException;
import com.njmetro.evaluation.service.*;
import com.njmetro.evaluation.util.StatisticUtil;
import com.njmetro.evaluation.vo.*;
import com.njmetro.evaluation.vo.api.TestQuestionStandardVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

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
    private final EditResultLogService editResultLogService;
    private final JudgeDrawResultService judgeDrawResultService;
    private final JudgeSubmitStateService judgeSubmitStateService;
    private final PauseRecordService pauseRecordService;
    /**
     * （judge_submit_State）成绩待补录状态 2
     */
    public static Integer READY_WRITE_RESULT_STATE = 2;
    public static Integer END_WRITE_RESULT_STATE = 3;

    /**
     * 获取校验成绩的结果，两个裁判的打分结果的对比
     *
     * @param gameNumber
     * @param gameRound
     * @return
     */
    @GetMapping("/getTempResult")
    public List<TestResultVO> getTempResult(@RequestParam("gameNumber") Integer gameNumber, @RequestParam("gameRound") Integer gameRound) {
        //获取指定场次轮次的成绩
        List<TestResultVO> testResultVOList = testResultService.getTempResult(gameNumber, gameRound);
        //获取指定场次轮次的考生列表
        List<Integer> studentIdList = testResultService.getStudentIdList(gameNumber, gameRound);
        for (Integer num : studentIdList) {
            List<TestResultVO> testResultVOArrayList = new ArrayList<>();

            for (TestResultVO testResultVO : testResultVOList) {
                if (testResultVO.getStudentId() == num) {
                    testResultVOArrayList.add(testResultVO);//将两个裁判的打分存入该list
                }
            }
            if (testResultVOArrayList.size() == 2) {
                if (testResultVOArrayList.get(0).getResult().subtract(testResultVOArrayList.get(1).getResult()).abs().compareTo(new BigDecimal("10")) > -1) {
                    log.info("考生{}得分差的绝对值>=10", testResultVOArrayList.get(0).getStudentName());
                    for (TestResultVO testResultVO : testResultVOList) {
                        if (testResultVO.getStudentId() == testResultVOArrayList.get(0).getStudentId()) {
                            testResultVO.setFlag(1);//标记分差大的项
                        }
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
    public TestResultDetailByJudgeIdVO getResultByStudentCode(@RequestParam("gameNumber") Integer gameNumber, @RequestParam("gameRound") Integer gameRound, @RequestParam("studentId") Integer studentId) {
        //获取指定场次，轮次的考生的两个裁判
        List<Integer> integerList = testResultService.getJudgeId(gameNumber, gameRound, studentId);

        List<TestResultDetailVO> testResultDetailVOListOne = testResultService.getTestResultDetailByJudgeId(gameNumber, gameRound, studentId, integerList.get(0));
        List<TestResultDetailVO> testResultDetailVOListTwo = new ArrayList<>();
        if (integerList.size() == 2) {
            testResultDetailVOListTwo = testResultService.getTestResultDetailByJudgeId(gameNumber, gameRound, studentId, integerList.get(1));
        } else {
            testResultDetailVOListTwo = null;
        }
        TestResultDetailByJudgeIdVO testResultDetailByJudgeIdVO = new TestResultDetailByJudgeIdVO();
        testResultDetailByJudgeIdVO.setTestResultDetailVOListOne(testResultDetailVOListOne);
        testResultDetailByJudgeIdVO.setTestResultDetailVOListTwo(testResultDetailVOListTwo);
        return testResultDetailByJudgeIdVO;

    }

    /**
     * 主裁校正成绩id
     *
     * @param id
     * @param cent
     * @return
     */
    @Transactional
    @GetMapping("/editCent")
    public Boolean editCent(@RequestParam("id") Integer id, @RequestParam("cent") Double cent) {
        log.info("主裁校正成绩id：{}", id);
        log.info("主裁校正成绩分值：{}", cent);
        //记录修改记录
        EditResultLog editResultLog = new EditResultLog();
        editResultLog.setTestResultId(id);
        editResultLog.setCentAfter(cent);
        editResultLog.setEditUser("xxx");
        editResultLog.setCentBefore(testResultService.getById(id).getCent());
        editResultLogService.save(editResultLog);
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
        int tempNumber = config.getGameNumber();
        if (tempNumber == 1) {
            throw new StudentException("第二场比赛开始，才能对分数进行统计！");
        }
        if (config.getGameNumber() > 1 && config.getGameRound() == 3 && config.getState() == 4) {
            tempNumber = config.getGameNumber();
        } else {
            tempNumber = config.getGameNumber() - 1;
        }
        //临时表，得到所有场次和轮次的结果 ，理论上一个考生有3个结果，在这个list里
        List<FinalResultVO> finalTempResultVOList = new ArrayList<>();
        for (int gameNumber = 1; gameNumber <= tempNumber; gameNumber++) {
            for (int gameRound = 1; gameRound <= 3; gameRound++) {
                log.info("第{}场：", gameNumber);
                log.info("第{}轮：", gameRound);
                // 获取指定场次的所有考生的考试结果,通过裁判区分，包含两个裁判的打分
                List<TestResultVO> testResultVOList = testResultService.getTempResult(gameNumber, gameRound);
                log.info("获取指定场次的所有考生的考试结果：{}", testResultVOList);

                List<Integer> studentIdList = testResultService.getStudentIdList(gameNumber, gameRound);
                //获取违纪的考生，本场本轮不应该计算成绩的人,todo  ,违纪的人，直接0分
                List<Integer> breakRuleStudentList = seatDrawService.getBreakRuleStudentList(gameNumber, gameRound);
                //将违纪的考生直接去除，下方不计算成绩
                studentIdList.removeAll(breakRuleStudentList);
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
//                            if (seatDrawList.size() == 0) {
//                                throw new StudentException("请检查赛位抽签表" + gameNumber + "场" + gameRound + "轮的考生" + testResultVO.getStudentName() + "没有比赛用时！");
//                            }
                            Integer useTime = 0;
                            //获取到指定场次轮次下 的比赛用时
                            if (seatDrawList.size() == 1) {
                                useTime = seatDrawList.get(0).getUseTime();
                                //时间分赋值
                                testResultVO.setTimeCent(StatisticUtil.getTimeCent(useTime, testResultVO.getResult()));
                            }
                            testResultVOArrayList.add(testResultVO);
                        }
                    }
                    //表示结果异常，正常情况下一个考生应该有两条成绩
                    if (testResultVOArrayList.size() < 2) {
                        System.out.println(testResultVOArrayList);
                        throw new StudentException("第" + gameNumber + "场，第" + gameRound + "轮的考生" + num + "号考生缺少成绩（每个考生需要有两个裁判打分成绩）");
                    }
                    //两个裁判打分取中值，包含时间分
                    BigDecimal res = testResultVOArrayList.get(0).getResult().add(testResultVOArrayList.get(0).getTimeCent()).add(testResultVOArrayList.get(1).getResult()).add(testResultVOArrayList.get(1).getTimeCent()).divide(new BigDecimal("2"));
                    //BigDecimal res = new BigDecimal((testResultVOArrayList.get(0).getResult()+ testResultVOArrayList.get(0).getTimeCent()+ testResultVOArrayList.get(1).getResult()+ testResultVOArrayList.get(1).getTimeCent()) / 2;
                    FinalResultVO finalResultVO = new FinalResultVO();
                    finalResultVO.setStudentId(testResultVOArrayList.get(0).getStudentId());
                    finalResultVO.setStudentCode(testResultVOArrayList.get(0).getStudentCode());
                    finalResultVO.setStudentName(testResultVOArrayList.get(0).getStudentName());
                    finalResultVO.setCompanyName(testResultVOArrayList.get(0).getCompanyName());
                    finalResultVO.setIdCard(testResultVOArrayList.get(0).getIdCard());
                    finalResultVO.setResult(res);
                    finalTempResultVOList.add(finalResultVO);
                }
                for (Integer lackPeopleId : breakRuleStudentList)//缺考违纪的考生
                {
                    Student student = studentService.getById(lackPeopleId);
                    FinalResultVO finalResultVO = new FinalResultVO();
                    finalResultVO.setStudentId(lackPeopleId);
                    finalResultVO.setStudentCode(student.getCode());
                    finalResultVO.setStudentName(student.getName());
                    finalResultVO.setCompanyName(student.getCompanyName());
                    finalResultVO.setIdCard(student.getIdCard());
                    finalResultVO.setResult(new BigDecimal("0"));
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
                finalResultVO.setIdCard(tempList.get(0).getIdCard());
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
        List<FinalResultVO> finaResultVOListSort = finaResultVOList.stream().sorted((Comparator.comparing(FinalResultVO::getComprehensiveResult)).reversed()).collect(Collectors.toList());
        Integer id = 1;
        for (FinalResultVO item :
                finaResultVOListSort) {
            item.setId(id);
            id++;
        }
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(finaResultVOList);
//        log.info("得分结果json字符串：{}", json);

        return finaResultVOListSort;
    }

    /**
     * 最终打分结果导出
     *
     * @return
     */
    @GetMapping("/exportExcel")
    public ResponseEntity<byte[]> exportFile() throws IOException {
        //此处用于只能获取已进行的场次的成绩汇总
        Config config = configService.getById(1);
        int tempNumber = config.getGameNumber();
        if (tempNumber == 1) {
            throw new StudentException("第二场比赛开始，才能对分数进行统计！");
        }
        if (config.getGameNumber() > 1 && config.getGameRound() == 3 && config.getState() == 4) {
            tempNumber = config.getGameNumber();
        } else {
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
                //获取违纪的考生，本场本轮不应该计算成绩的人
                List<Integer> breakRuleStudentList = seatDrawService.getBreakRuleStudentList(gameNumber, gameRound);
                //将违纪的考生直接去除，下方不计算成绩
                studentIdList.removeAll(breakRuleStudentList);
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
//                            if (seatDrawList.size() == 0) {
//                                throw new StudentException("请检查赛位抽签表" + gameNumber + "场" + gameRound + "轮的考生" + testResultVO.getStudentName() + "没有比赛用时！");
//                            }
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
                    //表示结果异常，正常情况下一个考生应该有两条成绩
                    if (testResultVOArrayList.size() < 2) {
                        throw new StudentException("第" + gameNumber + "场，第" + gameRound + "轮的考生" + num + "号考生缺少成绩（每个考生需要有两个裁判打分成绩）");
                    }
                    //两个裁判打分取中值，包含时间分
                    BigDecimal res = testResultVOArrayList.get(0).getResult().add(testResultVOArrayList.get(0).getTimeCent()).add(testResultVOArrayList.get(1).getResult()).add(testResultVOArrayList.get(1).getTimeCent()).divide(new BigDecimal("2"));
                    //BigDecimal res = new BigDecimal((testResultVOArrayList.get(0).getResult()+ testResultVOArrayList.get(0).getTimeCent()+ testResultVOArrayList.get(1).getResult()+ testResultVOArrayList.get(1).getTimeCent()) / 2;
                    FinalResultVO finalResultVO = new FinalResultVO();
                    finalResultVO.setStudentId(testResultVOArrayList.get(0).getStudentId());
                    finalResultVO.setStudentCode(testResultVOArrayList.get(0).getStudentCode());
                    finalResultVO.setStudentName(testResultVOArrayList.get(0).getStudentName());
                    finalResultVO.setCompanyName(testResultVOArrayList.get(0).getCompanyName());
                    finalResultVO.setIdCard(testResultVOArrayList.get(0).getIdCard());
                    finalResultVO.setResult(res);
                    finalTempResultVOList.add(finalResultVO);
                }
                for (Integer lackPeopleId : breakRuleStudentList)//缺考违纪的考生
                {
                    Student student = studentService.getById(lackPeopleId);
                    FinalResultVO finalResultVO = new FinalResultVO();
                    finalResultVO.setStudentId(lackPeopleId);
                    finalResultVO.setStudentCode(student.getCode());
                    finalResultVO.setStudentName(student.getName());
                    finalResultVO.setCompanyName(student.getCompanyName());
                    finalResultVO.setIdCard(student.getIdCard());
                    finalResultVO.setResult(new BigDecimal("0"));
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
                finalResultVO.setIdCard(tempList.get(0).getIdCard());
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
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(finaResultVOList);
//        log.info("得分结果json字符串：{}", json);

        List<FinalResultVO> finaResultVOListSort = finaResultVOList.stream().sorted((Comparator.comparing(FinalResultVO::getComprehensiveResult)).reversed()).collect(Collectors.toList());

        Integer id = 1;
        for (FinalResultVO item :
                finaResultVOListSort) {
            item.setId(id);
            id++;
        }

        TemplateExportParams params = new TemplateExportParams(
                "c:/结果汇总.xlsx");
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        for (FinalResultVO item : finaResultVOListSort
        ) {

            Map<String, Object> lm = new HashMap<String, Object>();
            lm.put("id", item.getId());
            lm.put("studentId", item.getStudentId() + "");
            lm.put("studentName", item.getStudentName());
            lm.put("companyName", item.getCompanyName());
            lm.put("idCard", item.getIdCard());
            lm.put("result", item.getResult());
            lm.put("computerTestResult", item.getComputerTestResult());
            lm.put("comprehensiveResult", item.getComprehensiveResult());
            listMap.add(lm);
        }

        map.put("maplist", listMap);
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("fileName", "结果汇总.xlsx");
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
            importParams.setHeadRows(2);
            List<ComputerTestResultExcelDTO> computerTestResultExcelList = ExcelImportUtil.importExcel(file, ComputerTestResultExcelDTO.class, importParams);
            log.info("{}", computerTestResultExcelList);
            for (ComputerTestResultExcelDTO item : computerTestResultExcelList
            ) {
                UpdateWrapper<Student> studentUpdateWrapper = new UpdateWrapper<>();
                studentUpdateWrapper.eq("id_card", item.getIdCard()).set("computer_test_result", item.getCent());
                studentService.update(studentUpdateWrapper);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param
     * @return java.lang.String
     * @Description 进行成绩汇总条件的校验
     * @date 2020-10-28 10:18
     * @auther zc
     */
    @GetMapping("/checkCountCondition")
    public String checkCountCondition() {
        String tip = "";
        Double sum = studentService.checkComputerTestResult();
        System.out.println("sum=" + sum);
        if (sum == 0) {
            return "机考成绩异常，查询是否正确导入!";
        }
        //此处用于只能获取已进行的场次的成绩汇总
        Config config = configService.getById(1);
        int tempNumber = config.getGameNumber();
        if (tempNumber == 1) {
            return "第二场比赛开始，才能对分数进行统计！";
        }
        if (config.getGameNumber() > 1 && config.getGameRound() == 3 && config.getState() == 4) {
            tempNumber = config.getGameNumber();
        } else {
            tempNumber = config.getGameNumber() - 1;
        }
        tip = "条件满足，可以进行成绩汇总！注：此时可以进行" + tempNumber + "场(包含)以前的成绩汇总！";
        //临时表，得到所有场次和轮次的结果 ，理论上一个考生有3个结果，在这个list里
        List<FinalResultVO> finalTempResultVOList = new ArrayList<>();
        for (int gameNumber = 1; gameNumber <= tempNumber; gameNumber++) {
            for (int gameRound = 1; gameRound <= 3; gameRound++) {
                // 获取指定场次的所有考生的考试结果,通过裁判区分，包含两个裁判的打分
                List<TestResultVO> testResultVOList = testResultService.getTempResult(gameNumber, gameRound);
                log.info("获取指定场次的所有考生的考试结果：{}", testResultVOList);
                List<Integer> studentIdList = testResultService.getStudentIdList(gameNumber, gameRound);
                //获取违纪的考生，本场本轮不应该计算成绩的人
                List<Integer> breakRuleStudentList = seatDrawService.getBreakRuleStudentList(gameNumber, gameRound);
                //将违纪的考生直接去除，下方不计算成绩
                studentIdList.removeAll(breakRuleStudentList);
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
                            Integer useTime = 0;
                            //获取到指定场次轮次下 的比赛用时
                            if (seatDrawList.size() == 1) {
                                if (seatDrawList.get(0).getUseTime() == null) {
                                    tip += "请检查赛位抽签表" + gameNumber + "场" + gameRound + "轮的考生" + testResultVO.getStudentName() + "缺少比赛用时！";
                                }
                            }
                            testResultVOArrayList.add(testResultVO);
                        }
                    }
                    //表示结果异常，正常情况下一个考生应该有两条成绩
                    if (testResultVOArrayList.size() < 2) {
                        //throw new StudentException("第" + gameNumber + "场，第" + gameRound + "轮的考生" + num + "号考生缺少成绩（每个考生需要有两个裁判打分成绩）");
                        tip += "第" + gameNumber + "场，第" + gameRound + "轮的考生" + num + "号考生缺少成绩（每个考生需要有两个裁判打分成绩）";
                    }
                }
            }
        }
        return tip;
    }

    /**
     * 获取成绩补录 评分列表
     *
     * @param gameNumber 场次
     * @param gameRound  轮次
     * @param seatId     裁判编号
     * @return
     */
    @GetMapping("/getWriteResultStandardVO")
    public List<TestQuestionStandardVO> getWriteResultStandardVO(Integer gameNumber, Integer gameRound, Integer seatId) {
        log.info("gameNumber {}", gameNumber);
        log.info("gameRound {}", gameRound);
        log.info("seatId {}", seatId);
        // 根据 seatId 在judge_draw_result表中找到 judge_id
        QueryWrapper<JudgeDrawResult> judgeDrawResultQueryWrapper = new QueryWrapper<>();
        judgeDrawResultQueryWrapper.eq("seat_id", seatId);
        Integer judgeId = judgeDrawResultService.getOne(judgeDrawResultQueryWrapper).getJudgeId();
        // 在 judge_submit_state 中找到 是否允许补录状态
        QueryWrapper<JudgeSubmitState> judgeSubmitStateQueryWrapper = new QueryWrapper<>();
        judgeSubmitStateQueryWrapper.eq("game_number", gameNumber)
                .eq("game_round", gameRound)
                .eq("judge_id", judgeId);
        JudgeSubmitState judgeSubmitState = judgeSubmitStateService.getOne(judgeSubmitStateQueryWrapper);
        if (judgeSubmitState == null) {
            throw new TestResultException(gameNumber + " 场" + gameRound + " 轮未开考，不允许录入成绩 ");
        } else {
            if (judgeSubmitState.getState().equals(READY_WRITE_RESULT_STATE)) {
                // test_result 表中根据 gameNumber gameRound judgeId 获取评分标准
                return testResultService.getWriteResultStandards(gameNumber, gameRound, judgeId);
            } else {
                throw new TestResultException("[ " + gameNumber + " 场" + gameRound + " 轮 " + seatId + " 座位裁判 ]当前状态不允许手动录入成绩，请核验后重试");
            }
        }
    }

    /**
     * 录入一条记录
     *
     * @param id   录入成绩id
     * @param cent 录入成绩
     * @return
     */
    @GetMapping("/writeOneTestResult")
    Boolean writeOneTestResult(Integer id, double cent) {
        log.info("id {}", id);
        log.info("cent {}", cent);
        TestResult testResult = testResultService.getById(id);
        testResult.setCent(cent);
        return testResultService.updateById(testResult);
    }

    /**
     * 成绩补录完成
     *
     * @param gameNumber 场次
     * @param gameRound  轮次
     * @param seatId     裁判编号
     * @return
     */
    @GetMapping("/writeTestResultEnd")
    Boolean writeTestResultEnd(Integer gameNumber, Integer gameRound, Integer seatId) {
        log.info("gameNumber {}", gameNumber);
        log.info("gameRound {}", gameRound);
        log.info("seatId {}", seatId);
        // 根据 seatId 在judge_draw_result表中找到 judge_id
        QueryWrapper<JudgeDrawResult> judgeDrawResultQueryWrapper = new QueryWrapper<>();
        judgeDrawResultQueryWrapper.eq("seat_id", seatId);
        Integer judgeId = judgeDrawResultService.getOne(judgeDrawResultQueryWrapper).getJudgeId();
        QueryWrapper<JudgeSubmitState> judgeSubmitStateQueryWrapper = new QueryWrapper<>();
        judgeSubmitStateQueryWrapper.eq("game_number", gameNumber)
                .eq("game_round", gameRound)
                .eq("judge_id", judgeId);
        JudgeSubmitState judgeSubmitState = judgeSubmitStateService.getOne(judgeSubmitStateQueryWrapper);
        judgeSubmitState.setState(END_WRITE_RESULT_STATE);
        return judgeSubmitStateService.updateById(judgeSubmitState);
    }

    /***
     * @Description 获取次考生暂停列表
     * @param name
     * @return java.util.List<com.njmetro.evaluation.domain.PauseRecord>
     * @date 2020-11-5 14:29
     * @auther zc
     */
    @GetMapping("/getPauseAdjustList")
    public List<PauseRecordVO> getPauseAdjustList(@RequestParam("name") String name) {
        return pauseRecordService.getPauseAdjustList(name);
    }

    /***
     * @Description 修改暂停 从无效变有效，同时从比赛用时中减去这个时间
     * @param id
     * @return java.lang.Boolean
     * @date 2020-11-6 10:35
     * @auther zc
     */
    @GetMapping("/changeFlag")
    public Boolean changeFlag(@RequestParam("id") Integer id) {
        System.out.println(id);
        UpdateWrapper<PauseRecord> pauseRecordUpdateWrapper = new UpdateWrapper<>();
        pauseRecordUpdateWrapper.eq("id", id).set("flag", true);
        PauseRecord pauseRecord = pauseRecordService.getById(id);
        System.out.println("pauseRecord = " + pauseRecord);
        QueryWrapper<SeatDraw> seatDrawQueryWrapper = new QueryWrapper<>();
        seatDrawQueryWrapper.eq("game_number", pauseRecord.getGameNumber())
                .eq("game_round", pauseRecord.getGameRound())
                .eq("student_id", pauseRecord.getStudentId());
        SeatDraw seatDraw = seatDrawService.getOne(seatDrawQueryWrapper);
        System.out.println("seatDraw = " + seatDraw);
        UpdateWrapper<SeatDraw> seatDrawUpdateWrapper = new UpdateWrapper<>();
        seatDrawUpdateWrapper.eq("game_number", pauseRecord.getGameNumber())
                .eq("game_round", pauseRecord.getGameRound())
                .eq("student_id", pauseRecord.getStudentId())
                .set("use_time", seatDraw.getUseTime() - pauseRecord.getPauseTime());
        seatDrawService.update(seatDrawUpdateWrapper);

        return pauseRecordService.update(pauseRecordUpdateWrapper);
    }


    /**
     * @return java.lang.Integer
     * @Description 获取机考成绩中不合理的结果
     * @date 2020-11-4 10:33
     * @auther zc
     */
    @GetMapping("/getComputerTestResultZero")
    public List<String> getComputerTestResultZero() {
        List<String> resList = new ArrayList<>();
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("computer_test_result", 0);
        List<Student> studentList = studentService.list(studentQueryWrapper);
        for (Student item : studentList) {
            resList.add(item.getCompanyName() + "的" + item.getName() + "的成绩为0分");
        }
        return resList;
    }

    /***
     * @Description 获取考试用时为null的考生
     * @return java.util.List<java.lang.String>
     * @date 2020-11-4 10:53
     * @auther zc
     */

    @GetMapping("/getUseTimeNull")
    public List<String> getUseTimeNull() {
        List<String> resList = new ArrayList<>();
        QueryWrapper<SeatDraw> seatDrawQueryWrapper = new QueryWrapper<>();
        seatDrawQueryWrapper.isNull("use_time");
        List<SeatDraw> seatDrawList = seatDrawService.list(seatDrawQueryWrapper);
        for (SeatDraw item : seatDrawList) {
            resList.add("第" + item.getGameNumber() + "场" + item.getGameRound() + "的" + item.getStudentId() + "号考生用时为空");
        }
        return resList;
    }


    /**
     * 获取包含各项成绩的list
     */

    @GetMapping("/getDetailResult")
    public List<DetailResultVO> getDetailResult(@RequestParam("one") Integer one, @RequestParam("two") Integer two, @RequestParam("three") Integer three) {
        //保存最终的查询结果
        List<DetailResultVO> detailResultVOList = new ArrayList<>();
        List<Student> studentIdList = studentService.list();

        for (Student student : studentIdList) {
            DetailResultVO detailResultVO = new DetailResultVO();
            detailResultVO.setId(student.getId());
            detailResultVO.setCode(student.getCode());
            detailResultVO.setName(student.getName());
            detailResultVO.setCompanyName(student.getCompanyName());
            detailResultVO.setIdCard(student.getIdCard());
            detailResultVOList.add(detailResultVO);
        }
        //光缆接续
        one = 1;
        //视频搭建
        two = 10;
        //交换机
        three = 4;
        //所有考生在一道题目上的得分，包含场次、伦次、裁判id ，裁判姓名，学生id，学生姓名，题目号
        List<DetailTempDTO> detailTempDTOList = testResultService.getDetailTempResult(one);
        for (DetailTempDTO item : detailTempDTOList) {
            //计算出时间分，塞进去
            QueryWrapper<SeatDraw> seatDrawQueryWrapper = new QueryWrapper<>();
            seatDrawQueryWrapper.eq("game_number", item.getGameNumber())
                    .eq("game_round", item.getGameRound()).eq("student_id", item.getStudentId());
            SeatDraw seatDraw = seatDrawService.getOne(seatDrawQueryWrapper);
            //时间分
            item.setTimeCent(StatisticUtil.getTimeCent(seatDraw.getUseTime(), item.getCent()));
            //包含时间分
            item.setAllCent(item.getCent().add(StatisticUtil.getTimeCent(seatDraw.getUseTime(), item.getCent())));
        }
        for (Student student : studentIdList) {
            BigDecimal i = new BigDecimal("0");
            for (DetailTempDTO detailTempDTO : detailTempDTOList) {
                if (detailTempDTO.getStudentId().equals(student.getId())) {
//                    System.out.println(detailTempDTO.getStudentId());
//                    System.out.println(student.getId());
//                    System.out.println(detailTempDTO.getAllCent());
                    i=i.add(detailTempDTO.getAllCent());//两个裁判打分求和
//                    System.out.println(i);
                }
            }
            i=i.divide(new BigDecimal("2"));
            for (DetailResultVO item : detailResultVOList) {
                if (item.getId().equals(student.getId())) {
                    item.setOne(i);
                }
            }
        }
        List<DetailTempDTO> detailTempDTOList2 = testResultService.getDetailTempResult(two);
        for (DetailTempDTO item : detailTempDTOList2) {
            //计算出时间分，塞进去
            QueryWrapper<SeatDraw> seatDrawQueryWrapper2 = new QueryWrapper<>();
            seatDrawQueryWrapper2.eq("game_number", item.getGameNumber())
                    .eq("game_round", item.getGameRound()).eq("student_id", item.getStudentId());
            SeatDraw seatDraw2 = seatDrawService.getOne(seatDrawQueryWrapper2);
            //时间分
            item.setTimeCent(StatisticUtil.getTimeCent(seatDraw2.getUseTime(), item.getCent()));
            //包含时间分
            item.setAllCent(item.getCent().add(StatisticUtil.getTimeCent(seatDraw2.getUseTime(), item.getCent())));
        }
        for (Student student : studentIdList) {
            BigDecimal i = new BigDecimal("0");
            for (DetailTempDTO detailTempDTO : detailTempDTOList2) {
                if (detailTempDTO.getStudentId().equals(student.getId())) {
                    i=i.add(detailTempDTO.getAllCent());//两个裁判打分求和
                }
            }
            i=i.divide(new BigDecimal("2"));
            for (DetailResultVO item : detailResultVOList) {
                if (item.getId().equals(student.getId())) {
                    item.setTwo(i);
                }
            }
        }
        List<DetailTempDTO> detailTempDTOList3 = testResultService.getDetailTempResult(three);
        for (DetailTempDTO item : detailTempDTOList3) {
            //计算出时间分，塞进去
            QueryWrapper<SeatDraw> seatDrawQueryWrapper3 = new QueryWrapper<>();
            seatDrawQueryWrapper3.eq("game_number", item.getGameNumber())
                    .eq("game_round", item.getGameRound()).eq("student_id", item.getStudentId());
            SeatDraw seatDraw2 = seatDrawService.getOne(seatDrawQueryWrapper3);
            //时间分
            item.setTimeCent(StatisticUtil.getTimeCent(seatDraw2.getUseTime(), item.getCent()));
            //包含时间分
            item.setAllCent(item.getCent().add(StatisticUtil.getTimeCent(seatDraw2.getUseTime(), item.getCent())));
        }
        for (Student student : studentIdList) {
            BigDecimal i = new BigDecimal("0");
            for (DetailTempDTO detailTempDTO : detailTempDTOList3) {
                if (detailTempDTO.getStudentId().equals(student.getId())) {
                    i=i.add(detailTempDTO.getAllCent());//两个裁判打分求和
                }
            }
            i=i.divide(new BigDecimal("2"));
            for (DetailResultVO item : detailResultVOList) {
                if (item.getId().equals(student.getId())) {
                    item.setThree(i);
                }
            }
        }
        BigDecimal res = new BigDecimal("0");
       for(DetailResultVO item :detailResultVOList) {
           res = ((item.getOne()).add(item.getTwo()).add(item.getThree()));
           res = res.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP);
           System.out.println(res);
       }
        System.out.println(detailResultVOList);
        System.out.println(detailTempDTOList);
        return detailResultVOList;
    }

    /**
     * 导出包含各项成绩
     */

    @GetMapping("/exportDetailResult")
    public ResponseEntity<byte[]> exportDetailResult() throws IOException {
        System.out.println(11);
        //保存最终的查询结果
        List<DetailResultVO> detailResultVOList = new ArrayList<>();
        List<Student> studentIdList = studentService.list();

        for (Student student : studentIdList) {
            DetailResultVO detailResultVO = new DetailResultVO();
            detailResultVO.setId(student.getId());
            detailResultVO.setCode(student.getCode());
            detailResultVO.setName(student.getName());
            detailResultVO.setCompanyName(student.getCompanyName());
            detailResultVO.setIdCard(student.getIdCard());
            detailResultVOList.add(detailResultVO);
        }
        //光缆接续
        Integer one = 1;
        //视频搭建
        Integer two = 11;
        //交换机
        Integer three = 7;
        //所有考生在一道题目上的得分，包含场次、伦次、裁判id ，裁判姓名，学生id，学生姓名，题目号
        List<DetailTempDTO> detailTempDTOList = testResultService.getDetailTempResult(one);
        for (DetailTempDTO item : detailTempDTOList) {
            //计算出时间分，塞进去
            QueryWrapper<SeatDraw> seatDrawQueryWrapper = new QueryWrapper<>();
            seatDrawQueryWrapper.eq("game_number", item.getGameNumber())
                    .eq("game_round", item.getGameRound()).eq("student_id", item.getStudentId());
            SeatDraw seatDraw = seatDrawService.getOne(seatDrawQueryWrapper);
            //时间分
            if(seatDraw.getState().equals(4))
            {
                item.setTimeCent(StatisticUtil.getTimeCent(seatDraw.getUseTime(), item.getCent()));
                item.setAllCent(item.getCent().add(StatisticUtil.getTimeCent(seatDraw.getUseTime(), item.getCent())));
            }
            else{
                item.setAllCent(new BigDecimal("0"));
            }
            //包含时间分
        }
        for (Student student : studentIdList) {
            BigDecimal i = new BigDecimal("0");
            for (DetailTempDTO detailTempDTO : detailTempDTOList) {
                if (detailTempDTO.getStudentId().equals(student.getId())) {
//                    System.out.println(detailTempDTO.getStudentId());
//                    System.out.println(student.getId());
//                    System.out.println(detailTempDTO.getAllCent());
                    i=i.add(detailTempDTO.getAllCent());//两个裁判打分求和
//                    System.out.println(i);
                }
            }
            i=i.divide(new BigDecimal("2"));
            for (DetailResultVO item : detailResultVOList) {
                if (item.getId().equals(student.getId())) {
                    item.setOne(i);
                }
            }
        }
        List<DetailTempDTO> detailTempDTOList2 = testResultService.getDetailTempResult(two);
        for (DetailTempDTO item : detailTempDTOList2) {
            //计算出时间分，塞进去
            QueryWrapper<SeatDraw> seatDrawQueryWrapper2 = new QueryWrapper<>();
            seatDrawQueryWrapper2.eq("game_number", item.getGameNumber())
                    .eq("game_round", item.getGameRound()).eq("student_id", item.getStudentId());
            SeatDraw seatDraw2 = seatDrawService.getOne(seatDrawQueryWrapper2);
            //时间分
            if(seatDraw2.getState().equals(4))
            {
                item.setTimeCent(StatisticUtil.getTimeCent(seatDraw2.getUseTime(), item.getCent()));
                item.setAllCent(item.getCent().add(StatisticUtil.getTimeCent(seatDraw2.getUseTime(), item.getCent())));
            }
            else{
                item.setAllCent(new BigDecimal("0"));
             }
            //包含时间分

        }
        for (Student student : studentIdList) {
            BigDecimal i = new BigDecimal("0");
            for (DetailTempDTO detailTempDTO : detailTempDTOList2) {
                if (detailTempDTO.getStudentId().equals(student.getId())) {
                    i=i.add(detailTempDTO.getAllCent());//两个裁判打分求和
                }
            }
            i=i.divide(new BigDecimal("2"));
            for (DetailResultVO item : detailResultVOList) {
                if (item.getId().equals(student.getId())) {
                    item.setTwo(i);
                }
            }
        }
        List<DetailTempDTO> detailTempDTOList3 = testResultService.getDetailTempResult(three);
        for (DetailTempDTO item : detailTempDTOList3) {
            //计算出时间分，塞进去
            QueryWrapper<SeatDraw> seatDrawQueryWrapper3 = new QueryWrapper<>();
            seatDrawQueryWrapper3.eq("game_number", item.getGameNumber())
                    .eq("game_round", item.getGameRound()).eq("student_id", item.getStudentId());
            SeatDraw seatDraw3 = seatDrawService.getOne(seatDrawQueryWrapper3);
            if(seatDraw3.getState().equals(4))
            {
                item.setTimeCent(StatisticUtil.getTimeCent(seatDraw3.getUseTime(), item.getCent()));
                item.setAllCent(item.getCent().add(StatisticUtil.getTimeCent(seatDraw3.getUseTime(), item.getCent())));
            }
            else{
            //时间分
                item.setAllCent(new BigDecimal("0"));
            }
            //包含时间分

        }
        for (Student student : studentIdList) {
            BigDecimal i = new BigDecimal("0");
            for (DetailTempDTO detailTempDTO : detailTempDTOList3) {
                if (detailTempDTO.getStudentId().equals(student.getId())) {
                    i=i.add(detailTempDTO.getAllCent());//两个裁判打分求和
                }
            }
            i=i.divide(new BigDecimal("2"));
            for (DetailResultVO item : detailResultVOList) {
                if (item.getId().equals(student.getId())) {
                    item.setThree(i);
                }
            }
        }
        BigDecimal res = new BigDecimal("0");
        for(DetailResultVO item :detailResultVOList) {
            res = ((item.getOne()).add(item.getTwo()).add(item.getThree()));
            res = res.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP);
            item.setAll(res);
        }


        TemplateExportParams params = new TemplateExportParams(
                "c:/结果汇总（分项）.xlsx");
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        for (DetailResultVO item : detailResultVOList
        ) {

            Map<String, Object> lm = new HashMap<String, Object>();
            lm.put("id", item.getId());
            lm.put("code", item.getCode() + "");
            lm.put("name", item.getName());
            lm.put("idCard", item.getIdCard());
            lm.put("companyName", item.getCompanyName());
            lm.put("one", item.getOne());
            lm.put("two", item.getTwo());
            lm.put("three", item.getThree());
            lm.put("all", item.getAll());
            listMap.add(lm);
        }

        map.put("maplist", listMap);
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("fileName", "结果汇总（分项）.xlsx");
        resultMap.put("fileStream", outputStream.toByteArray());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(new String(String.valueOf(resultMap.get("fileName")).getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)).build());
        return ResponseEntity.ok().headers(headers).body((byte[]) resultMap.get("fileStream"));

    }

}

