package com.njmetro.evaluation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njmetro.evaluation.domain.Judge;
import com.njmetro.evaluation.domain.JudgeDrawResult;
import com.njmetro.evaluation.domain.SeatGroup;
import com.njmetro.evaluation.mapper.JudgeDrawResultMapper;
import com.njmetro.evaluation.mapper.SeatGroupMapper;
import com.njmetro.evaluation.service.SeatGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njmetro.evaluation.util.SeatUtil;
import com.njmetro.evaluation.vo.GroupTypeJudgeVO;
import com.njmetro.evaluation.vo.GroupTypeVO;
import com.njmetro.evaluation.vo.GroupVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zc
 * @since 2020-09-28
 */
@Service
@Slf4j
public class SeatGroupServiceImpl extends ServiceImpl<SeatGroupMapper, SeatGroup> implements SeatGroupService {

    @Autowired
    SeatGroupMapper seatGroupMapper;

    @Autowired
    JudgeDrawResultMapper judgeDrawResultMapper;

    /**
     * 根据考试类型 和 赛组Id 获取裁判 详细信息
     * @param groupId 赛组Id
     * @param type 考试类型
     * @return
     */
    @Override
    public List<GroupTypeJudgeVO> getGroupTypeJudgeVOByGroupId(Integer groupId, String type){
        // 根据 groupId 和 type 来获取 裁判 seatId 信息
        Integer leftJudgeSeatId = SeatUtil.getLeftJudgeSeatIdByGroupIdAndType(groupId,type);
        Integer rightJudgeSeatId = SeatUtil.getRightJudgeSeatIdByGroupIdAndType(groupId,type);
        // 返回结果
        List<GroupTypeJudgeVO> groupTypeJudgeVOList = new ArrayList<>();
        groupTypeJudgeVOList.add(seatGroupMapper.getGroupTypeJudgeVOBySeatId(leftJudgeSeatId).get(0));
        groupTypeJudgeVOList.add(seatGroupMapper.getGroupTypeJudgeVOBySeatId(rightJudgeSeatId).get(0));
        log.info("groupTypeJudgeVOList {}",groupTypeJudgeVOList.toString());
        return groupTypeJudgeVOList;
    }



    @Override
    public List<GroupVO> getGroups() {
       /* List<GroupVO> groupVOList = new ArrayList<>();
        QueryWrapper<SeatGroup> seatGroupQueryWrapper = new QueryWrapper<>();
        List<SeatGroup> seatGroupList = seatGroupMapper.selectList(seatGroupQueryWrapper);
        for(SeatGroup seatGroup : seatGroupList){
            GroupVO groupVO = new GroupVO();
            groupVO.setGroupName(seatGroup.getGroupName());
            List<GroupTypeVO> groupTypeVOList = new ArrayList<>();
            for(int i = 1; i <=3 ;i++){
                GroupTypeVO groupTypeVO = new GroupTypeVO()

            }
            groupVO.setGroupTypeVOList();

        }*/
        return null;
    }
}
