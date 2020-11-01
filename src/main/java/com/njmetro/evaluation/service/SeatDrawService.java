
package com.njmetro.evaluation.service;

import com.njmetro.evaluation.domain.SeatDraw;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njmetro.evaluation.domain.Student;
import com.njmetro.evaluation.vo.SeatDrawVO;
import com.njmetro.evaluation.vo.StudentReadyShowVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zc
 * @since 2020-09-28
 */
public interface SeatDrawService extends IService<SeatDraw> {
    /**
     * 重置 seat_draw 表格
     *
     * @return true
     */
    Boolean deleteTable();

    List<SeatDrawVO> getSeatDraw();
    void cleanAll();

    /**
     * 根据场次、轮次、错位号 获取学生信息
     *
     * @param gameNumber 场次
     * @param gameRound 轮次
     * @param seatId 学生座位ID
     * @return
     */
    List<Student> getStudentShowBySeatId(Integer gameNumber, Integer gameRound, Integer seatId);
    /**
     *  根据场次轮次信息获取考生就绪状态
     *
     * @param gameNumber 场次
     * @param gameRound 轮次
     * @return
     */
    List<StudentReadyShowVO> listStudentReady(Integer gameNumber, Integer gameRound);

    /**
     *  根据场次轮次信息获取违纪考生id列表
     *
     * @param gameNumber 场次
     * @param gameRound 轮次
     * @return
     */
    List<Integer> getBreakRuleStudentList(Integer gameNumber, Integer gameRound);
}