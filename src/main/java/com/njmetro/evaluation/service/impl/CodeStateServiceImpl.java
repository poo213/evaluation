package com.njmetro.evaluation.service.impl;

import com.njmetro.evaluation.domain.CodeState;
import com.njmetro.evaluation.mapper.CodeStateMapper;
import com.njmetro.evaluation.mapper.StudentMapper;
import com.njmetro.evaluation.service.CodeStateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zc
 * @since 2020-10-08
 */
@Service
@RequiredArgsConstructor
public class CodeStateServiceImpl extends ServiceImpl<CodeStateMapper, CodeState> implements CodeStateService {
    private final CodeStateMapper codeStateMapper ;
    @Override
   public List<String> getIpList(Integer type)
  {
      return codeStateMapper.getIpList(type);
  };
}
