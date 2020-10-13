package com.njmetro.evaluation.controller;


import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.njmetro.evaluation.domain.TestQuestion;
import com.njmetro.evaluation.dto.TestQuestionExcelDTO;
import com.njmetro.evaluation.service.TestQuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zc
 * @since 2020-09-21
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/testQuestion")
public class TestQuestionController {

    public final TestQuestionService testQuestionService;
    /**
     * 上传机考得分
     */
    @GetMapping("/readExcel")
    public void readExcel() {
        log.info("上传评分标准");
        try {
            //存储并解析Excel
            File file = new File("C:/evaluation/excel/test.xlsx");
            ImportParams importParams = new ImportParams();
            importParams.setHeadRows(1);
            List<TestQuestionExcelDTO> TestQuestionExcelDTOList = ExcelImportUtil.importExcel(file, TestQuestionExcelDTO.class, importParams);
            log.info("TestQuestionExcelDTOList {}", TestQuestionExcelDTOList);
            for (TestQuestionExcelDTO item : TestQuestionExcelDTOList) {
                log.info("item {}",item);
                TestQuestion testQuestion = new TestQuestion(item);
                testQuestionService.save(testQuestion);
                log.info("插入成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

