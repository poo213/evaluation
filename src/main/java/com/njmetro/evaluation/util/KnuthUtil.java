package com.njmetro.evaluation.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.support.StaticWebApplicationContext;

import java.util.Random;


/**
 * 抽签算法
 *
 * @author 牟欢
 * @Classname KnuthUtil
 * @Description TODO
 * @Date 2020-09-22 11:24
 */
@Slf4j
public class KnuthUtil {
    /**
     * 返回区间值中的 一个随机值
     *
     * @param min 区间最大值
     * @param max 区间最小值
     * @return
     */
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    /**
     * 将数组等概率打乱
     *
     * @param baseArray 待抽签数组
     * @return
     */
    public static Integer[] result(Integer[] baseArray) {
        for (int i = baseArray.length - 1; i >= 0; i--) {
            Integer randomIndex = randInt(0, i);
            Integer temp = baseArray[i];
            baseArray[i] = baseArray[randomIndex];
            baseArray[randomIndex] = temp;
        }
        return baseArray;
    }

    public static void main(String[] args) {
        // 初始化一维数组
        Integer[] baseArray = new Integer[41];
        System.out.println(baseArray.length);
        for (int i = 0; i < baseArray.length; i++) {
            System.out.println(i);
            baseArray[i] = i;
        }
        // 抽签
        baseArray = result(baseArray);
        // 打印抽签内容 +1
        for (int i = 0; i < baseArray.length; i++) {
            System.out.print(baseArray[i] + 1 + " ");
        }
    }
}
