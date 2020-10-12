package com.njmetro.evaluation.util;

import com.njmetro.evaluation.common.SystemCommon;
import com.njmetro.evaluation.domain.JudgeDrawResult;
import lombok.Data;

/**
 * 赛位信息转换
 *
 * @author 牟欢
 * @Classname JudgeSeatUtil
 * @Description TODO
 * @Date 2020-10-01 13:36
 */
@Data
public class SeatUtil {

    /**
     * 根据裁判所在赛组和监考类型 获取左侧裁判 座位id
     *
     * @param groupId 裁判所在组
     * @param type    裁判监考类型
     * @return
     */
    public static Integer getLeftJudgeSeatIdByGroupIdAndType(Integer groupId, String type) {
        Integer leftJudgeSeatId = 0;
        switch (type) {
            case SystemCommon.OPTICAL_TYPE:
                leftJudgeSeatId = (groupId - 1) * 6 + 1;
                break;
            case SystemCommon.SWITCH_TYPE:
                leftJudgeSeatId = (groupId - 1) * 6 + 3;
                break;
            case SystemCommon.VIDEO_TYPE:
                leftJudgeSeatId = (groupId - 1) * 6 + 5;
                break;

        }
        return leftJudgeSeatId;
    }

    /**
     * 根据裁判所在赛组和监考类型 获取右侧裁判 座位id
     *
     * @param groupId 裁判所在组
     * @param type    裁判监考类型
     * @return
     */
    public static Integer getRightJudgeSeatIdByGroupIdAndType(Integer groupId, String type) {
        Integer rightJudgeSeatId = 0;
        switch (type) {
            case SystemCommon.OPTICAL_TYPE:
                rightJudgeSeatId = (groupId - 1) * 6 + 2;
                break;
            case SystemCommon.SWITCH_TYPE:
                rightJudgeSeatId = (groupId - 1) * 6 + 4;
                break;
            case SystemCommon.VIDEO_TYPE:
                rightJudgeSeatId = (groupId - 1) * 6 + 6;
                break;

        }
        return rightJudgeSeatId;
    }

    /**
     * 根据裁判座位id 获取 裁判所在 赛组id
     *
     * @param judgeSeatId 裁判赛组Id
     * @return
     */
    public static Integer getGroupIdByJudgeSeatId(Integer judgeSeatId) {
        return judgeSeatId / 6 + 1;
    }

    /**
     * 根据裁判所在赛组和监考类型 获取考生所在座位id
     *
     * @param groupId 裁判所在组
     * @param type    裁判监考类型
     * @return
     */
    public static Integer getStudentSeatIdByGroupIdAndType(Integer groupId, String type) {
        Integer studentSeatId = 0;
        switch (type) {
            case SystemCommon.OPTICAL_TYPE:
                studentSeatId = (groupId - 1) * 3 + 1;
                break;
            case SystemCommon.SWITCH_TYPE:
                studentSeatId = (groupId - 1) * 3 + 2;
                break;
            case SystemCommon.VIDEO_TYPE:
                studentSeatId = (groupId - 1) * 3 + 3;
                break;

        }
        return studentSeatId;
    }

    /**
     * 根据考生座位id 获取 考生所在 赛组id
     *
     * @param studentSeatId 裁判赛组Id
     * @return
     */
    public static Integer getGroupIdByStudentSeatId(Integer studentSeatId) {
        return studentSeatId / 3 + 1;
    }


    /**
     * 根据裁判座位id 获取 考生所在id
     *
     * @param judgeSeatId 裁判Id
     * @return
     */
    public static Integer getStudentSeatIdByJudgeSeatId(Integer judgeSeatId) {
        return (judgeSeatId + 1) / 2;
    }

    /**
     * 根据考生Id 获取左侧裁判Id
     *
     * @param studentId 考生Id
     * @return
     */
    public static Integer getLeftJudgeSeatIdByStudentSeatId(Integer studentId) {
        return studentId * 2 - 1;
    }

    /**
     * 根据考生Id 获取右侧裁判Id
     *
     * @param studentId 考生Id
     * @return
     */
    public static Integer getRightJudgeSeatIdByStudentSeatId(Integer studentId) {
        return studentId * 2;
    }

    /**
     * 根据学生座位ID获取考试类型
     *
     * @param studentSeatId 学生座位id
     * @return
     */
    public static String getGameTypeByStudentSeatId(Integer studentSeatId){
        String typeName = "";
        switch (studentSeatId % 3){
            case 0:
                typeName = "视频搭建";
                break;
            case 1:
                typeName = "光缆接续";
                break;
            case 2:
                typeName = "交换机组网";
                break;
        }
        return typeName;
    }
}
