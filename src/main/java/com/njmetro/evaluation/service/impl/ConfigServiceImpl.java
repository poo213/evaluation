package com.njmetro.evaluation.service.impl;

import com.njmetro.evaluation.domain.Config;
import com.njmetro.evaluation.mapper.ConfigMapper;
import com.njmetro.evaluation.service.ConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zc
 * @since 2020-09-27
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

}
