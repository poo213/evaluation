package com.njmetro.evaluation.util;

/**
 * @author 牟欢
 * @Classname NumberToCharUtil
 * @Description TODO
 * @Date 2020-10-10 10:57
 */
public class NumberToCharUtil {

    public static String getChar(Integer number) {
        String result = "";
        switch (number) {
            case 1:
                result = "一";
                break;
            case 2:
                result = "二";
                break;
            case 3:
                result = "三";
                break;
            case 4:
                result = "四";
                break;
            case 5:
                result = "五";
                break;
            case 6:
                result = "六";
                break;
            case 7:
                result = "七";
                break;
        }
        return result;
    }
}
