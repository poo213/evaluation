package com.njmetro.evaluation.controller;


import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.njmetro.evaluation.domain.TestQuestionStandard;
import com.njmetro.evaluation.dto.TestQuestionStandardExcelDTO;
import com.njmetro.evaluation.service.TestQuestionStandardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/testQuestionStandard")
public class TestQuestionStandardController {
    public final TestQuestionStandardService testQuestionStandardService;

    /**
     * 上传机考得分
     */
    @GetMapping("/readExcel")
    public void readExcel() {
        log.info("上传评分标准");
        try {
            //存储并解析Excel
            File file = new File("C:/evaluation/excel/standard.xlsx");
            ImportParams importParams = new ImportParams();
            importParams.setHeadRows(1);
            List<TestQuestionStandardExcelDTO> testQuestionStandardDTOList = ExcelImportUtil.importExcel(file, TestQuestionStandardExcelDTO.class, importParams);
            log.info("testQuestionStandardDTOList {}", testQuestionStandardDTOList);
            for (TestQuestionStandardExcelDTO item : testQuestionStandardDTOList) {
                log.info("item {}",item);
                TestQuestionStandard testQuestionStandard = new TestQuestionStandard(item);
                testQuestionStandardService.save(testQuestionStandard);
                log.info("插入成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

