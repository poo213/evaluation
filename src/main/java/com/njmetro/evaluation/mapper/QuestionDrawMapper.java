package com.njmetro.evaluation.mapper;

import com.njmetro.evaluation.domain.QuestionDraw;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njmetro.evaluation.vo.QuestionDrawVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zc
 * @since 2020-09-29
 */
public interface QuestionDrawMapper extends BaseMapper<QuestionDraw> {

    /**
     * 获取已经抽签的考题结果
     * @return
     */
    @Select("SELECT game_number,game_type,test_question.name\n" +
            "FROM test_question,question_draw\n" +
            "WHERE test_question.id = question_draw.question_id")
    List<QuestionDrawVO> selectList();

}
