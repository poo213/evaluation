package com.njmetro.evaluation.service;

import com.njmetro.evaluation.domain.CodeState;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njmetro.evaluation.vo.MenuVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zc
 * @since 2020-10-08
 */
public interface CodeStateService extends IService<CodeState> {
    List<String> getIpList(Integer type);
}
