package com.njmetro.evaluation.mapper;

import com.njmetro.evaluation.domain.SeatDraw;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njmetro.evaluation.domain.Student;
import com.njmetro.evaluation.dto.JudgeInfoDTO;
import com.njmetro.evaluation.vo.SeatDrawVO;
import com.njmetro.evaluation.vo.StudentReadyShowVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * Mapper 接口
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
     * 获取赛位结果
     *
     * @param
     * @return
     */
    @Select("select seat_draw.id, student.company_name,student.name,student.code ,seat_draw.game_number,seat_draw.game_round, seat_draw.seat_id, seat_draw.group_id from student,seat_draw ,company where seat_draw.student_id = student.id and seat_draw.company_id  = company.id")
    List<SeatDrawVO> getSeatDraw();

    @Update("truncate table seat_draw")
    void cleanAll();


    /**
     * 根据场次、轮次、错位号 获取学生信息
     *
     * @param gameNumber 场次
     * @param gameRound  轮次
     * @param seatId     学生座位ID
     * @return
     */
    @Select("SELECT student.name,student.id_card,student.code,student.sign_state\n" +
            "FROM seat_draw,student\n" +
            "WHERE seat_draw.game_number = #{gameNumber} and seat_draw. game_round = #{gameRound} and seat_id =#{seatId} and student.id = seat_draw.student_id")
    List<Student> selectShowStudentBySeatId(Integer gameNumber, Integer gameRound, Integer seatId);

    /**
     * 根据场次轮次信息获取考生就绪状态
     *
     * @param gameNumber 场次
     * @param gameRound  轮次
     * @return
     */
    @Select("SELECT student.name,student.code,seat_draw.id,seat_draw.state,seat_draw.seat_id\n" +
            "FROM seat_draw,student\n" +
            "WHERE seat_draw.game_number = #{gameNumber} AND seat_draw.game_round = #{gameRound} AND seat_draw.student_id = student.id order by seat_draw.seat_id")
    List<StudentReadyShowVO> selectListByGameNumberAndGameRound(Integer gameNumber, Integer gameRound);

    /**
     * 根据场次轮次信息获取考生就绪状态
     *
     * @param gameNumber 场次
     * @param gameRound  轮次
     * @return
     */
    @Select("select student_id from seat_draw where game_number = #{gameNumber} and game_round = #{gameRound} and (state = 5 or state = 6) ")
    List<Integer> getBreakRuleStudentList(Integer gameNumber, Integer gameRound);
}