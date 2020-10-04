package com.njmetro.evaluation.mapper;

import com.njmetro.evaluation.domain.TestResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njmetro.evaluation.vo.FinalResultVO;
import com.njmetro.evaluation.vo.TestResultVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zc
 * @since 2020-09-30
 */
public interface TestResultMapper extends BaseMapper<TestResult> {
    @Select("SELECT student_id,student.code as student_code,student.name as student_name,sum(cent) as result ,judge_id,judge.name as judge_name FROM test_result,student,judge where test_result.game_number = #{gameNumber} and test_result.game_round = #{gameRound}  and judge.id = test_result.judge_id and student.id = test_result.student_id GROUP BY judge_id")
    List<TestResultVO> getTempResult(@Param("gameNumber") Integer gameNumber,@Param("gameRound") Integer gameRound);
    @Select("SELECT DISTINCT student_id FROM test_result where game_number = #{gameNumber} and game_round = #{gameRound}")
    List<Integer> getStudentIdList(@Param("gameNumber") Integer gameNumber,@Param("gameRound") Integer gameRound);

    @Select("SELECT student_id,student.code as student_code,student.name as student_name,sum(cent) as result  FROM test_result,student where  student.id = test_result.student_id GROUP BY student_id")
    List<FinalResultVO> getFinalResult();
}
