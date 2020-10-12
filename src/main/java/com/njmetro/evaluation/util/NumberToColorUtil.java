package com.njmetro.evaluation.util;

/**
 * @author 牟欢
 * @Classname NumberToColorUtil
 * @Description TODO
 * @Date 2020-10-10 14:23
 */
public class NumberToColorUtil {

    public static final String readyColor = "background-color: #00baff";
    public static final String testColor = "background-color: #3de7c9";
    public static final String breakColor = "background-color: #ffc530";
    public static final String endColor = "background-color: #ffffff";

    /**
     * 根据 number(1-4) 转换为对应的状态
     *
     * @param number 1:  就绪中  2： 考试中  3. 比赛中断  4. 考试结束
     * @return
     */
    public static String getBackColor(Integer number){
        String result = "";
        switch (number){
            case 1 :
                result = readyColor;
                break;
            case 2 :
                result = testColor;
                break;
            case 3 :
                result = breakColor;
                break;
            case 4 :
                result = endColor;
                break;
        }
        return result;
    }
}
