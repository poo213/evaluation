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
     * 根据裁判赛位获取考试类型
     * @param judgeSeatId 裁判座位Id
     * @return
     */
    public static  String getTypeNameByJudgeSeatId(Integer judgeSeatId){
        Integer studentSeatId = getStudentSeatIdByJudgeSeatId(judgeSeatId);
        return getTypeNameByStudentSeatId(studentSeatId);
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
    /**
     * 根据学生座位ID转换成123
     *
     * @param studentSeatId 学生座位id
     * @return
     */
    public static Integer getTypeNumByStudentSeatId(Integer studentSeatId){
        Integer type=0;
        switch (studentSeatId % 3){
            case 0:
                type = 3;
                break;
            case 1:
                type = 1;
                break;
            case 2:
                type = 2;
                break;
        }
        return type;
    }

    /**
     * 根据学生赛位ID获取考试类型
     * @param studentSeatId
     * @return
     */
    public static String getTypeNameByStudentSeatId(Integer studentSeatId){
        String type = "";
        switch (studentSeatId % 3){
            case 0:
                type = SystemCommon.VIDEO_TYPE;
                break;
            case 1:
                type = SystemCommon.OPTICAL_TYPE;
                break;
            case 2:
                type = SystemCommon.SWITCH_TYPE;
                break;
        }
        return type;
    }

    /**
     * 根据学生座位ID转换成字母
     *
     * @param num 学生座位id
     * @return
     */
    public static String exchangeGroup(Integer num){
        String groupName="";
        switch (num){
            case 1:
                groupName = "A";
                break;
            case 2:
                groupName = "B";
                break;
            case 3:
                groupName = "C";
                break;
            case 4:
                groupName = "D";
                break;
            case 5:
                groupName = "E";
                break;
            case 6:
                groupName = "F";
                break;
        }
        return groupName;
    }

    /**
     * 根据座Id 获取座位名称
     * @param id 座位Id
     * @return
     */
    public static String getSeatNameById(Integer id){
        String seatName = "";
        switch (id){
            case 1:
                seatName = "A-1";
                break;
            case 2:
                seatName = "A-2";
                break;
            case 3:
                seatName = "A-3";
                break;
            case 4:
                seatName = "B-1";
                break;
            case 5:
                seatName = "B-2";
                break;
            case 6:
                seatName = "B-3";
                break;
            case 7:
                seatName = "C-1";
                break;
            case 8:
                seatName = "C-2";
                break;
            case 9:
                seatName = "C-3";
                break;
            case 10:
                seatName = "D-1";
                break;
            case 11:
                seatName = "D-2";
                break;
            case 12:
                seatName = "D-3";
                break;
            case 13:
                seatName = "E-1";
                break;
            case 14:
                seatName = "E-2";
                break;
            case 15:
                seatName = "E-3";
                break;
            case 16:
                seatName = "F-1";
                break;
            case 17:
                seatName = "F-2";
                break;
            case 18:
                seatName = "F-3";
                break;
        }
        return seatName;
    }

    /**
     * 根据赛位 名称获取赛位Id
     * @param seatName 座位Id
     * @return
     */
    public static Integer getSeatIdBySeatName(String seatName){
        Integer seatId = 0;
        switch (seatName){
            case "A-1":
                seatId = 1;
                break;
            case "A-2":
                seatId = 2;
                break;
            case "A-3":
                seatId = 3;
                break;
            case "B-1":
                seatId = 4;
                break;
            case "B-2":
                seatId = 5;
                break;
            case "B-3":
                seatId = 6;
                break;
            case "C-1":
                seatId = 7;
                break;
            case "C-2":
                seatId = 8;
                break;
            case "C-3":
                seatId = 9;
                break;
            case "D-1":
                seatId = 10;
                break;
            case "D-2":
                seatId = 11;
                break;
            case "D-3":
                seatId = 12;
                break;
            case "E-1":
                seatId = 13;
                break;
            case "E-2":
                seatId = 14;
                break;
            case "E-3":
                seatId = 15;
                break;
            case "F-1":
                seatId = 16;
                break;
            case "F-2":
                seatId = 17;
                break;
            case "F-3":
                seatId = 18;
                break;
        }
        return seatId;
    }



}
