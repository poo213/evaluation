CREATE DATABASE evaluation;
USE evaluation;

### 创建表
# 1. 参赛单位
CREATE TABLE IF NOT EXISTS company
(
    id            INT UNSIGNED AUTO_INCREMENT COMMENT '参赛单位 自增ID',
    code          VARCHAR(20)  NOT NULL COMMENT '单位编码',
    name          VARCHAR(20)  NOT NULL COMMENT '单位名称',
    introduction  VARCHAR(255) NOT NULL COMMENT '单位简介',
    leader_name   VARCHAR(20)  NOT NULL COMMENT '领队姓名',
    leader_phone  VARCHAR(11)  NOT NULL COMMENT '领队联系方式 ',
    draw_result   INT UNSIGNED NOT NULL COMMENT '排序  初始值0',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;

# 2. 考生表

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
    sign_state           CHAR(1)      NOT NULL COMMENT '签到1 未签到0',
    test_day_state       INT UNSIGNED NOT NULL COMMENT '考试当天状态，初始0，进入候考区：1，进入候考区：2，进入考场：3，离开考场：4',
    create_time          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;

# 3. 评委  Judges

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
    two_dimensional_code VARCHAR(20)  NOT NULL COMMENT '二维码编号',
    sign_state           CHAR(1)      NOT NULL COMMENT '签到1 未签到0',
    judge_type           VARCHAR(20)  NOT NULL COMMENT '监考类型（抽签决定）',
    master               INT UNSIGNED NOT NULL COMMENT '一般裁判1，备用裁判0',
    create_time          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;


# 4. 赛位表 seat

CREATE TABLE IF NOT EXISTS seat
(
    id            INT UNSIGNED AUTO_INCREMENT COMMENT '赛位 自增ID',
    code          VARCHAR(20)  NOT NULL COMMENT '赛位编号',
    name          VARCHAR(20)  NOT NULL COMMENT '赛位名称',
    type          VARCHAR(20)  NOT NULL COMMENT '赛位类型 光缆接续、视频搭建、交换机组网',
    group_number  INT UNSIGNED NOT NULL COMMENT '赛位组号 1-6组',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;

# 5. 平板管理 pad

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


# 6. 考题 test_question

CREATE TABLE IF NOT EXISTS test_question
(
    id            INT UNSIGNED AUTO_INCREMENT COMMENT '考题 自增ID',
    code          VARCHAR(20)  NOT NULL COMMENT '考题编号',
    name          VARCHAR(20)  NOT NULL COMMENT '考题名称',
    seat_type     INT UNSIGNED NOT NULL COMMENT '赛位类型',
    url           VARCHAR(255) NOT NULL COMMENT '考题URL',
    test_time     INT UNSIGNED NOT NULL COMMENT '考试时长',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;


# 7 考题的评分标准  test_question_standard

CREATE TABLE IF NOT EXISTS test_question_standard
(
    id               INT UNSIGNED AUTO_INCREMENT COMMENT '评分标准 自增ID',
    test_question_id INT UNSIGNED NOT NULL COMMENT '评分标准所在考题 id',
    text             VARCHAR(255) NOT NULL COMMENT '考核内容',
    point            VARCHAR(255) NOT NULL COMMENT '考核点',
    score            INT UNSIGNED NOT NULL COMMENT '分值',
    standard         VARCHAR(255) NOT NULL COMMENT '评分标准',
    step             INT UNSIGNED NOT NULL COMMENT '评分步长',
    `create_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;


# 8 考生签到表 student_sign 记录进入候考区，备考区，扫码离开几个状态

CREATE TABLE IF NOT EXISTS student_sign
(
    id            INT UNSIGNED AUTO_INCREMENT COMMENT '签到表自增ID',
    company_name  VARCHAR(20)  NOT NULL COMMENT '考生所在单位名称',
    student_id    INT UNSIGNED NOT NULL COMMENT '考生id',
    sign_time     DATETIME     NOT NULL COMMENT '考生签到时间',
    state         INT UNSIGNED NOT NULL COMMENT '考生签到，0表示进入候考区，1、进入备考区，2、考试中，3扫码离开',
    register_name VARCHAR(20)  NOT NULL COMMENT '登记人姓名',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;

# 9 裁判签到表 student_sign

# CREATE TABLE IF NOT EXISTS judge_sign
# (
#     id            INT UNSIGNED AUTO_INCREMENT COMMENT '裁判签到表自增ID',
#     company_name  VARCHAR(20)  NOT NULL COMMENT '裁判所在单位名称',
#     judge_id      INT UNSIGNED NOT NULL COMMENT '裁判id',
#     sign_time     DATETIME     NOT NULL COMMENT '裁判签到时间',
#     state         INT UNSIGNED NOT NULL COMMENT '裁判签到状态 1： 已签到 0： 未签到',
#     register_name VARCHAR(20)  NOT NULL COMMENT '登记人姓名',
#     number        INT UNSIGNED NOT NULL COMMENT '第几次签到',
#     `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
#     `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
#     PRIMARY KEY (id)
# ) ENGINE = InnoDB
#   DEFAULT CHARSET = UTF8MB4;

# 11 评委抽签结果表  judge_draw_result

CREATE TABLE IF NOT EXISTS judge_draw_result
(
    id            INT UNSIGNED AUTO_INCREMENT COMMENT '自增id',
    seat_id       INT UNSIGNED NOT NULL COMMENT '赛位id',
    judge_id      INT UNSIGNED NOT NULL COMMENT '评委id',
    pad_id        INT UNSIGNED NOT NULL COMMENT '平板id',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;

# 12 考生赛位报道表（参赛人员 开始考试）（和14表合并，见下方）

# CREATE TABLE IF NOT EXISTS student_seat_sign
# (
#     id            INT UNSIGNED AUTO_INCREMENT COMMENT '自增id',
#     student_id    INT UNSIGNED NOT NULL COMMENT '人员编号id',
#     seat_id       INT UNSIGNED NOT NULL COMMENT '赛位号id',
#
#     game_number   INT UNSIGNED NOT NULL COMMENT '比赛场次',
#     state         INT UNSIGNED NOT NULL COMMENT '报道状态，0表示登录（正在考试）',
#     sign_time     DATETIME     NOT NULL COMMENT '考生签到时间',
#     `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
#     `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
#     PRIMARY KEY (id)
# ) ENGINE = InnoDB
#   DEFAULT CHARSET = UTF8MB4;


# 13 赛位绑定信息表（评委）

CREATE TABLE IF NOT EXISTS judge_seat_sign
(
    id            INT UNSIGNED AUTO_INCREMENT COMMENT '自增id',
    seat_id       INT UNSIGNED NOT NULL COMMENT '赛位号id',
    pad_id        INT UNSIGNED NOT NULL COMMENT 'pad id',
    judge_id      INT UNSIGNED NOT NULL COMMENT '评委id',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;


# 14 赛位抽签结果表，赛位上的状态

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

#暂停记录
CREATE TABLE IF NOT EXISTS pause_record
(
    id            INT UNSIGNED AUTO_INCREMENT COMMENT '自增id',
    seat_draw_id    INT UNSIGNED NOT NULL COMMENT 'seat_draw_id',
    use_time      INT UNSIGNED COMMENT '比赛用时',
    state           INT UNSIGNED COMMENT '0暂停，1开始',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间,即暂停时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;
# 14 主裁参数配置
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

#主裁对题库抽签
CREATE TABLE IF NOT EXISTS question_draw
(
    id             INT UNSIGNED AUTO_INCREMENT COMMENT '自增id',
    game_number    INT UNSIGNED NOT NULL COMMENT '比赛场次（1-7）',
    seat_id        INT UNSIGNED NOT NULL COMMENT '考生赛位号id',
    question_id    INT UNSIGNED NOT NULL COMMENT '题目编号',
    `create_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;

# 15  考生得分表  student_score

# CREATE TABLE IF NOT EXISTS student_score
# (
#     id               INT UNSIGNED AUTO_INCREMENT COMMENT '评分标准 自增ID',
#     seat_id INT UNSIGNED NOT NULL COMMENT '赛位 id',
#     student_id INT UNSIGNED NOT NULL COMMENT '考生 id',
#     judge_id INT UNSIGNED NOT NULL COMMENT '评委 id',
#     game_number   INT UNSIGNED NOT NULL COMMENT '比赛场次',
#     see_question_time     DATETIME     NOT NULL COMMENT '看题开始时间',
#     start_time     DATETIME     NOT NULL COMMENT '开始时间',
#     end_time     DATETIME     NOT NULL COMMENT '结束时间',
#
#     ## 怎么写
#     `create_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
#     `update_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
#     PRIMARY KEY (id)
# ) ENGINE = InnoDB
#   DEFAULT CHARSET = UTF8MB4;




















