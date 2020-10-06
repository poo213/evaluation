
SELECT judge.id,judge.name,judge.code,judge_draw_result.seat_id,phone,judge_type
FROM pad, judge_draw_result,judge
WHERE pad.ip = "192.168.97.7"  and judge_draw_result.pad_id = pad.id and judge_id = judge.id;


# 1. 根据 赛位 seatId 获取 裁判信息

SELECT judge.code,judge.name,judge_draw_result.state FROM judge,judge_draw_result WHERE judge.id = judge_draw_result.judge_id and judge_draw_result.seat_id =1;

# 2. 查询结果d
SELECT student_id,judge_id,sum(cent) as result ,student.name,judge.name FROM test_result,student,judge where judge.id = test_result.judge_id and student.id = test_result.student_id GROUP BY judge_id;

# 3. 根据场次、轮次、座位号 获取考生详细信息
SELECT student.name,student.code,student.sign_state
FROM seat_draw,student
WHERE seat_draw.game_number = 1 and seat_draw. game_round = 2 and seat_id =12 and student.id = seat_draw.student_id
