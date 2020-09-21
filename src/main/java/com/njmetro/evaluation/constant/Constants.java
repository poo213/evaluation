package com.njmetro.evaluation.constant;
/**
 * 全局常量
 *
 * @author RCNJTECH
 * @date 2020/4/12 12:00
 */
public class Constants {
    /**
     * http://112.4.141.91:8103
     * http://localhost:8103
     */
    public static final String THESIS_PERK_APP = "http://112.4.141.91:8103";

    /**
     * http://112.4.141.91:8096
     * http://localhost:8105
     */
    public static final String AUTHENTICATION_SERVER = "http://localhost:8105";

    /**
     * 文件路径
     */
    public static final String DOCUMENT = "C:/Thesis/Document";
    public static final String DOCUMENT_TEMP = "/Temp";
    public static final String DOCUMENT_LAST = "/Last";

    /**
     * 职工权限，论文报销人为 0，单位管理员为 1，单位负责人为 2，公司管理员为 3，超级管理员为 4
     */
    public static final int THESIS_APPLICANT = 0;
    public static final int DEPARTMENT_ADMIN = 1;
    public static final int DEPARTMENT_LEADER = 2;
    public static final int COMPANY_ADMIN = 3;
    public static final int SYSTEM_ROOT = 4;

    /**
     * 论文补贴申请的状态
     */
    public static final int THESIS_APPLICANT_SUBMITTED = 0;
    public static final int DEPARTMENT_ADMIN_REVIEWED = 1;
    public static final int DEPARTMENT_LEADER_REVIEWED = 2;
    public static final int COMPANY_ADMIN_REVIEWED = 3;

    /**
     * 审核结果，驳回为0，通过为 1
     */
    public static final int REVIEWED_REJECT = 0;
    public static final int REVIEWED_PASS = 1;

    private Constants() {
    }
}
