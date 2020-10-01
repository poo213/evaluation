package com.njmetro.evaluation.mapper;

import com.njmetro.evaluation.domain.SeatGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njmetro.evaluation.vo.GroupTypeJudgeVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zc
 * @since 2020-09-28
 */
public interface SeatGroupMapper extends BaseMapper<SeatGroup> {

    /**
     * 根据 赛位 seatId 获取 裁判信息
     * @param seatId 赛位Id
     * @return
     */
     @Select("SELECT judge.code,judge.name,judge_draw_result.state FROM judge,judge_draw_result WHERE judge.id = judge_draw_result.judge_id and judge_draw_result.seat_id = #{seatId}")
     List<GroupTypeJudgeVO> getGroupTypeJudgeVOBySeatId(@Param("seatId") Integer seatId);


}
