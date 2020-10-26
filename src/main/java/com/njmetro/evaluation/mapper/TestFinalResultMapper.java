package com.njmetro.evaluation.mapper;

import com.njmetro.evaluation.domain.TestFinalResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njmetro.evaluation.vo.TestCompanyResultVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zc
 * @since 2020-10-26
 */
public interface TestFinalResultMapper extends BaseMapper<TestFinalResult> {
    @Select("select company_name, sum(comprehensive_result) as comprehensive_result from test_final_result group by company_name")
    List<TestCompanyResultVO> getCompanyResult();
}
