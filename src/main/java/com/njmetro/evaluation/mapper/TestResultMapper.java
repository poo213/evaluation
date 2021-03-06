package com.njmetro.evaluation.mapper;

import com.njmetro.evaluation.domain.TestResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njmetro.evaluation.dto.DetailTempDTO;
import com.njmetro.evaluation.vo.FinalResultVO;
import com.njmetro.evaluation.vo.TestResultDetailVO;
import com.njmetro.evaluation.vo.TestResultVO;
import com.njmetro.evaluation.vo.api.TestQuestionStandardVO;
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
    @Select("select * from (SELECT student_id,student.company_name as company_name,student.id_card as id_card,student.code as student_code,student.name as student_name,sum(cent) as result ,judge_id,judge.name as judge_name FROM test_result,student,judge where test_result.game_number = #{gameNumber} and test_result.game_round = #{gameRound}  and judge.id = test_result.judge_id and student.id = test_result.student_id GROUP BY judge_id,student_id) as t order by student_id")
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


    /**
     * 获取手动补录成绩 评分标准
     * @param gameNumber 场次
     * @param gameRound 轮次
     * @param judgeId 裁判Id
     * @return
     */
    @Select("SELECT test_result.id,text,point,score,standard,step,min_score,cent,test_question_id\n" +
            "FROM test_result,test_question_standard\n" +
            "WHERE test_result.question_standard_id = test_question_standard.id and game_number = #{gameNumber} and game_round = #{gameRound} and judge_id =#{judgeId};")
    List<TestQuestionStandardVO> getWriteResultStandards(Integer gameNumber,Integer gameRound,Integer judgeId);

    @Select("select test_result.game_number,test_result.game_round,test_result.judge_id,judge.name as judge_name,student.id as student_id,student.name as student_name,question_id,sum(cent) as cent from test_result,student,judge where test_result.student_id=student.id and test_result.judge_id = judge.id and question_id=#{questionNum} GROUP BY judge_id,student_id,question_id")
    List<DetailTempDTO> getDetailTempResult(@Param("questionNum") Integer questionNum);

}
