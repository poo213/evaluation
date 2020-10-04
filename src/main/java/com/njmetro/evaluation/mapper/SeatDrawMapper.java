package com.njmetro.evaluation.mapper;

import com.njmetro.evaluation.domain.SeatDraw;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njmetro.evaluation.dto.JudgeInfoDTO;
import com.njmetro.evaluation.vo.SeatDrawVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zc
 * @since 2020-09-28
 */
public interface SeatDrawMapper extends BaseMapper<SeatDraw> {
    /**
     * 重置 seat_draw 表格
     */
    @Update("truncate table seat_draw")
    void delete();

    /**
     *  获取赛位结果
     *
     * @param
     * @return
     */
    @Select("select seat_draw.id, student.company_name,student.name,student.code ,seat_draw.game_number,seat_draw.game_round, seat_draw.seat_id, seat_draw.group_id from student,seat_draw ,company where seat_draw.student_id = student.id and seat_draw.company_id  = company.id")
    List<SeatDrawVO> getSeatDraw();

    @Update("truncate table seat_draw")
    void cleanAll();
}