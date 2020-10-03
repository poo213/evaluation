package com.njmetro.evaluation.util;

import com.njmetro.evaluation.util.judgeDrawEntity.JudgeEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.support.StaticWebApplicationContext;

import java.util.ArrayList;
import java.util.List;
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

    /**
     * 随机打乱裁判顺序
     *
     * @param judgeEntityList 裁判 List
     * @return 返回洗牌后的结果
     */
    public static List<JudgeEntity> getRandomJudgeEntityList(List<JudgeEntity> judgeEntityList){
        Integer[] initArray = new Integer[judgeEntityList.size()];
        for(int i = 0 ; i < judgeEntityList.size() ; i++){
            initArray[i] = i;
        }
        Integer[] resultArray = result(initArray);
        List<JudgeEntity> resultList =  new ArrayList<>();
        for(int i = 0 ; i < resultArray.length ; i++){
            resultList.add(judgeEntityList.get(resultArray[i]));
        }
        return resultList;
    }
}
