
SELECT judge.id,judge.name,judge.code,judge_draw_result.seat_id,phone,judge_type
FROM pad, judge_draw_result,judge
WHERE pad.ip = '192.168.97.7'  and judge_draw_result.pad_id = pad.id and judge_id = judge.id;


# 1. 根据 赛位 seatId 获取 裁判信息

SELECT judge.code,judge.name,judge_draw_result.state FROM judge,judge_draw_result WHERE judge.id = judge_draw_result.judge_id and judge_draw_result.seat_id =1;

# 2. 查询结果d
SELECT student_id,judge_id,sum(cent) as result ,student.name,judge.name FROM test_result,student,judge where judge.id = test_result.judge_id and student.id = test_result.student_id GROUP BY judge_id;

# 3. 根据场次、轮次、座位号 获取考生详细信息
SELECT student.name,student.code,student.sign_state
FROM seat_draw,student
WHERE seat_draw.game_number = 1 and seat_draw. game_round = 2 and seat_id =12 and student.id = seat_draw.student_id;

# 试题抽签结果
SELECT game_number,game_type,test_question.name
FROM test_question,question_draw
WHERE test_question.id = question_draw.question_id and game_number = 3;


##
truncate table question_draw;
truncate table test_result;

# 查找考生考试状态
SELECT student.name,seat_draw.id,seat_draw.state
FROM seat_draw,student
WHERE seat_draw.game_number =1 AND seat_draw.game_round =1 AND seat_draw.student_id = student.id;

# 查找裁判就绪状态
SELECT judge.name,judge_draw_result.id,judge_draw_result.state
FROM judge_draw_result,judge
WHERE judge_draw_result.seat_id = 1 and judge_draw_result.judge_id = judge.id;

SELECT id,two_dimensional_code,state,create_time,update_time,ip FROM code_state WHERE (two_dimensional_code = 'njdt01' AND ip = '172.18.1.239' AND (state = 0 OR state = 1));


SELECT student_id,student.company_name as company_name,student.code as student_code,student.name as student_name,sum(cent) as result ,judge_id,judge.name as judge_name
FROM test_result,student,judge
where test_result.game_number = 1 and test_result.game_round = 1  and judge.id = test_result.judge_id and student.id = test_result.student_id   GROUP BY judge_id  ;

###3 分页查询

SELECT  *
FROM company
ORDER BY draw_result
LIMIT 22 OFFSET 22;


### 4. 所有监考裁判列表
SELECT judge_draw_result.id,code,seat_id,group_id,type_name
FROM judge_draw_result,judge
WHERE judge_draw_result.judge_id = judge.id order by seat_id;


### 查询补录成绩评分标准

SELECT test_result.id,text,point,score,standard,step,min_score
FROM test_result,test_question_standard
WHERE test_result.question_standard_id = test_question_standard.id and game_number = 1 and game_round = 1 and judge_id =51;


INSERT INTO pause_record(game_number, game_round, type, seat_draw_id, student_id, pause_time, flag)
value (1,1,1,1,1,1,true);

SELECT  COUNT(*)
FROM student
WHERE test_day_state = 1;




