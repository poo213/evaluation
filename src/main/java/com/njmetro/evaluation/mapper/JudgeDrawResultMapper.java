package com.njmetro.evaluation.mapper;

import com.njmetro.evaluation.domain.JudgeDrawResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njmetro.evaluation.vo.JudgeReadyShowVO;
import org.apache.ibatis.annotations.Select;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zc
 * @since 2020-09-29
 */
public interface JudgeDrawResultMapper extends BaseMapper<JudgeDrawResult> {

    /**
     * 根据裁判座位Id获取裁判就绪状态
     * @param seatId 座位id
     * @return
     */
    @Select("SELECT judge.name,judge.code,judge_draw_result.id,judge_draw_result.state\n" +
            "FROM judge_draw_result,judge\n" +
            "WHERE judge_draw_result.seat_id = #{seatId} and judge_draw_result.judge_id = judge.id")
    public JudgeReadyShowVO selectOneBySeatId(Integer seatId);

}
