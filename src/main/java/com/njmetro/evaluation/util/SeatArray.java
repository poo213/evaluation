package com.njmetro.evaluation.util;

import lombok.Data;

import java.util.List;

/**
 * @author 牟欢
 * @Classname seatArray
 * @Description TODO
 * @Date 2020-09-23 19:50
 */
@Data
public class SeatArray {
    /**
     * 座位名称 A-F 6个座位区
     */
    public String seatName;

    /**
     * 裁判是否全部抽签完毕
     */
    public Boolean flag;

    public List<Integer> companyId;

}
