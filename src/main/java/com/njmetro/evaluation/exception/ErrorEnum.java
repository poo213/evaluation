package com.njmetro.evaluation.exception;

/**
 * 错误信息枚举
 *
 * @author RCNJTECH
 * @date 2020/4/11 18:37
 */
public enum ErrorEnum {
    /**
     * 需要登录
     */
    NEED_LOGIN("NeedLogin", "需要登录"),
    /**
     * 无权访问或操作
     */
    ACCESS_DENIED("AccessDenied", "无权访问或操作"),
    /**
     * 系统异常
     */
    SYSTEM_ERROR("SystemError", "系统异常"),
    /**
     * 后台获取用户信息异常
     */
    GET_USER_INFO_ERROR("GetUserInfoError", "后台获取用户信息异常"),
    /**
     * 该职工不存在
     */
    EMPLOYEE_NOT_FOUND("EmployeeNotFound", "该职工不存在"),
    /**
     * 数据保存失败
     */
    SAVE_FAILURE("SaveFailure", "数据保存失败"),
    /**
     * 上传的文件名称错误
     */
    UPLOAD_FILE_NAME_ERROR("UploadFileNameError", "上传的文件名称错误"),

    /**
     * 该论文不存在
     */
    RESULT_NOT_FOUND("ResultNotFound", "结算表数据获取异常"),

    /**
     * 考勤数据未录入
     */
    NO_ATTENDENCE("NoAttendence", "考勤数据不存在，请检查数据源"),
    /**
     * 工资数据获取异常
     */
    ERROR_SALARY("ErrorSalary", "工资数据获取异常，请检查数据源"),
    // Manager Exception
    /**
     * 更新数据记录失败
     */
    ERROR_UPDATE("ErrorSalary", "更新数据记录失败，请检查数据库"),
    // Manager Exception
    /**
     * 该管理员已存在
     */
    MANAGER_IS_EXIST("ManagerIsExist", "该管理员已存在"),

    /**
     * 审核信息保存失败
     */
    REVIEW_SAVE_FAILED("ReviewSaveFailed", "审核信息保存失败"),

    /**
     * 添加该管理员失败
     */
    MANAGER_SAVE_FAILED("ManagerSaveFailed", "添加该管理员失败"),

    /**
     * 修改该管理员失败
     */
    MANAGER_UPDATE_FAILED("ManagerUpdateFailed", "修改该管理员失败"),

    /**
     * 该管理员不存在
     */
    MANAGER_NOT_FOUND("ManagerNotFound", "该管理员不存在"),

    /**
     * 不能修改自己的权限
     */
    UPDATE_ONESELF_IS_FORBIDDEN("UpdateOneselfIsForbidden", "不能修改自己的权限"),

    /**
     * 不能删除自己
     */
    DELETE_ONESELF_IS_FORBIDDEN("DeleteOneselfIsForbidden", "不能删除自己"),


    /**
     * 请求参数错误
     */
    REQUEST_PARAM_ERROR("RequestParamError", "请求参数错误"),

    /**
     * 上传的文件为空
     */
    UPLOAD_FILE_IS_EMPTY("UploadFileIsEmpty", "上传的文件为空"),

    /**
     * 上传的文件不是 PDF 格式
     */
    UPLOAD_FILE_IS_NOT_PDF("UploadFileIsNotPdf", "上传的文件不是 PDF 格式"),

    /**
     * 上传的文件保存失败
     */
    UPLOAD_FILE_SAVE_FAILED("UploadFileSaveFailed", "上传的文件保存失败"),

    /**
     * 日期区间非法
     */
    DATE_RANGE_ILLEGAL("DateRangeIllegal", "日期区间非法"),

    /**
     * 导出错误
     */
    EXPORT_ERROR("ExportError", "导出错误"),

    /**
     * 没有数据
     */
    NO_DATA("NoData", "没有数据"),

    /**
     * 拷贝文件错误
     */
    COPY_FILE_ERROR("CopyFileError", "拷贝文件错误"),

    /**
     * 导出 Excel 文件错误
     */
    EXPORT_EXCEL_ERROR("ExportExcelError", "导出 Excel 文件错误"),

    /**
     * 导出 Zip 文件错误
     */
    EXPORT_ZIP_ERROR("ExportZipError", "导出 Zip 文件错误"),

    /**
     * 表单未填写完整
     */
    FORM_NOT_COMPLETED("FormNotCompleted", "表单未填写完整"),

    /**
     * 论文第一作者非申请者自己
     */
    THESIS_FIRST_AUTHOR_IS_NOT_ONESELF("ThesisFirstAuthorIsNotOneself", "论文第一作者非申请者自己"),

    /**
     * 保存论文补贴申请失败
     */
    THESIS_PERK_SAVE_FAILED("ThesisPerkSaveFailed", "保存论文补贴申请失败"),

    /**
     * 该配置不存在或未设置
     */
    CONFIG_NOT_FOUND_OR_NOT_SET("ConfigNotFoundOrNotSet", "该配置不存在或未设置"),

    /**
     * 配置修改失败
     */
    CONFIG_UPDATE_FAILED("ConfigUpdateFailed", "配置修改失败"),

    // 审核

    /**
     * 无审核驳回记录
     */
    NO_REVIEW_REJECT_RECORD("NoReviewRejectRecord", "无审核驳回记录");

    /**
     * 错误代码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    ErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
