package com.njmetro.evaluation.mapper;

import com.njmetro.evaluation.domain.SeatDraw;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

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

}
