package com.njmetro.evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.njmetro.evaluation.domain.*;
import com.njmetro.evaluation.service.CompanyService;
import com.njmetro.evaluation.service.JudgeDrawResultService;
import com.njmetro.evaluation.service.JudgeService;
import com.njmetro.evaluation.service.SeatGroupService;
import com.njmetro.evaluation.util.JudgeDrawAlgorithm;
import com.njmetro.evaluation.util.KnuthUtil;
import com.njmetro.evaluation.util.judgeDrawEntity.JudgeEntity;
import com.njmetro.evaluation.util.judgeDrawEntity.SaveJudgeEntity;
import com.njmetro.evaluation.util.judgeDrawEntity.SeatGroupEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.util.LimitedInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zc
 * @since 2020-09-27
 */
@RestController
@RequestMapping("/judge")
@RequiredArgsConstructor
@Slf4j
public class JudgeController {

    public static final Integer JUDGE_TYPE_NUMBER = 3;
    public static final Integer JUDGE_NUMBER_PER_TYPE = 12;
    public static final Integer SEAT_GROUP_NUMBER = 6;
    public static final String NJ_COMPANY_NAME = "南京地铁";
    private final JudgeService judgeService;
    private final CompanyService companyService;
    private final JudgeDrawResultService judgeDrawResultService;

    @Autowired
    SeatGroupEntity seatGroupEntity;

    @Autowired
    SeatGroupService seatGroupService;

    public static Integer Max_JUDGE_NUMBER = 2;



    /**
     * 获取裁判列表
     */
    @GetMapping("/getJudgeList")
    public List<Judge> getStudentList() {
        return judgeService.list();
    }

