### 创建数据库

CREATE DATABASE evaluation;

### 创建表

USE evaluation;
# 使用 evaluation 数据库

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
    state         INT UNSIGNED NOT NULL COMMENT '裁判就绪状态，初始值 0 ，准备就绪：1',
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

# 6.主裁对题库抽签 question_draw
### 考题抽签表
CREATE TABLE IF NOT EXISTS question_draw
(
    id            INT UNSIGNED AUTO_INCREMENT COMMENT '自增id',
    game_number   INT UNSIGNED NOT NULL COMMENT '比赛场次（1-7）',
    seat_id       INT UNSIGNED NOT NULL COMMENT '考生赛位号id',
    question_id   INT UNSIGNED NOT NULL COMMENT '题目编号',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间,即比赛开始时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;

# 7. 赛位抽签结果表，赛位上的状态

CREATE TABLE IF NOT EXISTS seat_draw
(
    id            INT UNSIGNED AUTO_INCREMENT COMMENT '自增id',
    company_id    INT UNSIGNED NOT NULL COMMENT '单位编码',
    student_id    INT UNSIGNED NOT NULL COMMENT '考生编码',
    seat_id       INT UNSIGNED NOT NULL COMMENT '赛位号id',
    draw_name     VARCHAR(20)  NOT NULL COMMENT '抽签人姓名',
    draw_time     DATETIME     NOT NULL COMMENT '抽签时间',
    game_number   INT UNSIGNED NOT NULL COMMENT '比赛场次（1-7）',
    game_round    INT UNSIGNED NOT NULL COMMENT '比赛轮次（1，2，3）',
    group_id      INT          NOT NULL COMMENT '组名1-6',
    state         INT UNSIGNED NOT NULL COMMENT '状态初始0，1.考生就绪，2.考试中，3.比赛中断,4。表示 考试结束',
    use_time      INT UNSIGNED COMMENT '比赛用时',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间，当变为4的时候，此时间表示考试结束时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;

# 8. 打分表

CREATE TABLE IF NOT EXISTS test_result
(
    id            INT UNSIGNED AUTO_INCREMENT COMMENT '自增id',
    student_id     INT UNSIGNED NOT NULL COMMENT  '考生id',
    judge_id     INT UNSIGNED NOT NULL COMMENT  '裁判id',
    question_id     INT UNSIGNED NOT NULL COMMENT  '题目id',
    question_standard_id  INT UNSIGNED NOT NULL COMMENT  '评分标准id',
    cent                  INT UNSIGNED NOT NULL COMMENT   '得分',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间，当变为4的时候，此时间表示考试结束时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;

# 9. 当前场次轮次 配置表
CREATE TABLE IF NOT EXISTS config
(
    id          INT UNSIGNED AUTO_INCREMENT COMMENT '自增id',
    game_number INT UNSIGNED NOT NULL COMMENT '比赛场次（1-7）',
    game_round  INT UNSIGNED NOT NULL COMMENT '比赛轮次（1，2，3）',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;

# 9. 平板ip 赛位对照表
CREATE TABLE IF NOT EXISTS pad
(
    id            INT UNSIGNED AUTO_INCREMENT COMMENT '平板 自增ID',
    code          VARCHAR(20)  NOT NULL COMMENT '平板编号',
    ip            VARCHAR(20)  NOT NULL COMMENT '平板绑定的ip地址',
    seat_id       INT UNSIGNED NOT NULL COMMENT '对应工位id',
    type          INT UNSIGNED NOT NULL COMMENT '平板用途（1: 考生、2:评委）',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;

# 考生 暂停记录表
CREATE TABLE IF NOT EXISTS pause_record
(
    id            INT UNSIGNED AUTO_INCREMENT COMMENT 'id',
    seat_draw_id  INT UNSIGNED NOT NULL COMMENT 'seat_draw_id id',
    type          INT UNSIGNED NOT NULL COMMENT '类型 0 :暂停  1：开始',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;


# 考生 暂停记录表
CREATE TABLE IF NOT EXISTS draw_state
(
    id            INT UNSIGNED AUTO_INCREMENT COMMENT 'id',
    draw_name     CHAR(100) NOT NULL COMMENT '抽签名称',
    state         BOOLEAN  NOT NULL COMMENT '抽签状态 true: 允许抽签   false: 不允许抽签',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;


