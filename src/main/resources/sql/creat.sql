### 创建数据库

CREATE DATABASE evaluation;

### 创建表

USE evaluation; # 使用 evaluation 数据库

# 1. 参赛单位表 company

CREATE TABLE IF NOT EXISTS company
(
    id            INT UNSIGNED AUTO_INCREMENT COMMENT '参赛单位 自增ID',
    code          VARCHAR(20)  NOT NULL COMMENT '单位编码',
    name          VARCHAR(20)  NOT NULL COMMENT '单位名称',
    introduction  VARCHAR(255) NOT NULL COMMENT '单位简介',
    leader_name   VARCHAR(20)  NOT NULL COMMENT '领队姓名',
    leader_phone  VARCHAR(11)  NOT NULL COMMENT '领队联系方式 ',
    draw_result   INT UNSIGNED NOT NULL COMMENT '抽签结果',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;

# 2. 裁判基础信息表 judge

CREATE TABLE IF NOT EXISTS judge
(
    id                   INT UNSIGNED AUTO_INCREMENT COMMENT '评委 自增ID',
    code                 VARCHAR(20)  NOT NULL COMMENT '评委编号',
    name                 VARCHAR(20)  NOT NULL COMMENT '评委姓名',
    id_card              VARCHAR(18)  NOT NULL COMMENT '评委身份证号',
    sex                  INT UNSIGNED NOT NULL COMMENT '性别 0：男 1：女',
    age                  INT UNSIGNED NOT NULL COMMENT '年龄',
    phone                VARCHAR(11)  NOT NULL COMMENT '评委手机号',
    company_name         VARCHAR(20)  NOT NULL COMMENT '评委所在单位名称',
    company_id           INT UNSIGNED NOT NULL COMMENT '评委所在单位id',
    two_dimensional_code VARCHAR(20)  NOT NULL COMMENT '二维码编号',
    sign_state           CHAR(1)      NOT NULL COMMENT '签到1 未签到0',
    judge_type           VARCHAR(20)  NOT NULL COMMENT '监考类型（抽签决定）',
    master               INT UNSIGNED NOT NULL COMMENT '一般裁判1，备用裁判0',
    create_time          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;

# 3. 考生基础信息表 student
CREATE TABLE IF NOT EXISTS student
(
    id                   INT UNSIGNED AUTO_INCREMENT COMMENT '考生 自增ID',
    code                 VARCHAR(20)  NOT NULL COMMENT '考生编号',
    name                 VARCHAR(20)  NOT NULL COMMENT '考生姓名',
    id_card              VARCHAR(18)  NOT NULL COMMENT '身份证号',
    sex                  INT UNSIGNED NOT NULL COMMENT '性别 0：男 1：女',
    age                  INT UNSIGNED NOT NULL COMMENT '年龄',
    phone                VARCHAR(11)  NOT NULL COMMENT '考生手机号',
    company_name         VARCHAR(50)  NOT NULL COMMENT '考生单位名称',
    two_dimensional_code VARCHAR(20)  NOT NULL COMMENT '二维码编号',
    leader               INT UNSIGNED NOT NULL COMMENT '是否为队长 1: 是队长 0：不是队长',
    sign_state           CHAR(1)      NOT NULL COMMENT '签到状态： 签到1 未签到0',
    create_time          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;

# 4. 裁判抽签结果表 judge_draw_result

CREATE TABLE IF NOT EXISTS judge_draw_result
(
    id            INT UNSIGNED AUTO_INCREMENT COMMENT '自增id',
    seat_id       INT UNSIGNED NOT NULL COMMENT '赛位id',
    judge_id      INT UNSIGNED NOT NULL COMMENT '评委id',
    pad_id        INT UNSIGNED NOT NULL COMMENT '平板id',
    group_id      INT UNSIGNED NOT NULL COMMENT '所在赛组id',
    type_name     CHAR(10)     NOT NULL COMMENT '监考类型 光缆接续 交换机组网 视频搭建',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;

# 5. 赛组信息表  seat_group

### 创建赛组 seat_group
CREATE TABLE IF NOT EXISTS seat_group
(
    id            INT UNSIGNED AUTO_INCREMENT COMMENT '自增id',
    group_name    CHAR(20) NOT NULL COMMENT '赛组名称',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;



