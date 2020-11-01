### 文件简介 ： 初始化项目中表格基础数据

### 一、 重置项目表格基础数据（清空表格数据，重置表格自增长）

# 1. 重置 judge_draw_result 表

TRUNCATE TABLE judge_draw_result;

# 2. 重置 seat_group

TRUNCATE TABLE seat_group;

# 3. 重置 config 表

TRUNCATE TABLE config;

# 4. 重置 pad 表

TRUNCATE TABLE pad;

# 5. 重置 draw_state 表
TRUNCATE TABLE draw_state;

# 6. 重置裁判评分表
TRUNCATE TABLE test_result;

# 7. 重置座位抽签表
TRUNCATE TABLE seat_draw;

# 8. 清空 code_state
TRUNCATE TABLE code_state;

### 二、 生成项目表格基础数据

# 1.  初始化 judge_draw_result 表

INSERT INTO judge_draw_result(seat_id, judge_id, pad_id, group_id, type_name, state)
values (1, 0, 19, 1, '光缆续接', 0),
       (2, 0, 20, 1, '光缆续接', 0),
       (3, 0, 21, 1, '视频搭建', 0),
       (4, 0, 22, 1, '视频搭建', 0),
       (5, 0, 23, 1, '交换机组网', 0),
       (6, 0, 24, 1, '交换机组网', 0),

       (7, 0, 25, 2, '光缆续接', 0),
       (8, 0, 26, 2, '光缆续接', 0),
       (9, 0, 27, 2, '视频搭建', 0),
       (10, 0, 28, 2, '视频搭建', 0),
       (11, 0, 29, 2, '交换机组网', 0),
       (12, 0, 30, 2, '交换机组网', 0),

       (13, 0, 31, 3, '光缆续接', 0),
       (14, 0, 32, 3, '光缆续接', 0),
       (15, 0, 33, 3, '视频搭建', 0),
       (16, 0, 34, 3, '视频搭建', 0),
       (17, 0, 35, 3, '交换机组网', 0),
       (18, 0, 36, 3, '交换机组网', 0),

       (19, 0, 37, 4, '光缆续接', 0),
       (20, 0, 38, 4, '光缆续接', 0),
       (21, 0, 39, 4, '视频搭建', 0),
       (22, 0, 40, 4, '视频搭建', 0),
       (23, 0, 41, 4, '交换机组网', 0),
       (24, 0, 42, 4, '交换机组网', 0),

       (25, 0, 43, 5, '光缆续接', 0),
       (26, 0, 44, 5, '光缆续接', 0),
       (27, 0, 45, 5, '视频搭建', 0),
       (28, 0, 46, 5, '视频搭建', 0),
       (29, 0, 47, 5, '交换机组网', 0),
       (30, 0, 48, 5, '交换机组网', 0),

       (31, 0, 49, 6, '光缆续接', 0),
       (32, 0, 50, 6, '光缆续接', 0),
       (33, 0, 51, 6, '视频搭建', 0),
       (34, 0, 52, 6, '视频搭建', 0),
       (35, 0, 53, 6, '交换机组网', 0),
       (36, 0, 54, 6, '交换机组网', 0);

# 2. 初始化赛组信息

INSERT INTO seat_group(group_name)
VALUES ('A组'),
       ('B组'),
       ('C组'),
       ('D组'),
       ('E组'),
       ('F组');


# 3. 初始化 config 表（只存放一条记录）

INSERT INTO config(game_number, game_round) value (1, 1);


# 4. 初始化 pad 配置表

