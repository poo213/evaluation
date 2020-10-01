
SELECT judge.id,judge.name,judge.code,judge_draw_result.seat_id,phone,judge_type
FROM pad, judge_draw_result,judge
WHERE pad.ip = "192.168.97.7"  and judge_draw_result.pad_id = pad.id and judge_id = judge.id;


# 1. 根据 赛位 seatId 获取 裁判信息

SELECT judge.code,judge.name,judge_draw_result.state FROM judge,judge_draw_result WHERE judge.id = judge_draw_result.judge_id and judge_draw_result.seat_id =1;
