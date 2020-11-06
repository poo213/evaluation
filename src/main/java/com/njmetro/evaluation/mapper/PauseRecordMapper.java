package com.njmetro.evaluation.mapper;

import com.njmetro.evaluation.domain.PauseRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njmetro.evaluation.vo.PauseRecordVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zc
 * @since 2020-09-30
 */
public interface PauseRecordMapper extends BaseMapper<PauseRecord> {
    @Select("select pause_record.id,pause_record.game_number,pause_record.student_id,pause_record.game_round,student.name as student_name,student.company_name,pause_record.pause_time,pause_record.flag from pause_record,student where pause_record.student_id = student.id and student.name = #{name} and pause_record.pause_time is not null ")
    List<PauseRecordVO> getPauseAdjustList(@Param("name") String name);
}
