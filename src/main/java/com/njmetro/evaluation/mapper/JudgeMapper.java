package com.njmetro.evaluation.mapper;

import com.njmetro.evaluation.domain.Judge;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njmetro.evaluation.dto.JudgeInfoDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zc
 * @since 2020-09-27
 */
public interface JudgeMapper extends BaseMapper<Judge> {
    @Select("SELECT judge.id,judge.name,judge.code,judge_draw_result.seat_id,phone,judge_type FROM pad, judge_draw_result,judge WHERE pad.ip = #{ip}  and judge_draw_result.pad_id = pad.id and judge_id = judge.id")
    List<JudgeInfoDTO> getInfo(@Param("ip") String ip);

}
