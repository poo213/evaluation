package com.njmetro.evaluation.util;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @program: evaluation
 * @description: 统计用工具类
 * @author: zc
 * @create: 2020-10-19 14:44
 **/
@Slf4j
public class StatisticUtil {
    /**
     * 计算时间分
     *
     * @param useTime      考试用时
     * @param sumOtherCent 其他总分
     * @return
     */
    public static BigDecimal getTimeCent(Integer useTime, BigDecimal sumOtherCent) {

        if (useTime <= 900) {
            return (sumOtherCent.multiply(new BigDecimal(1)).add(new BigDecimal(10))).multiply(new BigDecimal("0.1"));
            //return (sumOtherCent * 1 + 10) * 0.1;
        } else if (useTime <= 915) {
            return (sumOtherCent.multiply(new BigDecimal("0.95")).add(new BigDecimal(10))).multiply(new BigDecimal("0.1"));

            //return (sumOtherCent * 0.95 + 10) * 0.1;
        }else if (useTime >= 916 && useTime <= 930) {
            return (sumOtherCent.multiply(new BigDecimal("0.9")).add(new BigDecimal(10))).multiply(new BigDecimal("0.1"));
            //return (sumOtherCent * 0.9 + 10) * 0.1;
        }else if (useTime >= 931 && useTime <= 945) {
            return  (sumOtherCent.multiply(new BigDecimal("0.85")).add(new BigDecimal(10))).multiply(new BigDecimal("0.1"));
            //return (sumOtherCent * 0.85 + 10) * 0.1;
        }else if (useTime >= 946 && useTime <= 960) {
            return (sumOtherCent.multiply(new BigDecimal("0.8")).add(new BigDecimal(10))).multiply(new BigDecimal("0.1"));
            //return (sumOtherCent * 0.8 + 10) * 0.1;
        }else if (useTime >= 961 && useTime <= 975) {
            return  (sumOtherCent.multiply(new BigDecimal("0.75")).add(new BigDecimal(10))).multiply(new BigDecimal("0.1"));
            //return (sumOtherCent * 0.75 + 10) * 0.1;
        }else if (useTime >= 976 && useTime <= 990) {
            return (sumOtherCent.multiply(new BigDecimal("0.7")).add(new BigDecimal(10))).multiply(new BigDecimal("0.1"));
            //return (sumOtherCent * 0.7 + 10) * 0.1;
        }else if (useTime >= 991 && useTime <= 1005) {
            return  (sumOtherCent.multiply(new BigDecimal("0.65")).add(new BigDecimal(10))).multiply(new BigDecimal("0.1"));
            //return (sumOtherCent * 0.65 + 10) * 0.1;
        }else if (useTime >= 1006 && useTime <= 1020) {
            return  (sumOtherCent.multiply(new BigDecimal("0.6")).add(new BigDecimal(10))).multiply(new BigDecimal("0.1"));
            //return (sumOtherCent * 0.6 + 10) * 0.1;
        }else if (useTime >= 1021 && useTime <= 1035) {
            return  (sumOtherCent.multiply(new BigDecimal("0.55")).add(new BigDecimal(10))).multiply(new BigDecimal("0.1"));
            //return (sumOtherCent * 0.55 + 10) * 0.1;
        }else if (useTime >= 1036 && useTime <= 1050) {
            return  (sumOtherCent.multiply(new BigDecimal("0.5")).add(new BigDecimal(10))).multiply(new BigDecimal("0.1"));
            //return (sumOtherCent * 0.5 + 10) * 0.1;
        }else if (useTime >= 1051 && useTime <= 1065) {
            return  (sumOtherCent.multiply(new BigDecimal("0.45")).add(new BigDecimal(10))).multiply(new BigDecimal("0.1"));
            //return (sumOtherCent * 0.45 + 10) * 0.1;
        }else if (useTime >= 1066 && useTime <= 1080) {
            return  (sumOtherCent.multiply(new BigDecimal("0.4")).add(new BigDecimal(10))).multiply(new BigDecimal("0.1"));
            //return (sumOtherCent * 0.4 + 10) * 0.1;
        }else if (useTime >= 1081 && useTime <= 1095) {
            return  (sumOtherCent.multiply(new BigDecimal("0.35")).add(new BigDecimal(10))).multiply(new BigDecimal("0.1"));
            //return (sumOtherCent * 0.35 + 10) * 0.1;
        }else if (useTime >= 1096 && useTime <= 1110) {
            return  (sumOtherCent.multiply(new BigDecimal("0.3")).add(new BigDecimal(10))).multiply(new BigDecimal("0.1"));
            //return (sumOtherCent * 0.3 + 10) * 0.1;
        }else if (useTime >= 1111 && useTime <= 1125) {
            return  (sumOtherCent.multiply(new BigDecimal("0.25")).add(new BigDecimal(10))).multiply(new BigDecimal("0.1"));
            //return (sumOtherCent * 0.25 + 10) * 0.1;
        }else if (useTime >= 1026 && useTime <= 1140) {
            return  (sumOtherCent.multiply(new BigDecimal("0.2")).add(new BigDecimal(10))).multiply(new BigDecimal("0.1"));
            //return (sumOtherCent * 0.2 + 10) * 0.1;
        }else if (useTime >= 1141 && useTime <= 1170) {
            return  (sumOtherCent.multiply(new BigDecimal("0.15")).add(new BigDecimal(10))).multiply(new BigDecimal("0.1"));
            //return (sumOtherCent * 0.15 + 10) * 0.1;
        }else if (useTime >= 1171 && useTime <= 1200) {
            return  (sumOtherCent.multiply(new BigDecimal("0.1")).add(new BigDecimal(10))).multiply(new BigDecimal("0.1"));
            //return (sumOtherCent * 0.1 + 10) * 0.1;
        }else {
            return new BigDecimal(0);
            //return 0.0;
        }
    }
}
