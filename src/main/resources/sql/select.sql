SELECT judge.id,judge.name,judge.code,judge_draw_result.seat_id,phone,judge_type
FROM pad, judge_draw_result,judge
WHERE pad.ip = "192.168.97.7"  and judge_draw_result.pad_id = pad.id and judge_id = judge.id;
