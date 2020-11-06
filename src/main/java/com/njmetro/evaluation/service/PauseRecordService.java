package com.njmetro.evaluation.service;

import com.njmetro.evaluation.domain.PauseRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njmetro.evaluation.vo.PauseRecordVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zc
 * @since 2020-09-30
 */
public interface PauseRecordService extends IService<PauseRecord> {
    /***
     * @Description 根据姓名获取到暂停的记录
     * @param
     * @return java.util.List<com.njmetro.evaluation.vo.PauseRecordVO>
     * @date 2020-11-6 10:57
     * @auther zc
     */
    List<PauseRecordVO> getPauseAdjustList(String name);
}
