package com.njmetro.evaluation.service;

import com.njmetro.evaluation.domain.QuestionDraw;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njmetro.evaluation.vo.QuestionDrawVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zc
 * @since 2020-09-29
 */
public interface QuestionDrawService extends IService<QuestionDraw> {
    /**
     * 获取已经抽签的考题结果
     * @return
     */
    List<QuestionDrawVO> selectList();
}
