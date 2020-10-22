package com.njmetro.evaluation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njmetro.evaluation.domain.*;
import com.njmetro.evaluation.mapper.CodeStateMapper;
import com.njmetro.evaluation.mapper.JudgeMapper;
import com.njmetro.evaluation.mapper.SeatGroupMapper;
import com.njmetro.evaluation.service.*;
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

    @Autowired
    CodeStateService codeStateService;

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

    @Test
    void code() {
        QueryWrapper<CodeState> codeStateQueryWrapper = new QueryWrapper<>();
        //已经扫码，包含确认的和未确认的
//        codeStateQueryWrapper.eq("two_dimensional_code", "njdt001").eq("ip", "172.18.1.239").eq("state", 0).or().eq("state", 1);
        codeStateQueryWrapper.eq("two_dimensional_code", "njdt001").eq("ip", "172.18.1.239");
        codeStateQueryWrapper.and(wrapper->wrapper.eq("state", 0).or().eq("state", 1));
        List<CodeState> codeStateList = codeStateService.list(codeStateQueryWrapper);
        if (codeStateList.size() != 0) {
            System.out.println("本条扫码信息已存在！");
        } else {
            CodeState codeState = new CodeState();
            codeState.setIp("172.18.1.239");
            codeState.setTwoDimensionalCode("njdt001");
            codeState.setState(0);
             codeStateService.save(codeState);
        }
    }


}
