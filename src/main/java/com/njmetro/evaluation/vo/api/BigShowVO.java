package com.njmetro.evaluation.vo.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.njmetro.evaluation.vo.GroupShowVO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 牟欢
 * @Classname BigShowVO
 * @Description TODO
 * @Date 2020-10-10 11:07
 */
@Data
public class BigShowVO {
    /**
     * 当前时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    public LocalDateTime dateTime;
    /**
     * 候考区人数
     */
    public Integer waitNumber;
    /**
     * 备考区人数
     */
    public Integer readyNumber;

    /**
     * 考试中人数
     */
    public Integer testNumber;
    /**
     * 立场人数
     */
    public Integer leaveNumber;
    public Integer gameNumber;
    public Integer gameRound;
    List<GroupShowVO> groupShowVOList;
}