    /**
     * 裁判签到
     */
    @PostMapping("/signIn")
    public String signIn(@RequestBody List<Integer> idList) {
        List<String> errorInfo = new ArrayList<>();
        for (Integer id : idList) {
            UpdateWrapper<Judge> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", id).set("sign_state", "1");
            if (!judgeService.update(updateWrapper)) {
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
     * 将所有裁判平均分为三组  光缆接续 视频搭建 交换机组网
     *
     * @return
     */
    @GetMapping("/judgeGrouping")
    public Boolean judgeGrouping() {
        // 根据参赛队伍名称排序查询裁判列表
        QueryWrapper<Judge> judgeQueryWrapper = new QueryWrapper<>();
        judgeQueryWrapper.orderByAsc("company_name");
        List<Judge> judgeList = judgeService.list(judgeQueryWrapper);
        Integer judge_type = 0;
        for (Judge judge : judgeList) {
            switch (judge_type % 3) {
                case 0:
                    judge.setJudgeType("光缆接续");
                    break;
                case 1:
                    judge.setJudgeType("视频搭建");
                    break;
                case 2:
                    judge.setJudgeType("交换机组网");
                    break;
            }
            judgeService.updateById(judge);
            judge_type++;
        }
        return true;
    }

    /**
     * 根据监考类型返回 裁判列表
     *
     * @param judgeType 裁判监考类型
     * @return
     */
    @GetMapping("/getJudgeByJudgeType")
    public List<Judge> getJudgeByJudgeType(String judgeType) {
        QueryWrapper<Judge> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("judge_type", judgeType)
                .orderByAsc("company_name");
        return judgeService.list(queryWrapper);
    }

    /**
     * 每种类型的裁判中 随机挑选 12 个主裁判
     *
     * @return
     */
    @GetMapping("/choiceMasterJudgeFormAllJudge")
    public Boolean choiceMasterJudgeFormAllJudge() {
        // 之前如果选过裁判  需要重置主裁判
        List<Judge> allJudgeList = judgeService.list();
        for (Judge judgeIndex : allJudgeList) {
            judgeIndex.setMaster(0);
            judgeService.updateById(judgeIndex);
        }
        log.info("重置成功");
        // 对每一类型裁判随机生成12个主裁判（南京地铁必须为主裁）
        for (int i = 0; i < JUDGE_TYPE_NUMBER; i++) {
            QueryWrapper<Judge> njQueryWrapper = new QueryWrapper<>();
            QueryWrapper<Judge> otherQueryWrapper = new QueryWrapper<>();
            switch (i) {
                case 0:
                    njQueryWrapper.eq("judge_type", "交换机组网");
                    otherQueryWrapper.eq("judge_type", "交换机组网");
                    break;
                case 1:
                    njQueryWrapper.eq("judge_type", "视频搭建");
                    otherQueryWrapper.eq("judge_type", "视频搭建");
                    break;
                case 2:
                    njQueryWrapper.eq("judge_type", "光缆接续");
                    otherQueryWrapper.eq("judge_type", "光缆接续");
                    break;
            }
            // 南京代表队 裁判
            njQueryWrapper.eq("company_name", NJ_COMPANY_NAME);
            List<Judge> njJudgeList = judgeService.list(njQueryWrapper);
            // 其他代表对裁判
            otherQueryWrapper.ne("company_name", NJ_COMPANY_NAME);
            List<Judge> otherJudgeList = judgeService.list(otherQueryWrapper);
            // 其他代表队伍裁判 随机选取主裁
            Integer[] masterJudgeArray = new Integer[otherJudgeList.size()];
            for (int j = 0; j < otherJudgeList.size(); j++) {
                masterJudgeArray[j] = otherJudgeList.get(j).getId();
            }
            // 其他裁判公平打乱顺序
            Integer[] result = KnuthUtil.result(masterJudgeArray);
            for (int index = 0; index < JUDGE_NUMBER_PER_TYPE - njJudgeList.size(); index++) {
                Judge judge = judgeService.getById(result[index]);
                judge.setMaster(1);
                judgeService.updateById(judge);
            }
            log.info("{} 其他裁判排序抽签结束", i);
            // 将南京地铁裁判 设为主裁
            for (Judge judge : njJudgeList) {
                judge.setMaster(1);
                judgeService.updateById(judge);
            }
            log.info("{} 南京裁判排序抽签结束", i);
        }
        return true;
    }

    /**
     * 根据裁判类型 查询12个主裁判
     *
     * @param judgeType
     * @return
     */
    @GetMapping("/getMasterJudgeByJudgeType")
    public List<Judge> getMasterJudgeByJudgeType(String judgeType) {
        QueryWrapper<Judge> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("judge_type", judgeType)
                .eq("master", 1)
                .orderByAsc("company_name");
        return judgeService.list(queryWrapper);
    }

    /**
     * 根据类型获取赛位信息列表
     * @return
     */
    public List<SeatGroupEntity> getSeatGroupListByType(String type){
        List<SeatGroupEntity> seatGroupListNoType = new ArrayList<>();
        // 按抽签顺序获取单位列表
        QueryWrapper<Company> companyQueryWrapper = new QueryWrapper<>();
        companyQueryWrapper.orderByAsc("draw_result");
        List<Company> companyList = companyService.list(companyQueryWrapper);
        // 查询出6个赛组
        List<SeatGroup> seatGroupList = seatGroupService.list();
        for (SeatGroup seatGroup : seatGroupList){
            SeatGroupEntity seatGroupEntity = new SeatGroupEntity();
            seatGroupEntity.setGroupId(seatGroup.getId());
            seatGroupEntity.setGroupName(seatGroup.getGroupName());
            seatGroupEntity.setIsFull(false);
            // 查询每个赛组和裁判冲突的参赛队
            List<Integer> companyIdList = new ArrayList<>();
            for (Company company : companyList) {
                if ((company.getDrawResult() - 1) % 6 == seatGroup.getId() - 1) {
                    companyIdList.add(company.getId());
                }
            }
            seatGroupEntity.setCompanyDrawnList(companyIdList);
            // 设置保存裁判抽签 结果的list
            List<SaveJudgeEntity> saveJudgeEntityList = new ArrayList<>();
            seatGroupEntity.setSaveJudgeEntityList(saveJudgeEntityList);
            // 设置赛组类型
            seatGroupEntity.setTypeName(type);
            seatGroupListNoType.add(seatGroupEntity);
        }
        return seatGroupListNoType;
    }


    /**
     * 根据监考类型获取 监考裁判列表
     * @param type 监考类型
     * @return
     */
    public List<JudgeEntity> getJudgeEntityListByType(String type){
        List<JudgeEntity> judgeEntityList = new ArrayList<>();
        QueryWrapper<Judge> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("judge_type",type);
        queryWrapper.eq("master", 1);
        List<Judge> judgeList = judgeService.list(queryWrapper);
        for (Judge judge : judgeList) {
            JudgeEntity judgeEntity = new JudgeEntity();
            judgeEntity.setCompanyId(judge.getCompanyId());
            judgeEntity.setJudgeId(judge.getId());
            List<SeatGroupEntity> noConflictSeatGroupEntityList = new ArrayList<>();
            judgeEntity.setNoConflictSeatGroupEntityList(noConflictSeatGroupEntityList);
            judgeEntityList.add(judgeEntity);
        }
        return judgeEntityList;
    }



    /**
     * 主裁随机分配位置
     *
     * @return
     */
    @GetMapping("/judgeDraw")
    public Boolean judgeDraw() {
        // 获取三种类型对应的 赛组列表
        List<SeatGroupEntity> opticalSeatGroupList = getSeatGroupListByType("光缆接续");
        List<SeatGroupEntity> switchSeatGroupList = getSeatGroupListByType("交换机组网");
        List<SeatGroupEntity> videoSeatGroupList = getSeatGroupListByType("视频搭建");

        // 获取三种考试类型 对应的裁判列表
        List<JudgeEntity> opticalJudgeEntityList = getJudgeEntityListByType("光缆接续");
        List<JudgeEntity> switchJudgeEntityList = getJudgeEntityListByType("交换机组网");
        List<JudgeEntity> videoJudgeEntityList = getJudgeEntityListByType("视频搭建");

        // 调用抽签算法
        opticalSeatGroupList = JudgeDrawAlgorithm.run(opticalSeatGroupList,opticalJudgeEntityList);
        switchSeatGroupList = JudgeDrawAlgorithm.run(switchSeatGroupList,switchJudgeEntityList);
        videoSeatGroupList = JudgeDrawAlgorithm.run(videoSeatGroupList,videoJudgeEntityList);


        /**
         * 循环抽签结果,更改数据库信息
         * 根据groupId来计算位置方法  （groupId-1）*6 + m
         * m值为一个随机数，由type 决定
         *      type = "光缆接续"  m =1 或 2
         *      type = "交换机组网"  m =3 或 4
         *      type = "视频搭建"  m =5 或 6
         */
        log.info("光缆接续");
        for(SeatGroupEntity seatGroupEntity : opticalSeatGroupList){
            log.info(seatGroupEntity.toString());
            // 根据groupId 给裁判随机分配[ groupId-1）*6 +1 ] 和 [ groupId-1）*6 +2 ]位置
            Integer baseSeatId = (seatGroupEntity.getGroupId() - 1) * 6;
            Integer[] change = {1,2};
            change = KnuthUtil.result(change);
            List<SaveJudgeEntity> saveJudgeEntityList = seatGroupEntity.getSaveJudgeEntityList();
            for(int i =0; i< 2; i++){
                log.info("座位ID {}",baseSeatId+change[i]);
                QueryWrapper<JudgeDrawResult> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("seat_id",baseSeatId+change[i]);

                JudgeDrawResult judgeDrawResult = judgeDrawResultService.getOne(queryWrapper);
                judgeDrawResult.setJudgeId(saveJudgeEntityList.get(i).getJudgeId());
                judgeDrawResultService.updateById(judgeDrawResult);
            }
        }
        log.info("交换机组网");
        for(SeatGroupEntity seatGroupEntity : switchSeatGroupList){
            log.info(seatGroupEntity.toString());
            // 根据groupId 给裁判随机分配[ groupId-1）*6 +1 ] 和 [ groupId-1）*6 +2 ]位置
            Integer baseSeatId = (seatGroupEntity.getGroupId() - 1) * 6;
            Integer[] change = {3,4};
            change = KnuthUtil.result(change);
            List<SaveJudgeEntity> saveJudgeEntityList = seatGroupEntity.getSaveJudgeEntityList();
            for(int i =0; i< 2; i++){
                log.info("座位ID {}",baseSeatId+change[i]);
                QueryWrapper<JudgeDrawResult> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("seat_id",baseSeatId+change[i]);
                JudgeDrawResult judgeDrawResult = judgeDrawResultService.getOne(queryWrapper);
                judgeDrawResult.setJudgeId(saveJudgeEntityList.get(i).getJudgeId());
                judgeDrawResultService.updateById(judgeDrawResult);
            }
        }
        log.info("视频搭建");
        for(SeatGroupEntity seatGroupEntity : videoSeatGroupList){
            log.info(seatGroupEntity.toString());
            // 根据groupId 给裁判随机分配[ groupId-1）*6 +1 ] 和 [ groupId-1）*6 +2 ]位置
            Integer baseSeatId = (seatGroupEntity.getGroupId() - 1) * 6;
            Integer[] change = {5,6};
            change = KnuthUtil.result(change);
            List<SaveJudgeEntity> saveJudgeEntityList = seatGroupEntity.getSaveJudgeEntityList();
            for(int i =0; i< 2; i++){
                log.info("座位ID {}",baseSeatId+change[i]);
                QueryWrapper<JudgeDrawResult> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("seat_id",baseSeatId+change[i]);
                JudgeDrawResult judgeDrawResult = judgeDrawResultService.getOne(queryWrapper);
                judgeDrawResult.setJudgeId(saveJudgeEntityList.get(i).getJudgeId());
                judgeDrawResultService.updateById(judgeDrawResult);
            }
        }




       /* judgeDraw1();
        judgeDraw2();
        judgeDraw3();*/
        return true;
}

    public void  judgeDraw1(){
        // 获取赛组信息

        // 按抽签顺序获取单位列表
        QueryWrapper<Company> companyQueryWrapper = new QueryWrapper<>();
        companyQueryWrapper.orderByAsc("draw_result");
        List<Company> companyList = companyService.list(companyQueryWrapper);
        // 设置六个赛组信息 （赛组中参赛队伍 companyId 列表）
        List<SeatGroupEntity> seatGroupEntityList = new ArrayList<>();
        for (int i = 1; i <= SEAT_GROUP_NUMBER; i++) {
            // 将参赛队按抽签顺序 安排到对应的赛组 ： 1 放入 A 赛组，2 放入B 赛组
            List<Integer> companyIdList = new ArrayList<>();
            for (Company company : companyList) {
                if ((company.getDrawResult() - 1) % 6 == i - 1) {
                    companyIdList.add(company.getId());
                }
            }
            SeatGroupEntity seatGroupEntity = new SeatGroupEntity();
            // 设置赛组 参赛队Ids
            seatGroupEntity.setCompanyDrawnList(companyIdList);
            // 设置赛组名称
            switch (i) {
                case 1:
                    seatGroupEntity.setGroupName("A");
                    break;
                case 2:
                    seatGroupEntity.setGroupName("B");
                    break;
                case 3:
                    seatGroupEntity.setGroupName("C");
                    break;
                case 4:
                    seatGroupEntity.setGroupName("D");
                    break;
                case 5:
                    seatGroupEntity.setGroupName("E");
                    break;
                case 6:
                    seatGroupEntity.setGroupName("F");
                    break;

            }
            // 初始话赛组信息
            seatGroupEntity.setIsFull(false);
            // 设置选中的 裁判
            List<SaveJudgeEntity> saveJudgeEntityList = new ArrayList<>();
            seatGroupEntity.setSaveJudgeEntityList(saveJudgeEntityList);
            seatGroupEntityList.add(seatGroupEntity);
        }
        // 打印赛组信息
        for (SeatGroupEntity seatGroupEntity : seatGroupEntityList) {
            log.info(seatGroupEntity.toString());
        }

        // 获取裁判信息
        QueryWrapper<Judge> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("judge_type", "视频搭建");

        queryWrapper.eq("master", 1);
        List<Judge> judgeList = judgeService.list(queryWrapper);
        // 将一般裁判 转为算法 中使用的 JudgeEntity 实体
        List<JudgeEntity> judgeEntityList = new ArrayList<>();
        for (Judge judge : judgeList) {
            JudgeEntity judgeEntity = new JudgeEntity();
            judgeEntity.setCompanyId(judge.getCompanyId());
            judgeEntity.setJudgeId(judge.getId());
            List<SeatGroupEntity> noConflictSeatGroupEntityList = new ArrayList<>();
            judgeEntity.setNoConflictSeatGroupEntityList(noConflictSeatGroupEntityList);
            judgeEntityList.add(judgeEntity);
        }

        // 调用 裁判抽签算法
        judgeDrawUtil(seatGroupEntityList, judgeEntityList);
    }
    public void  judgeDraw2(){
        // 获取赛组信息

        // 按抽签顺序获取单位列表
        QueryWrapper<Company> companyQueryWrapper = new QueryWrapper<>();
        companyQueryWrapper.orderByAsc("draw_result");
        List<Company> companyList = companyService.list(companyQueryWrapper);
        // 设置六个赛组信息 （赛组中参赛队伍 companyId 列表）
        List<SeatGroupEntity> seatGroupEntityList = new ArrayList<>();
        for (int i = 1; i <= SEAT_GROUP_NUMBER; i++) {
            // 将参赛队按抽签顺序 安排到对应的赛组 ： 1 放入 A 赛组，2 放入B 赛组
            List<Integer> companyIdList = new ArrayList<>();
            for (Company company : companyList) {
                if ((company.getDrawResult() - 1) % 6 == i - 1) {
                    companyIdList.add(company.getId());
                }
            }
            SeatGroupEntity seatGroupEntity = new SeatGroupEntity();
            // 设置赛组 参赛队Ids
            seatGroupEntity.setCompanyDrawnList(companyIdList);
            // 设置赛组名称
            switch (i) {
                case 1:
                    seatGroupEntity.setGroupName("A");
                    break;
                case 2:
                    seatGroupEntity.setGroupName("B");
                    break;
                case 3:
                    seatGroupEntity.setGroupName("C");
                    break;
                case 4:
                    seatGroupEntity.setGroupName("D");
                    break;
                case 5:
                    seatGroupEntity.setGroupName("E");
                    break;
                case 6:
                    seatGroupEntity.setGroupName("F");
                    break;

            }
            // 初始话赛组信息
            seatGroupEntity.setIsFull(false);
            // 设置选中的 裁判
            List<SaveJudgeEntity> saveJudgeEntityList = new ArrayList<>();
            seatGroupEntity.setSaveJudgeEntityList(saveJudgeEntityList);
            seatGroupEntityList.add(seatGroupEntity);
        }
        // 打印赛组信息
        for (SeatGroupEntity seatGroupEntity : seatGroupEntityList) {
            log.info(seatGroupEntity.toString());
        }

        // 获取裁判信息
        QueryWrapper<Judge> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("judge_type", "交换机组网");

        queryWrapper.eq("master", 1);
        List<Judge> judgeList = judgeService.list(queryWrapper);
        // 将一般裁判 转为算法 中使用的 JudgeEntity 实体
        List<JudgeEntity> judgeEntityList = new ArrayList<>();
        for (Judge judge : judgeList) {
            JudgeEntity judgeEntity = new JudgeEntity();
            judgeEntity.setCompanyId(judge.getCompanyId());
            judgeEntity.setJudgeId(judge.getId());
            List<SeatGroupEntity> noConflictSeatGroupEntityList = new ArrayList<>();
            judgeEntity.setNoConflictSeatGroupEntityList(noConflictSeatGroupEntityList);
            judgeEntityList.add(judgeEntity);
        }

        // 调用 裁判抽签算法
        judgeDrawUtil(seatGroupEntityList, judgeEntityList);
    }
    public void  judgeDraw3(){
        // 获取赛组信息

        // 按抽签顺序获取单位列表
        QueryWrapper<Company> companyQueryWrapper = new QueryWrapper<>();
        companyQueryWrapper.orderByAsc("draw_result");
        List<Company> companyList = companyService.list(companyQueryWrapper);
        // 设置六个赛组信息 （赛组中参赛队伍 companyId 列表）
        List<SeatGroupEntity> seatGroupEntityList = new ArrayList<>();
        for (int i = 1; i <= SEAT_GROUP_NUMBER; i++) {
            // 将参赛队按抽签顺序 安排到对应的赛组 ： 1 放入 A 赛组，2 放入B 赛组
            List<Integer> companyIdList = new ArrayList<>();
            for (Company company : companyList) {
                if ((company.getDrawResult() - 1) % 6 == i - 1) {
                    companyIdList.add(company.getId());
                }
            }
            SeatGroupEntity seatGroupEntity = new SeatGroupEntity();
            // 设置赛组 参赛队Ids
            seatGroupEntity.setCompanyDrawnList(companyIdList);
            // 设置赛组名称
            switch (i) {
                case 1:
                    seatGroupEntity.setGroupName("A");
                    break;
                case 2:
                    seatGroupEntity.setGroupName("B");
                    break;
                case 3:
                    seatGroupEntity.setGroupName("C");
                    break;
                case 4:
                    seatGroupEntity.setGroupName("D");
                    break;
                case 5:
                    seatGroupEntity.setGroupName("E");
                    break;
                case 6:
                    seatGroupEntity.setGroupName("F");
                    break;

            }
            // 初始话赛组信息
            seatGroupEntity.setIsFull(false);
            // 设置选中的 裁判
            List<SaveJudgeEntity> saveJudgeEntityList = new ArrayList<>();
            seatGroupEntity.setSaveJudgeEntityList(saveJudgeEntityList);
            seatGroupEntityList.add(seatGroupEntity);
        }
        // 打印赛组信息
        for (SeatGroupEntity seatGroupEntity : seatGroupEntityList) {
            log.info(seatGroupEntity.toString());
        }

        // 获取裁判信息
        QueryWrapper<Judge> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("judge_type", "光缆接续");

        queryWrapper.eq("master", 1);
        List<Judge> judgeList = judgeService.list(queryWrapper);
        // 将一般裁判 转为算法 中使用的 JudgeEntity 实体
        List<JudgeEntity> judgeEntityList = new ArrayList<>();
        for (Judge judge : judgeList) {
            JudgeEntity judgeEntity = new JudgeEntity();
            judgeEntity.setCompanyId(judge.getCompanyId());
            judgeEntity.setJudgeId(judge.getId());
            List<SeatGroupEntity> noConflictSeatGroupEntityList = new ArrayList<>();
            judgeEntity.setNoConflictSeatGroupEntityList(noConflictSeatGroupEntityList);
            judgeEntityList.add(judgeEntity);
        }

        // 调用 裁判抽签算法
        judgeDrawUtil(seatGroupEntityList, judgeEntityList);
    }


    /**
     * 裁判抽签算法
     *
     * @param seatGroupEntityList 赛组信息
     * @param judgeEntityList     裁判信息
     */
    public void judgeDrawUtil(List<SeatGroupEntity> seatGroupEntityList, List<JudgeEntity> judgeEntityList) {

        log.info("--------------开始执行算法-----------------");

        for (JudgeEntity judgeEntity : judgeEntityList) {
            Boolean success = false;
            int index = 0;
            log.info("裁判 {} (所在参赛队 {})寻找赛位 ", judgeEntity.getJudgeId(), judgeEntity.getCompanyId());

            // 1. 根据裁判所在参赛队 在seatGroupEntityList 中找到可以进入的赛组
            for (SeatGroupEntity seatGroupEntity : seatGroupEntityList) {
                index++;
                // 判断 seatGroupEntity 中的 companyIdList 中是否含有裁判所在 companyId
                if (seatGroupEntity.getCompanyDrawnList().contains(judgeEntity.getCompanyId())) {
                    log.info("{} 赛组中 : 冲突 !!!, 裁判 {} 不能放入 ", seatGroupEntity.getGroupName(), judgeEntity.getJudgeId());
                } else {
                    if (!seatGroupEntity.getIsFull()) {
                        // 如果赛组内裁判未满 6 个，添加裁判
                        List saveJudgeEntityList = seatGroupEntity.getSaveJudgeEntityList();
                        SaveJudgeEntity saveJudgeEntity = new SaveJudgeEntity(judgeEntity);
                        // 设置下标位置
                        saveJudgeEntity.setSeatGroupIndex(index - 1);
                        saveJudgeEntityList.add(saveJudgeEntity);
                        seatGroupEntity.setSaveJudgeEntityList(saveJudgeEntityList);
                        log.info("{} 赛组中 : 裁判 {} 放入 ", seatGroupEntity.getGroupName(), judgeEntity.getJudgeId());
                        if (saveJudgeEntityList.size() < Max_JUDGE_NUMBER) {
                            seatGroupEntity.setIsFull(false);
                        } else {
                            seatGroupEntity.setIsFull(true);
                        }
                        success = true;
                        break;
                    } else {
                        // 如果裁判不冲突，只是 赛组裁判已经满了，就赛组信息存放到指定位置
                        List<SeatGroupEntity> noConflictSeatGroupEntityList = judgeEntity.getNoConflictSeatGroupEntityList();
                        noConflictSeatGroupEntityList.add(seatGroupEntity);
                        judgeEntity.setNoConflictSeatGroupEntityList(noConflictSeatGroupEntityList);
                        log.info("{} 赛组中 : 裁判已满6人,裁判 {} 不能放入 ", seatGroupEntity.getGroupName(), judgeEntity.getJudgeId());
                    }
                }
            }
            // 没有放置成功，开始调换顺序
            if (index == 6 && !success) {
                log.info("裁判 {} 寻找赛位 失败 ！！需要调换位置。", judgeEntity.getJudgeId());
                if (judgeEntity.getNoConflictSeatGroupEntityList().size() == 0) {
                    log.info("裁判 {} 和所有赛组 冲突，算法执行失败 ！！！！！！！！！！！！！！！！", judgeEntity.getJudgeId());
                    break;
                } else {
                    log.info("############################# 裁判调换位置开始##################################");
                    List<SeatGroupEntity> NoConflictSeatGroupEntityList = judgeEntity.getNoConflictSeatGroupEntityList();
                    // 还需要把 调整好的 set 进去

                    log.info("有 {} 个可以交换的赛组", NoConflictSeatGroupEntityList.size());
                    Boolean changeResult = false;
                    for (SeatGroupEntity NoConflictSeatGroupEntity : NoConflictSeatGroupEntityList) {
                        log.info("不冲突的赛组 {} 中裁判进行尝试 调换 ----", NoConflictSeatGroupEntity.getGroupName());
                        // 赛组 中的裁判
                        List<SaveJudgeEntity> saveJudgeEntityList = NoConflictSeatGroupEntity.getSaveJudgeEntityList();

                        for (SaveJudgeEntity saveJudgeEntity : saveJudgeEntityList) {
                            log.info("{} 裁判进行尝试 调换 ==", saveJudgeEntity.getJudgeId());
                            Integer changeIndex = 0;
                            Boolean changeSuccess = false;


                            // 在同样的六个赛组内 进行查找
                            for (SeatGroupEntity changSeatGroup : seatGroupEntityList) {
                                changeIndex++;
                                if (changSeatGroup.getCompanyDrawnList().contains(saveJudgeEntity.getCompanyId())) {
                                    log.info("{} 赛组中：冲突 ！！！裁判 {} 不能放入", changSeatGroup.getGroupName(), saveJudgeEntity.getJudgeId());
                                } else {
                                    if (!changSeatGroup.getIsFull()) {
                                        log.info("{} 赛组中：可以调换", changSeatGroup.getGroupName(), saveJudgeEntity.getJudgeId());
                                        log.info("进行调换的裁判 {} ,可以放入 {} 组中 ：", saveJudgeEntity.getJudgeId(), changSeatGroup.getGroupName());
                                        List<SaveJudgeEntity> saveJudgeEntityLists = changSeatGroup.getSaveJudgeEntityList();
                                        saveJudgeEntityLists.add(new SaveJudgeEntity(saveJudgeEntity.getCompanyId(), saveJudgeEntity.getJudgeId()));
                                        changSeatGroup.setSaveJudgeEntityList(saveJudgeEntityLists);
                                        log.info("1. 进行调换的裁判 {} 进入新位置", saveJudgeEntity.getJudgeId());

                                        SeatGroupEntity ChangeSeatGroupEntity = seatGroupEntityList.get(saveJudgeEntity.getSeatGroupIndex());

                                        List<SaveJudgeEntity> ChangeSaveJudgeEntityList = ChangeSeatGroupEntity.getSaveJudgeEntityList();
                                        ChangeSaveJudgeEntityList.remove(saveJudgeEntity);
                                        log.info("2. 进行调换的裁判 {} 在老位置删除", saveJudgeEntity.getJudgeId());

                                        ChangeSaveJudgeEntityList.add(new SaveJudgeEntity(judgeEntity));
                                        log.info("3. 无法插入裁判 {} 进入老位置", judgeEntity.getJudgeId());
                                        seatGroupEntityList.get(saveJudgeEntity.getSeatGroupIndex()).setSaveJudgeEntityList(ChangeSaveJudgeEntityList);
                                        log.info("调换完成，逐层退出循环");
                                        changeSuccess = true;
                                        break;
                                    } else {
                                        log.info("{} 赛组中：裁判已满无法添加", changSeatGroup.getGroupName());
                                    }
                                }
                            }
                            if (changeIndex == 6 && !changeSuccess) {
                                log.info("赛组 {} 中 调换失败，开始一下组调换", NoConflictSeatGroupEntity.getGroupName());
                            } else {
                                log.info("赛组 {} 中 调换成功 ！！！！", NoConflictSeatGroupEntity.getGroupName());
                                changeResult = true;
                                break;
                            }
                        }
                        if (changeResult) {
                            log.info("跳出最外层循环");
                            log.info("############################# 裁判调换位置结束##################################");
                            break;
                        }
                    }
                    if (!changeResult) {
                        log.info("算法失败 ！！！ 请调整裁判构成");
                    }

                }
            }
        }
        log.info("--------------算法执行结束--------------------");
        for (SeatGroupEntity seatGroupEntity : seatGroupEntityList) {
            log.info(seatGroupEntity.getCompanyDrawnList().toString());
        }
        for (JudgeEntity judgeEntity : judgeEntityList) {
            System.out.println(judgeEntity.getJudgeId() + " ");
        }
        log.info("结果￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥");
        for (SeatGroupEntity seatGroupEntity : seatGroupEntityList) {
            System.out.println();
            log.info("赛组 {}中 考生所在的七个单位ID 为 ：", seatGroupEntity.getGroupName());
            for (Integer index : seatGroupEntity.getCompanyDrawnList()) {
                System.out.print(index + " ");
            }
            System.out.println();
            log.info("赛组 {}中 选中的裁判 ID 为 ：", seatGroupEntity.getGroupName());
            for (SaveJudgeEntity saveJudgeEntity : seatGroupEntity.getSaveJudgeEntityList()) {
                // 写入到数据库
                JudgeDrawResult judgeDrawResult = new JudgeDrawResult();
                judgeDrawResult.setJudgeId(saveJudgeEntity.getJudgeId());
                judgeDrawResult.setSeatId(0);
                judgeDrawResult.setPadId(0);
                judgeDrawResultService.save(judgeDrawResult);

                System.out.print(saveJudgeEntity.getJudgeId() + " ");
            }
        }
    }

}