INSERT INTO pad (code, ip, seat_id, type)
VALUES ('******', '172.18.1.228', 1, 1),
       ('******', '172.18.1.229', 2, 1),
       ('******', '172.18.1.230', 3, 1),

       ('******', '192.168.96.4', 4, 1),
       ('******', '192.168.96.5', 5, 1),
       ('******', '192.168.96.6', 6, 1),
       ('******', '192.168.96.7', 7, 1),
       ('******', '192.168.96.8', 8, 1),
       ('******', '192.168.96.9', 9, 1),
       ('******', '192.168.96.10', 10, 1),
       ('******', '192.168.96.11', 11, 1),
       ('******', '192.168.96.12', 12, 1),
       ('******', '192.168.96.13', 13, 1),
       ('******', '192.168.96.14', 14, 1),
       ('******', '192.168.96.15', 15, 1),
       ('******', '192.168.96.16', 16, 1),
       ('******', '192.168.96.17', 17, 1),
       ('******', '192.168.96.18', 18, 1),


       ('******', '172.18.1.231', 1, 2),
       ('******', '172.18.1.232', 2, 2),
       ('******', '172.18.1.233', 3, 2),
       ('******', '172.18.1.234', 4, 2),
       ('******', '172.18.1.235', 5, 2),
       ('******', '172.18.1.236', 6, 2),

       ('******', '192.168.97.7', 7, 2),
       ('******', '192.168.97.8', 8, 2),
       ('******', '192.168.97.9', 9, 2),
       ('******', '192.168.97.10', 10, 2),
       ('******', '192.168.97.11', 11, 2),
       ('******', '192.168.97.12', 12, 2),
       ('******', '192.168.97.13', 13, 2),
       ('******', '192.168.97.14', 14, 2),
       ('******', '192.168.97.15', 15, 2),
       ('******', '192.168.97.16', 16, 2),
       ('******', '192.168.97.17', 17, 2),
       ('******', '192.168.97.18', 18, 2),
       ('******', '192.168.97.19', 19, 2),
       ('******', '192.168.97.20', 20, 2),
       ('******', '192.168.97.21', 21, 2),
       ('******', '192.168.97.22', 22, 2),
       ('******', '192.168.97.23', 23, 2),
       ('******', '192.168.97.24', 24, 2),
       ('******', '192.168.97.25', 25, 2),
       ('******', '192.168.97.26', 26, 2),
       ('******', '192.168.97.27', 27, 2),
       ('******', '192.168.97.28', 28, 2),
       ('******', '192.168.97.29', 29, 2),
       ('******', '192.168.97.30', 30, 2),
       ('******', '192.168.97.31', 31, 2),
       ('******', '192.168.97.32', 32, 2),
       ('******', '192.168.97.33', 33, 2),
       ('******', '192.168.97.34', 34, 2),
       ('******', '192.168.97.35', 35, 2),
       ('******', '192.168.97.36', 36, 2),

       ('******', '172.18.1.239', 36, 3),
       ('******', '172.18.1.240', 36, 4),
       ('******', '172.18.1.241', 36, 5);


# 5. 初始化抽签状态表

INSERT
INTO draw_state(draw_name, state)
values ('参赛队抽签', true),
       ('考生赛位抽签', false),
       ('裁判类型抽签', true),
       ('执行裁判抽签', false),
       ('裁判位置抽签', false);
# 6. 打分结果表
INSERT INTO test_result(game_number, game_round, question_id, question_standard_id, cent, student_id, judge_id)
values (1, 1, 1, 1, 10, 1, 1),
       (1, 1, 1, 2, 10, 1, 1),
       (1, 1, 1, 3, 10, 1, 1),
       (1, 1, 1, 1, 10, 1, 2),
       (1, 1, 1, 2, 10, 1, 2),
       (1, 1, 1, 3, 10, 1, 2),
       (1, 1, 2, 1, 10, 2, 3),
       (1, 1, 2, 2, 10, 2, 3),
       (1, 1, 2, 3, 10, 2, 3),
       (1, 1, 2, 1, 10, 2, 4),
       (1, 1, 2, 2, 10, 2, 4),
       (1, 1, 2, 3, 10, 2, 4),
       (1, 1, 3, 1, 10, 3, 5),
       (1, 1, 3, 2, 10, 3, 5),
       (1, 1, 3, 3, 10, 3, 5),
       (1, 1, 3, 1, 10, 3, 6),
       (1, 1, 3, 2, 10, 3, 6),
       (1, 1, 3, 3, 10, 3, 6),
       (1, 2, 1, 1, 10, 3, 1),
       (1, 2, 1, 2, 10, 3, 1),
       (1, 2, 1, 3, 10, 3, 1),
       (1, 2, 1, 1, 10, 3, 2),
       (1, 2, 1, 2, 10, 3, 2),
       (1, 2, 1, 3, 10, 3, 2),
       (1, 2, 2, 1, 10, 1, 3),
       (1, 2, 2, 2, 10, 1, 3),
       (1, 2, 2, 3, 10, 1, 3),
       (1, 2, 2, 1, 10, 1, 4),
       (1, 2, 2, 2, 10, 1, 4),
       (1, 2, 2, 3, 10, 1, 4),
       (1, 2, 3, 1, 10, 2, 5),
       (1, 2, 3, 2, 10, 2, 5),
       (1, 2, 3, 3, 10, 2, 5),
       (1, 2, 3, 1, 10, 2, 6),
       (1, 2, 3, 2, 10, 2, 6),
       (1, 2, 3, 3, 10, 2, 6),
       (1, 3, 1, 1, 10, 2, 1),
       (1, 3, 1, 2, 10, 2, 1),
       (1, 3, 1, 3, 10, 2, 1),
       (1, 3, 1, 1, 10, 2, 2),
       (1, 3, 1, 2, 10, 2, 2),
       (1, 3, 1, 3, 10, 2, 2),
       (1, 3, 2, 1, 10, 3, 3),
       (1, 3, 2, 2, 10, 3, 3),
       (1, 3, 2, 3, 10, 3, 3),
       (1, 3, 2, 1, 10, 3, 4),
       (1, 3, 2, 2, 10, 3, 4),
       (1, 3, 2, 3, 10, 3, 4),
       (1, 3, 3, 1, 10, 1, 5),
       (1, 3, 3, 2, 10, 1, 5),
       (1, 3, 3, 3, 10, 1, 5),
       (1, 3, 3, 1, 10, 1, 6),
       (1, 3, 3, 2, 10, 1, 6),
       (1, 3, 3, 3, 10, 1, 6);
