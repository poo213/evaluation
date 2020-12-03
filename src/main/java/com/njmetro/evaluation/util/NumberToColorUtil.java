package com.njmetro.evaluation.util;

/**
 * @author 牟欢
 * @Classname NumberToColorUtil
 * @Description TODO
 * @Date 2020-10-10 14:23
 */
public class NumberToColorUtil {

    /**
     * 考生状态 对应颜色
     */
    public static final String studentNoReadyColor = "#ffffff";
    public static final String studentReadyColor = "#00baff";
    public static final String studentTestColor = "#ffc530";
    public static final String studentBreakColor = "#3de7c9";
    public static final String studentEndColor = "#a0d911";
    public static final String studentMissColor = "#722ed1";
    public static final String studentErrorColor = "#f5222d";
    /**
     * 裁判状态对应颜色
     */
    public static final String judgeNoReadyColor = "#ffffff";
    public static final String judgeReadyColor = "#00baff";
    public static final String judgeTestColor = "#3de7c9";
    public static final String judgeOkColor = "#a0d911";

    /**
     * 根据 number(1-6) 转换为对应的状态
     *
     * @param number 1:  就绪中  2： 考试中  3. 比赛中断  4. 考试结束 5. 考生缺考  6. 考生违纪
     * @return
     */
    public static String getStudentBackColor(Integer number) {
        String result = "";
        switch (number) {
            case 0:
                result = studentNoReadyColor;
                break;
            case 1:
                result = studentReadyColor;
                break;
            case 2:
                result = studentTestColor;
                break;
            case 3:
                result = studentBreakColor;
                break;
            case 4:
                result = studentEndColor;
                break;
            case 5:
                result = studentMissColor;
                break;
            case 6:
                result = studentErrorColor;
                break;
        }
        return result;
    }

    /**
     * 根据 number(1-6) 转换为对应的状态
     *
     * @param number 1:  未就绪  2： 已就绪  3. 监考中  4. 成绩以及叫
     * @return
     */
    public static String getJudgeBackColor(Integer number) {
        String result = "";
        switch (number) {
            case 0:
                result = judgeNoReadyColor;
                break;
            case 1:
                result = judgeReadyColor;
                break;
            case 2:
                result = judgeTestColor;
                break;
            case 3:
                result = judgeOkColor;
                break;
        }
        return result;
    }
}
