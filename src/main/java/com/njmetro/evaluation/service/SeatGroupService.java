package com.njmetro.evaluation.service;

import com.njmetro.evaluation.domain.SeatGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njmetro.evaluation.vo.GroupTypeJudgeVO;
import com.njmetro.evaluation.vo.GroupVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zc
 * @since 2020-09-28
 */
public interface SeatGroupService extends IService<SeatGroup> {
    /**
     * 获取赛位就绪状况
     * @return
     */
    List<GroupVO> getGroups();

    /**
     * 根据 赛组和监考类型 裁判信息
     *
     * @param groupId 群组id
     * @param type 监考类型
     * @return
     */
    public List<GroupTypeJudgeVO> getGroupTypeJudgeVOByGroupId(Integer groupId, String type);

}
