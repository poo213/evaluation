package com.njmetro.evaluation.mapper;

import com.njmetro.evaluation.domain.TestResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njmetro.evaluation.vo.FinalResultVO;
import com.njmetro.evaluation.vo.TestResultDetailVO;
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
    //获取指定场次和轮次的所有考生的总分,包含两个裁判的结果，用于前台成绩核验用
    @Select("select * from (SELECT student_id,student.company_name as company_name,student.code as student_code,student.name as student_name,sum(cent) as result ,judge_id,judge.name as judge_name FROM test_result,student,judge where test_result.game_number = #{gameNumber} and test_result.game_round = #{gameRound}  and judge.id = test_result.judge_id and student.id = test_result.student_id GROUP BY judge_id) as t order by student_id")
    List<TestResultVO> getTempResult(@Param("gameNumber") Integer gameNumber, @Param("gameRound") Integer gameRound);

    //获取指定场次和轮次的所有考生
    @Select("SELECT DISTINCT student_id FROM test_result where game_number = #{gameNumber} and game_round = #{gameRound}")
    List<Integer> getStudentIdList(@Param("gameNumber") Integer gameNumber, @Param("gameRound") Integer gameRound);
    //获取考试结果
    @Select("SELECT student_id,student.code as student_code,student.name as student_name,sum(cent) as result  FROM test_result,student where  student.id = test_result.student_id GROUP BY student_id")
    List<FinalResultVO> getFinalResult();

    @Select("SELECT test_result.id as id,student_id,student.code as student_code,student.name as student_name, judge.id as judge_id,judge.name as judge_name,test_question.id as question_id, test_question.name as question_name, test_question_standard.id as question_standard_id,test_question_standard.point as question_standard_name,cent  FROM test_result,student,test_question, test_question_standard,judge where test_result.game_number = #{gameNumber} and test_result.game_round = #{gameRound} and test_result.student_id = #{studentId} and student.id = test_result.student_id and judge.id = test_result.judge_id and test_result.question_id = test_question.id and test_result.question_standard_id= test_question_standard.id order by question_standard_id")
    List<TestResultDetailVO> getTestResultDetail(@Param("gameNumber") Integer gameNumber, @Param("gameRound") Integer gameRound, @Param("studentId") Integer studentId);

    @Select("select distinct judge_id from test_result where game_number = #{gameNumber} and game_round = #{gameRound}  and student_id = #{studentId}")
    List<Integer> getJudgeId(@Param("gameNumber") Integer gameNumber, @Param("gameRound") Integer gameRound, @Param("studentId") Integer studentId);
    @Select("SELECT test_result.id as id,student_id,student.code as student_code,student.name as student_name, judge.id as judge_id,judge.name as judge_name,test_question.id as question_id, test_question.name as question_name, test_question_standard.id as question_standard_id,test_question_standard.point as question_standard_name,cent  FROM test_result,student,test_question, test_question_standard,judge where test_result.game_number = #{gameNumber} and test_result.game_round = #{gameRound} and test_result.judge_id = #{judgeId} and test_result.student_id = #{studentId} and student.id = test_result.student_id and judge.id = test_result.judge_id and test_result.question_id = test_question.id and test_result.question_standard_id= test_question_standard.id order by question_standard_id")
    List<TestResultDetailVO> getTestResultDetailByJudgeId(@Param("gameNumber") Integer gameNumber, @Param("gameRound") Integer gameRound, @Param("studentId") Integer studentId, @Param("judgeId")Integer judgeId);
}
