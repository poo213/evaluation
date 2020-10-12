package com.njmetro.evaluation.vo.api;

import com.njmetro.evaluation.vo.GroupShowVO;
import lombok.Data;

import java.util.List;

/**
 * @author 牟欢
 * @Classname BigShowVO
 * @Description TODO
 * @Date 2020-10-10 11:07
 */
@Data
public class BigShowVO {
    public String gameNumber;
    public Integer gameRound;
    List<GroupShowVO> groupShowVOList;
}
