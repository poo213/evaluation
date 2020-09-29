package com.njmetro.evaluation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njmetro.evaluation.domain.Company;
import com.njmetro.evaluation.domain.Judge;
import com.njmetro.evaluation.domain.JudgeSeatSign;
import com.njmetro.evaluation.service.CompanyService;
import com.njmetro.evaluation.service.JudgeService;
import com.njmetro.evaluation.util.KnuthUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class EvaluationApplicationTests {
    @Autowired
    JudgeService judgeService;

    @Autowired
    CompanyService companyService;

    @Test
    void contextLoads() {
        QueryWrapper<Judge> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("judge_type","交换机组网")
                .orderByAsc("company_name");
        System.out.println(judgeService.list(queryWrapper));
    }

    /**
     *  随机打乱队伍排序
     */
    @Test
    void companyDraw() {
        List<Company> companyList =companyService.list();
        // 初始化一维数组
        Integer[] baseArray = new Integer[41];
        System.out.println(baseArray.length);
        for (int i = 0; i < baseArray.length; i++) {
            System.out.println(i);
            baseArray[i] = i;
        }
        // 抽签
        baseArray = KnuthUtil.result(baseArray);
        // 打印抽签内容 +1
        for (int i = 0; i < baseArray.length; i++) {
            Company company = companyList.get(i).setDrawResult(baseArray[i] + 1);
            companyService.updateById(company);
        }

    }

}
