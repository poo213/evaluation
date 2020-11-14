# 重置数据库（需要初始化）

###### 注意： 参赛队 company， 试题:test_question  试题评分标准：test_question_standard
#1.手动修改数据库，参赛队的顺序
#2.student表里的报道状态改为0，test_day_state改为0


TRUNCATE TABLE config;
TRUNCATE TABLE draw_state;
TRUNCATE TABLE judge_draw_result;

#重置数据库(不需要重新初始化)
TRUNCATE TABLE seat_draw;
TRUNCATE TABLE test_result;
TRUNCATE TABLE test_final_result;
TRUNCATE Table edit_result_log;
TRUNCATE TABLE question_draw;
TRUNCATE TABLE code_state; # 二维码状态
TRUNCATE TABLE pause_record;
TRUNCATE TABLE judge_submit_state;

##  初始数据

# config
INSERT INTO config(game_number, game_round, state) value (0, 3, 4);

# draw_state

INSERT INTO draw_state(draw_name, state)
values ('参赛队抽签', false),
       ('考生赛位抽签', true),
       ('裁判类型抽签', false),
       ('执行裁判抽签', true),
       ('裁判位置抽签', false);

# 裁判抽签结果
INSERT INTO judge_draw_result(seat_id, judge_id, pad_id, group_id, type_name, state)
values (1, 0, 19, 1, '光缆接续', 0),
       (2, 0, 20, 1, '光缆接续', 0),
       (3, 0, 21, 1, '视频搭建', 0),
       (4, 0, 22, 1, '视频搭建', 0),
       (5, 0, 23, 1, '交换机组网', 0),
       (6, 0, 24, 1, '交换机组网', 0),

       (7, 0, 25, 2, '光缆接续', 0),
       (8, 0, 26, 2, '光缆接续', 0),
       (9, 0, 27, 2, '视频搭建', 0),
       (10, 0, 28, 2, '视频搭建', 0),
       (11, 0, 29, 2, '交换机组网', 0),
       (12, 0, 30, 2, '交换机组网', 0),

       (13, 0, 31, 3, '光缆接续', 0),
       (14, 0, 32, 3, '光缆接续', 0),
       (15, 0, 33, 3, '视频搭建', 0),
       (16, 0, 34, 3, '视频搭建', 0),
       (17, 0, 35, 3, '交换机组网', 0),
       (18, 0, 36, 3, '交换机组网', 0),

       (19, 0, 37, 4, '光缆接续', 0),
       (20, 0, 38, 4, '光缆接续', 0),
       (21, 0, 39, 4, '视频搭建', 0),
       (22, 0, 40, 4, '视频搭建', 0),
       (23, 0, 41, 4, '交换机组网', 0),
       (24, 0, 42, 4, '交换机组网', 0),

       (25, 0, 43, 5, '光缆接续', 0),
       (26, 0, 44, 5, '光缆接续', 0),
       (27, 0, 45, 5, '视频搭建', 0),
       (28, 0, 46, 5, '视频搭建', 0),
       (29, 0, 47, 5, '交换机组网', 0),
       (30, 0, 48, 5, '交换机组网', 0),

       (31, 0, 49, 6, '光缆接续', 0),
       (32, 0, 50, 6, '光缆接续', 0),
       (33, 0, 51, 6, '视频搭建', 0),
       (34, 0, 52, 6, '视频搭建', 0),
       (35, 0, 53, 6, '交换机组网', 0),
       (36, 0, 54, 6, '交换机组网', 0);

#初始化student表中的两个状态
UPDATE student SET sign_state=0,test_day_state=0