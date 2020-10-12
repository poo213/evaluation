package com.njmetro.evaluation.mapper;

import com.njmetro.evaluation.domain.CodeState;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zc
 * @since 2020-10-08
 */
public interface CodeStateMapper extends BaseMapper<CodeState> {

    @Select("select ip from pad where type = #{type}")
    List<String> getIpList(@Param("type") Integer type);
}