# 6. 二维码扫码 存数据表
INSERT INTO code_state(two_dimensional_code, state) VALUE ('x1', 0, 1);
INSERT INTO code_state(two_dimensional_code, state) VALUE ('x2', 0, 3);
INSERT INTO code_state(two_dimensional_code, state) VALUE ('x3', 0, 4);


#
TRUNCATE TABLE code_state;
TRUNCATE TABLE pause_record;

### 重置menu
TRUNCATE TABLE menu;

#### 初始化目录
INSERT INTO menu (id, parent_id, name, path, redirect, component, icon, title)
VALUES (1, 0, 'index', '/', '/home', 'BasicLayout', ' ', '首页'),
       (2, 1, 'home', '/home', '', 'home', 'home', '首页'),
       (3, 1, 'system', '/system', '/system/student', 'RouteView', 'robot', '基础信息管理'),
       (4, 1, 'competition', '/competition', '', 'RouteView', 'solution', '签到管理'),
       (5, 1, 'drawManage', '/drawManage', '', 'RouteView', 'table', '抽签管理'),
       (6, 1, 'chairUmpire', '/chairUmpire', '', 'RouteView', 'audit', '主裁管理页面'),

       (7, 3, 'student', '/system/student', '', 'system/student', '', '考生管理'),
       (8, 3, 'judge', '/system/judge', '', 'system/judge', '', '裁判管理'),
       (9, 3, 'test', '/system/test', '', 'system/test', '', '试题管理'),
       (10, 3, 'company', '/system/company', '', 'system/company', '', '参赛单位管理'),
       (11, 3, 'syncComputerTestResult', '/chairUmpire/syncComputerTestResult', '','chairUmpire/syncComputerTestResult', '', '上传机考成绩'),


       (12, 4, 'studentSign', '/competition/studentSign', '', 'competition/studentSign', '', '考生签到（报到）'),
       (13, 4, 'judgeSign', '/competition/judgeSign', '', 'competition/judgeSign', '', '裁判签到（报到）'),
       (14, 4, 'studentSignOne', '/competition/studentSignOne', '', 'competition/studentSignOne', '', '候考签到'),
       (15, 4, 'studentSignTwo', '/competition/studentSignTwo', '', 'competition/studentSignTwo', '', '备考签到'),
       (16, 4, 'studentSignAway', '/competition/studentSignAway', '', 'competition/studentSignAway', '', '离场签到'),
       (17, 4, 'qrSign', '/competition/qrSign', '', 'competition/qrSign', '', '二维码签到'),

       (18, 5, 'drawStateManage', '/drawManage/drawStateManage', '', 'drawManage/drawStateManage', '', '抽签状态管理'),
       (19, 5, 'drawCompany', '/drawManage/drawCompany', '', 'drawManage/drawCompany', '', '代表队抽签'),
       (20, 5, 'chairDraw', '/drawManage/chairDraw', '', 'drawManage/chairDraw', '', '赛位抽签'),
       (21, 5, 'judgeDraw', '/drawManage/judgeDraw', '', 'drawManage/judgeDraw', '', '裁判抽签'),
       (22, 5, 'judgeDrawResult', '/drawManage/judgeDrawResult', '', 'drawManage/judgeDrawResult', '', '裁判抽签结果'),

       (23, 6, 'roundManage', '/chairUmpire/roundManage', '', 'chairUmpire/roundManage', '', '比赛管理'),
       (24, 6, 'checkResult', '/chairUmpire/checkResult', '', 'chairUmpire/checkResult', '', '成绩核验'),
       (25, 6, 'collectScore', '/chairUmpire/collectScore', '', 'chairUmpire/collectScore', '', '打分汇总');










