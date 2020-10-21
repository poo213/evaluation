package com.njmetro.evaluation.service;

import com.njmetro.evaluation.domain.Judge;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njmetro.evaluation.dto.JudgeInfoDTO;
import com.njmetro.evaluation.vo.JudgeDrawVO;
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

    /**
     * 根据教考类型查询裁判信息
     * @param type 裁判监考类型
     * @return
     */
    List<JudgeDrawVO> getJudgeDrawVOByType(@Param("type") String type);

    /**
     * 重置选择12个执行裁判的结果
     *
     * @return
     */
    Boolean resetMaster();

    /**
     *  重置裁判监考类型
     *
     * @return
     *
     */
    Boolean resetTypeAndMaster();
}

