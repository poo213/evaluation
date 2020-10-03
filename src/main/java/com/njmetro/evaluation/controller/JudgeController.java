package com.njmetro.evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.njmetro.evaluation.common.SystemCommon;
import com.njmetro.evaluation.domain.*;
import com.njmetro.evaluation.service.*;
import com.njmetro.evaluation.util.JudgeDrawAlgorithm;
import com.njmetro.evaluation.util.KnuthUtil;
import com.njmetro.evaluation.util.judgeDrawEntity.JudgeEntity;
import com.njmetro.evaluation.util.judgeDrawEntity.SaveJudgeEntity;
import com.njmetro.evaluation.util.judgeDrawEntity.SeatGroupEntity;
import com.njmetro.evaluation.vo.GroupJudgeTypeVO;
import com.njmetro.evaluation.vo.GroupJudgeVO;
import com.njmetro.evaluation.vo.JudgeDrawVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.util.LimitedInputStream;
import org.aspectj.apache.bcel.generic.ReturnaddressType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import javax.swing.text.html.parser.Entity;
import java.awt.*;
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
    public static final String NJ_COMPANY_NAME = "南京地铁";
    private final JudgeService judgeService;
    private final CompanyService companyService;
    private final JudgeDrawResultService judgeDrawResultService;
    private final SeatGroupEntity seatGroupEntity;
    private final SeatGroupService seatGroupService;
    private final PadService padService;
    private final DrawStateService drawStateService;

    /**
     * 根据 裁判类型获取裁判列表
     *
     * @param type 裁判类型
     * @return
     */
    @GetMapping("/getJudgeDrawVOByType")
    public List<JudgeDrawVO> getJudgeDrawVOByType(String type) {
        log.info("type {}", type);
        return judgeService.getJudgeDrawVOByType(type);
    }

    /**
     * 根据 裁判类型获取裁判列表
     *
     * @return
     */
    @GetMapping("/getJudgeDrawVO")
    public List<List<JudgeDrawVO>> getJudgeDrawVO() {
        List<List<JudgeDrawVO>> result = new ArrayList<>();
        result.add(judgeService.getJudgeDrawVOByType(SystemCommon.OPTICAL_TYPE));
        result.add(judgeService.getJudgeDrawVOByType(SystemCommon.SWITCH_TYPE));
        result.add(judgeService.getJudgeDrawVOByType(SystemCommon.VIDEO_TYPE));
        return result;
    }

    /**
     * 根据赛组获取裁判信息
     *
     * @param groupId 赛组Id
     * @return
     */
    public List<GroupJudgeTypeVO> getGroupJudgeTypeVO(Integer groupId) {
        List<GroupJudgeTypeVO> groupJudgeTypeVOList = new ArrayList<>();

        GroupJudgeTypeVO opticalGroupJudgeTypeVO = new GroupJudgeTypeVO();
        opticalGroupJudgeTypeVO.setTypeName(SystemCommon.OPTICAL_TYPE);
        opticalGroupJudgeTypeVO.setGroupTypeJudgeVOList(seatGroupService.getGroupTypeJudgeVOByGroupId(groupId, SystemCommon.OPTICAL_TYPE));
        groupJudgeTypeVOList.add(opticalGroupJudgeTypeVO);

        GroupJudgeTypeVO switchGroupJudgeTypeVO = new GroupJudgeTypeVO();
        switchGroupJudgeTypeVO.setTypeName(SystemCommon.SWITCH_TYPE);
        switchGroupJudgeTypeVO.setGroupTypeJudgeVOList(seatGroupService.getGroupTypeJudgeVOByGroupId(groupId, SystemCommon.SWITCH_TYPE));
        groupJudgeTypeVOList.add(switchGroupJudgeTypeVO);

        GroupJudgeTypeVO videoGroupJudgeTypeVO = new GroupJudgeTypeVO();
        videoGroupJudgeTypeVO.setTypeName(SystemCommon.VIDEO_TYPE);
        videoGroupJudgeTypeVO.setGroupTypeJudgeVOList(seatGroupService.getGroupTypeJudgeVOByGroupId(groupId, SystemCommon.VIDEO_TYPE));
        groupJudgeTypeVOList.add(videoGroupJudgeTypeVO);
        return groupJudgeTypeVOList;
    }

    /**
     * 裁判的抽签结果
     *
     * @return
     */
    @GetMapping("/getGroupJudgeVO")
    public List<GroupJudgeVO> getGroupJudgeVO() {
        List<GroupJudgeVO> groupJudgeVOList = new ArrayList<>();
        List<SeatGroup> seatGroupList = seatGroupService.list();
        for (SeatGroup seatGroup : seatGroupList) {
            GroupJudgeVO groupJudgeVO = new GroupJudgeVO();
            groupJudgeVO.setGroupName(seatGroup.getGroupName());
            List<GroupJudgeTypeVO> groupJudgeTypeVOList = getGroupJudgeTypeVO(seatGroup.getId());
            groupJudgeVO.setGroupJudgeTypeVOList(groupJudgeTypeVOList);
            groupJudgeVOList.add(groupJudgeVO);
        }
        return groupJudgeVOList;
    }

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

        // 修改抽签状态
        DrawState drawState = drawStateService.getById(3);
        drawState.setState(false);
        drawStateService.getById(drawState);
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

        // 修改抽签状态
        DrawState drawState = drawStateService.getById(4);
        drawState.setState(false);
        drawStateService.getById(drawState);
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
     *
     * @return
     */
    public List<SeatGroupEntity> getSeatGroupListByType(String type) {
        List<SeatGroupEntity> seatGroupListNoType = new ArrayList<>();
        // 按抽签顺序获取单位列表
        QueryWrapper<Company> companyQueryWrapper = new QueryWrapper<>();
        companyQueryWrapper.orderByAsc("draw_result");
        List<Company> companyList = companyService.list(companyQueryWrapper);
        // 查询出6个赛组
        List<SeatGroup> seatGroupList = seatGroupService.list();
        for (SeatGroup seatGroup : seatGroupList) {
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
     *
     * @param type 监考类型
     * @return
     */
    public List<JudgeEntity> getJudgeEntityListByType(String type) {
        List<JudgeEntity> judgeEntityList = new ArrayList<>();
        QueryWrapper<Judge> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("judge_type", type);
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
     * 将裁判抽签结果写入 judge_draw_result 表中
     *
     * @param seatGroupEntityList 保存裁判抽签的 list
     * @param type                裁判监考类型
     * @return
     */
    public Boolean saveSeatGroupEntity(List<SeatGroupEntity> seatGroupEntityList, String type) {
        // 根据groupId 给裁判随机分配[ groupId-1）*6 +1 ] 和 [ groupId-1）*6 +2 ]位置
        Integer[] change = new Integer[2];
        switch (type) {
            case "光缆接续":
                change[0] = 1;
                change[1] = 2;
                break;
            case "交换机组网":
                change[0] = 3;
                change[1] = 4;
                break;
            case "视频搭建":
                change[0] = 5;
                change[1] = 6;
                break;
        }
        for (SeatGroupEntity seatGroupEntity : seatGroupEntityList) {
            Integer baseSeatId = (seatGroupEntity.getGroupId() - 1) * 6;
            // 将数组内的值随机打乱
            change = KnuthUtil.result(change);
            List<SaveJudgeEntity> saveJudgeEntityList = seatGroupEntity.getSaveJudgeEntityList();
            for (int i = 0; i < 2; i++) {
                log.info("座位ID {}", baseSeatId + change[i]);
                QueryWrapper<JudgeDrawResult> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("seat_id", baseSeatId + change[i]);
                JudgeDrawResult judgeDrawResult = judgeDrawResultService.getOne(queryWrapper);
                judgeDrawResult.setJudgeId(saveJudgeEntityList.get(i).getJudgeId());
                // 裁判就绪状态
                judgeDrawResult.setState(0);
                // 根据裁判Id 获取 绑定 pad id
                QueryWrapper<Pad> padQueryWrapper = new QueryWrapper<>();
                padQueryWrapper.eq("seat_id", baseSeatId + change[i])
                        .eq("type", 2);
                Pad pad = padService.getOne(padQueryWrapper);
                // todo 默认 裁判id 等于 pad id
                judgeDrawResult.setPadId(pad.getId());
                judgeDrawResultService.updateById(judgeDrawResult);
            }
        }
        return true;
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
        // 调用洗牌算法，重新打乱
        opticalJudgeEntityList = KnuthUtil.getRandomJudgeEntityList(opticalJudgeEntityList);
        switchJudgeEntityList = KnuthUtil.getRandomJudgeEntityList(switchJudgeEntityList);
        videoJudgeEntityList= KnuthUtil.getRandomJudgeEntityList(videoJudgeEntityList);
        // 调用抽签算法
        opticalSeatGroupList = JudgeDrawAlgorithm.run(opticalSeatGroupList, opticalJudgeEntityList);
        switchSeatGroupList = JudgeDrawAlgorithm.run(switchSeatGroupList, switchJudgeEntityList);
        videoSeatGroupList = JudgeDrawAlgorithm.run(videoSeatGroupList, videoJudgeEntityList);
        // 将抽签结果写入数据库
        saveSeatGroupEntity(opticalSeatGroupList, "光缆接续");
        saveSeatGroupEntity(switchSeatGroupList, "交换机组网");
        saveSeatGroupEntity(videoSeatGroupList, "视频搭建");

        // 修改抽签状态
        DrawState drawState = drawStateService.getById(5);
        drawState.setState(false);
        drawStateService.getById(drawState);
        return true;
    }
}

