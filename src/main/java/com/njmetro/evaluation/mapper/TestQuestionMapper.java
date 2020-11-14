package com.njmetro.evaluation.mapper;

import com.njmetro.evaluation.domain.TestQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zc
 * @since 2020-09-21
 */
public interface TestQuestionMapper extends BaseMapper<TestQuestion> {

    @Select("select test_question.id,test_question.name,test_question.seat_type,test_question.wrong_point from test_question,question_draw where question_draw.game_number=1 and question_draw.question_id=test_question.id")
    List<TestQuestion> getQuestion();
}
