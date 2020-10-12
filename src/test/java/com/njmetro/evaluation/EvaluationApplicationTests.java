package com.njmetro.evaluation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njmetro.evaluation.domain.Company;
import com.njmetro.evaluation.domain.Judge;
import com.njmetro.evaluation.domain.Seat;
import com.njmetro.evaluation.domain.SeatDraw;
import com.njmetro.evaluation.mapper.JudgeMapper;
import com.njmetro.evaluation.mapper.SeatGroupMapper;
import com.njmetro.evaluation.service.CompanyService;
import com.njmetro.evaluation.service.JudgeService;
import com.njmetro.evaluation.service.SeatDrawService;
import com.njmetro.evaluation.service.SeatGroupService;
import com.njmetro.evaluation.util.KnuthUtil;
import com.njmetro.evaluation.util.SeatUtil;
import com.njmetro.evaluation.vo.JudgeShowVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class EvaluationApplicationTests {
    @Autowired
    JudgeService judgeService;

    @Autowired
    CompanyService companyService;

    @Autowired
    JudgeMapper judgeMapper;

    @Autowired
    SeatGroupService seatGroupService;

    @Autowired
    SeatGroupMapper seatGroupMapper;

    @Autowired
    SeatDrawService seatDrawService;

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


    @Test
    void mapper() {
        System.out.println(judgeMapper.getInfo("192.168.97.7"));
    }

    @Test
    void mapper1() {
        Integer groupId = 1;
        String type = "交换机组网";
        Integer leftJudgeSeatId = SeatUtil.getLeftJudgeSeatIdByGroupIdAndType(groupId,type);
        Integer rightJudgeSeatId = SeatUtil.getRightJudgeSeatIdByGroupIdAndType(groupId,type);
        // 返回结果
        List<JudgeShowVO> groupTypeJudgeVOList = new ArrayList<>();
        groupTypeJudgeVOList.add(seatGroupMapper.getGroupTypeJudgeVOBySeatId(leftJudgeSeatId).get(0));
        groupTypeJudgeVOList.add(seatGroupMapper.getGroupTypeJudgeVOBySeatId(rightJudgeSeatId).get(0));
        System.out.println("groupTypeJudgeVOList {}" + groupTypeJudgeVOList.toString());
    }

    @Test
    void mapper3() {
        System.out.println(seatDrawService.deleteTable());
    }

    @Test
    void mapper4() {
       List<SeatDraw> seatDraws = seatDrawService.list();
       for(SeatDraw seatDraw : seatDraws){
           seatDraw.setState(2);
           seatDrawService.updateById(seatDraw);
       }
    }


}
