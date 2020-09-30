package com.njmetro.evaluation.service;

import com.njmetro.evaluation.domain.Judge;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njmetro.evaluation.dto.JudgeInfoDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zc
 * @since 2020-09-27
 */
public interface JudgeService extends IService<Judge> {

    /**
     * 根据 IP 获取裁判详细信息
     * @param ip
     * @return
     */
    List<JudgeInfoDTO> getJudgeInfo(@Param("ip") String ip);

}
