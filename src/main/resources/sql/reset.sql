# 重置数据库（需要初始化）

###### 注意： 参赛队 company， 试题:test_question  试题评分标准：test_question_standard
# TRUNCATE TABLE company;
TRUNCATE TABLE student;
TRUNCATE TABLE judge;
TRUNCATE TABLE company;
TRUNCATE TABLE pad;
TRUNCATE TABLE config;
TRUNCATE TABLE draw_state;
TRUNCATE TABLE judge_draw_result;

#重置数据库(不需要重新初始化)
TRUNCATE TABLE seat_draw;
TRUNCATE TABLE test_result;
TRUNCATE TABLE question_draw;
TRUNCATE TABLE code_state; # 二维码状态
TRUNCATE TABLE pause_record;
TRUNCATE TABLE judge_submit_state;

##  初始数据

# 学生
INSERT INTO student(code, name, id_card, sex, age, phone, company_name, two_dimensional_code, leader)
values ('#######001', '南京地铁考生一', 320111111111111111, '男', 23, '13390797312', '南京地铁', 'njdt001', 1),
       ('#######002', '南京地铁考生二', 320111111111111111, '女', 23, '13390797312', '南京地铁', 'njdt002', 0),
       ('#######003', '南京地铁考生三', 320111111111111111, '男', 23, '13390797312', '南京地铁', 'njdt003', 0),

       ('#######004', '北京地铁考生一', 320111111111111111, '男', 23, '13390797312', '北京地铁', 'njdt004', 1),
       ('#######005', '北京地铁考生二', 320111111111111111, '女', 23, '13390797312', '北京地铁', 'njdt005', 0),
       ('#######006', '北京地铁考生三', 320111111111111111, '男', 23, '13390797312', '北京地铁', 'njdt006', 0),

       ('#######007', '天津地铁考生一', 320111111111111111, '男', 23, '13390797312', '天津地铁', 'njdt007', 1),
       ('#######008', '天津地铁考生二', 320111111111111111, '女', 23, '13390797312', '天津地铁', 'njdt008', 0),
       ('#######009', '天津地铁考生三', 320111111111111111, '男', 23, '13390797312', '天津地铁', 'njdt009', 0),

       ('#######010', '香港地铁考生一', 320111111111111111, '男', 23, '13390797312', '香港地铁', 'njdt010', 1),
       ('#######011', '香港地铁考生二', 320111111111111111, '女', 23, '13390797312', '香港地铁', 'njdt011', 0),
       ('#######012', '香港地铁考生三', 320111111111111111, '男', 23, '13390797312', '香港地铁', 'njdt012', 0),

       ('#######013', '上海地铁考生一', 320111111111111111, '男', 23, '13390797312', '上海地铁', 'njdt013', 1),
       ('#######014', '上海地铁考生二', 320111111111111111, '女', 23, '13390797312', '上海地铁', 'njdt014', 0),
       ('#######015', '上海地铁考生三', 320111111111111111, '男', 23, '13390797312', '上海地铁', 'njdt015', 0),

       ('#######016', '广州地铁考生一', 320111111111111111, '男', 23, '13390797312', '广州地铁', 'njdt016', 1),
       ('#######017', '广州地铁考生二', 320111111111111111, '女', 23, '13390797312', '广州地铁', 'njdt017', 0),
       ('#######018', '广州地铁考生三', 320111111111111111, '男', 23, '13390797312', '广州地铁', 'njdt018', 0),

       ('#######019', '长春地铁考生一', 320111111111111111, '男', 23, '13390797312', '长春地铁', 'njdt019', 1),
       ('#######020', '长春地铁考生二', 320111111111111111, '女', 23, '13390797312', '长春地铁', 'njdt020', 0),
       ('#######021', '长春地铁考生三', 320111111111111111, '男', 23, '13390797312', '长春地铁', 'njdt021', 0),

       ('#######022', '大连地铁考生一', 320111111111111111, '男', 23, '13390797312', '大连地铁', 'njdt022', 1),
       ('#######023', '大连地铁考生二', 320111111111111111, '女', 23, '13390797312', '大连地铁', 'njdt023', 0),
       ('#######024', '大连地铁考生三', 320111111111111111, '男', 23, '13390797312', '大连地铁', 'njdt024', 0),

       ('#######025', '武汉地铁考生一', 320111111111111111, '男', 23, '13390797312', '武汉地铁', 'njdt025', 1),
       ('#######026', '武汉地铁考生二', 320111111111111111, '女', 23, '13390797312', '武汉地铁', 'njdt026', 0),
       ('#######027', '武汉地铁考生三', 320111111111111111, '男', 23, '13390797312', '武汉地铁', 'njdt027', 0),

       ('#######028', '深圳地铁考生一', 320111111111111111, '男', 23, '13390797312', '深圳地铁', 'njdt028', 1),
       ('#######029', '深圳地铁考生二', 320111111111111111, '女', 23, '13390797312', '深圳地铁', 'njdt029', 0),
       ('#######030', '深圳地铁考生三', 320111111111111111, '男', 23, '13390797312', '深圳地铁', 'njdt030', 0),

       ('#######031', '重庆地铁考生一', 320111111111111111, '男', 23, '13390797312', '重庆地铁', 'njdt031', 1),
       ('#######032', '重庆地铁考生二', 320111111111111111, '女', 23, '13390797312', '重庆地铁', 'njdt032', 0),
       ('#######033', '重庆地铁考生三', 320111111111111111, '男', 23, '13390797312', '重庆地铁', 'njdt033', 0),

       ('#######034', '沈阳地铁考生一', 320111111111111111, '男', 23, '13390797312', '沈阳地铁', 'njdt034', 1),
       ('#######035', '沈阳地铁考生二', 320111111111111111, '女', 23, '13390797312', '沈阳地铁', 'njdt035', 0),
       ('#######036', '沈阳地铁考生三', 320111111111111111, '男', 23, '13390797312', '沈阳地铁', 'njdt036', 0),

       ('#######037', '成都地铁考生一', 320111111111111111, '男', 23, '13390797312', '成都地铁', 'njdt037', 1),
       ('#######038', '成都地铁考生二', 320111111111111111, '女', 23, '13390797312', '成都地铁', 'njdt038', 0),
       ('#######039', '成都地铁考生三', 320111111111111111, '男', 23, '13390797312', '成都地铁', 'njdt039', 0),

       ('#######040', '佛山地铁考生一', 320111111111111111, '男', 23, '13390797312', '佛山地铁', 'njdt040', 1),
       ('#######041', '佛山地铁考生二', 320111111111111111, '女', 23, '13390797312', '佛山地铁', 'njdt041', 0),
       ('#######042', '佛山地铁考生三', 320111111111111111, '男', 23, '13390797312', '佛山地铁', 'njdt042', 0),

       ('#######043', '西安地铁考生一', 320111111111111111, '男', 23, '13390797312', '西安地铁', 'njdt043', 1),
       ('#######044', '西安地铁考生二', 320111111111111111, '女', 23, '13390797312', '西安地铁', 'njdt044', 0),
       ('#######045', '西安地铁考生三', 320111111111111111, '男', 23, '13390797312', '西安地铁', 'njdt045', 0),

       ('#######046', '苏州地铁考生一', 320111111111111111, '男', 23, '13390797312', '苏州地铁', 'njdt046', 1),
       ('#######047', '苏州地铁考生二', 320111111111111111, '女', 23, '13390797312', '苏州地铁', 'njdt047', 0),
       ('#######048', '苏州地铁考生三', 320111111111111111, '男', 23, '13390797312', '苏州地铁', 'njdt048', 0),

       ('#######049', '昆明地铁考生一', 320111111111111111, '男', 23, '13390797312', '昆明地铁', 'njdt049', 1),
       ('#######050', '昆明地铁考生二', 320111111111111111, '女', 23, '13390797312', '昆明地铁', 'njdt050', 0),
       ('#######051', '昆明地铁考生三', 320111111111111111, '男', 23, '13390797312', '昆明地铁', 'njdt051', 0),

       ('#######052', '杭州地铁考生一', 320111111111111111, '男', 23, '13390797312', '杭州地铁', 'njdt052', 1),
       ('#######053', '杭州地铁考生二', 320111111111111111, '女', 23, '13390797312', '杭州地铁', 'njdt053', 0),
       ('#######054', '杭州地铁考生三', 320111111111111111, '男', 23, '13390797312', '杭州地铁', 'njdt0504', 0),

       ('#######055', '哈尔滨地铁考生一', 320111111111111111, '男', 23, '13390797312', '哈尔滨地铁', 'njdt055', 1),
       ('#######056', '哈尔滨地铁考生二', 320111111111111111, '女', 23, '13390797312', '哈尔滨地铁', 'njdt056', 0),
       ('#######057', '哈尔滨地铁考生三', 320111111111111111, '男', 23, '13390797312', '哈尔滨地铁', 'njdt057', 0),

       ('#######058', '郑州地铁考生一', 320111111111111111, '男', 23, '13390797312', '郑州地铁', 'njdt058', 1),
       ('#######059', '郑州地铁考生二', 320111111111111111, '女', 23, '13390797312', '郑州地铁', 'njdt059', 0),
       ('#######060', '郑州地铁考生三', 320111111111111111, '男', 23, '13390797312', '郑州地铁', 'njdt060', 0),

       ('#######061', '长沙地铁考生一', 320111111111111111, '男', 23, '13390797312', '长沙地铁', 'njdt061', 1),
       ('#######062', '长沙地铁考生二', 320111111111111111, '女', 23, '13390797312', '长沙地铁', 'njdt062', 0),
       ('#######063', '长沙地铁考生三', 320111111111111111, '男', 23, '13390797312', '长沙地铁', 'njdt063', 0),

       ('#######064', '宁波地铁考生一', 320111111111111111, '男', 23, '13390797312', '宁波地铁', 'njdt064', 1),
       ('#######065', '宁波地铁考生二', 320111111111111111, '女', 23, '13390797312', '宁波地铁', 'njdt065', 0),
       ('#######066', '宁波地铁考生三', 320111111111111111, '男', 23, '13390797312', '宁波地铁', 'njdt066', 0),

       ('#######067', '无锡地铁考生一', 320111111111111111, '男', 23, '13390797312', '无锡地铁', 'njdt067', 1),
       ('#######068', '无锡地铁考生二', 320111111111111111, '女', 23, '13390797312', '无锡地铁', 'njdt068', 0),
       ('#######069', '无锡地铁考生三', 320111111111111111, '男', 23, '13390797312', '无锡地铁', 'njdt069', 0),

       ('#######070', '青岛地铁考生一', 320111111111111111, '男', 23, '13390797312', '青岛地铁', 'njdt070', 1),
       ('#######071', '青岛地铁考生二', 320111111111111111, '女', 23, '13390797312', '青岛地铁', 'njdt071', 0),
       ('#######072', '青岛地铁考生三', 320111111111111111, '男', 23, '13390797312', '青岛地铁', 'njdt072', 0),

       ('#######073', '南昌地铁考生一', 320111111111111111, '男', 23, '13390797312', '南昌地铁', 'njdt073', 1),
       ('#######074', '南昌地铁考生二', 320111111111111111, '女', 23, '13390797312', '南昌地铁', 'njdt074', 0),
       ('#######075', '南昌地铁考生三', 320111111111111111, '男', 23, '13390797312', '南昌地铁', 'njdt075', 0),

       ('#######076', '福州地铁考生一', 320111111111111111, '男', 23, '13390797312', '福州地铁', 'njdt076', 1),
       ('#######077', '福州地铁考生二', 320111111111111111, '女', 23, '13390797312', '福州地铁', 'njdt077', 0),
       ('#######078', '福州地铁考生三', 320111111111111111, '男', 23, '13390797312', '福州地铁', 'njdt078', 0),

       ('#######079', '东莞地铁考生一', 320111111111111111, '男', 23, '13390797312', '东莞地铁', 'njdt079', 1),
       ('#######080', '东莞地铁考生二', 320111111111111111, '女', 23, '13390797312', '东莞地铁', 'njdt080', 0),
       ('#######081', '东莞地铁考生三', 320111111111111111, '男', 23, '13390797312', '东莞地铁', 'njdt081', 0),

       ('#######082', '南宁地铁考生一', 320111111111111111, '男', 23, '13390797312', '南宁地铁', 'njdt082', 1),
       ('#######083', '南宁地铁考生二', 320111111111111111, '女', 23, '13390797312', '南宁地铁', 'njdt083', 0),
       ('#######084', '南宁地铁考生三', 320111111111111111, '男', 23, '13390797312', '南宁地铁', 'njdt084', 0),

       ('#######085', '合肥地铁考生一', 320111111111111111, '男', 23, '13390797312', '合肥地铁', 'njdt085', 1),
       ('#######086', '合肥地铁考生二', 320111111111111111, '女', 23, '13390797312', '合肥地铁', 'njdt086', 0),
       ('#######087', '合肥地铁考生三', 320111111111111111, '男', 23, '13390797312', '合肥地铁', 'njdt087', 0),

       ('#######088', '石家庄地铁考生一', 320111111111111111, '男', 23, '13390797312', '石家庄地铁', 'njdt088', 1),
       ('#######089', '石家庄地铁考生二', 320111111111111111, '女', 23, '13390797312', '石家庄地铁', 'njdt089', 0),
       ('#######090', '石家庄地铁考生三', 320111111111111111, '男', 23, '13390797312', '石家庄地铁', 'njdt090', 0),

       ('#######091', '贵阳地铁考生一', 320111111111111111, '男', 23, '13390797312', '贵阳地铁', 'njdt091', 1),
       ('#######092', '贵阳地铁考生二', 320111111111111111, '女', 23, '13390797312', '贵阳地铁', 'njdt092', 0),
       ('#######093', '贵阳地铁考生三', 320111111111111111, '男', 23, '13390797312', '贵阳地铁', 'njdt093', 0),

       ('#######094', '厦门地铁考生一', 320111111111111111, '男', 23, '13390797312', '厦门地铁', 'njdt094', 1),
       ('#######095', '厦门地铁考生二', 320111111111111111, '女', 23, '13390797312', '厦门地铁', 'njdt095', 0),
       ('#######096', '厦门地铁考生三', 320111111111111111, '男', 23, '13390797312', '厦门地铁', 'njdt096', 0),

       ('#######097', '乌鲁木齐地铁考生一', 320111111111111111, '男', 23, '13390797312', '乌鲁木齐地铁', 'njdt097', 1),
       ('#######098', '乌鲁木齐地铁考生二', 320111111111111111, '女', 23, '13390797312', '乌鲁木齐地铁', 'njdt098', 0),
       ('#######099', '乌鲁木齐地铁考生三', 320111111111111111, '男', 23, '13390797312', '乌鲁木齐地铁', 'njdt099', 0),

       ('#######100', '温州地铁考生一', 320111111111111111, '男', 23, '13390797312', '温州地铁', 'njdt100', 1),
       ('#######101', '温州地铁考生二', 320111111111111111, '女', 23, '13390797312', '温州地铁', 'njdt101', 0),
       ('#######102', '温州地铁考生三', 320111111111111111, '男', 23, '13390797312', '温州地铁', 'njdt102', 0),


       ('#######103', '济南地铁考生一', 320111111111111111, '男', 23, '13390797312', '济南地铁', 'njdt103', 1),
       ('#######104', '济南地铁考生二', 320111111111111111, '女', 23, '13390797312', '济南地铁', 'njdt104', 0),
       ('#######105', '济南地铁考生三', 320111111111111111, '男', 23, '13390797312', '济南地铁', 'njdt105', 0),

       ('#######106', '兰州地铁考生一', 320111111111111111, '男', 23, '13390797312', '兰州地铁', 'njdt106', 1),
       ('#######107', '兰州地铁考生二', 320111111111111111, '女', 23, '13390797312', '兰州地铁', 'njdt107', 0),
       ('#######108', '兰州地铁考生三', 320111111111111111, '男', 23, '13390797312', '兰州地铁', 'njdt108', 0),

       ('#######109', '常州地铁考生一', 320111111111111111, '男', 23, '13390797312', '常州地铁', 'njdt109', 1),
       ('#######110', '常州地铁考生二', 320111111111111111, '女', 23, '13390797312', '常州地铁', 'njdt110', 0),
       ('#######111', '常州地铁考生三', 320111111111111111, '男', 23, '13390797312', '常州地铁', 'njdt111', 0),

       ('#######112', '徐州地铁考生一', 320111111111111111, '男', 23, '13390797312', '徐州地铁', 'njdt112', 1),
       ('#######113', '徐州地铁考生二', 320111111111111111, '女', 23, '13390797312', '徐州地铁', 'njdt113', 0),
       ('#######114', '徐州地铁考生三', 320111111111111111, '男', 23, '13390797312', '徐州地铁', 'njdt114', 0),

       ('#######115', '陕西地铁考生一', 320111111111111111, '男', 23, '13390797312', '陕西地铁', 'njdt115', 1),
       ('#######116', '陕西地铁考生二', 320111111111111111, '女', 23, '13390797312', '陕西地铁', 'njdt116', 0),
       ('#######117', '陕西地铁考生三', 320111111111111111, '男', 23, '13390797312', '陕西地铁', 'njdt117', 0),

       ('#######118', '澳门地铁考生一', 320111111111111111, '男', 23, '13390797312', '澳门地铁', 'njdt118', 1),
       ('#######119', '澳门地铁考生二', 320111111111111111, '女', 23, '13390797312', '澳门地铁', 'njdt119', 0),
       ('#######120', '澳门地铁考生三', 320111111111111111, '男', 23, '13390797312', '澳门地铁', 'njdt120', 0),

       ('#######121', '呼和浩特地铁考生一', 320111111111111111, '男', 23, '13390797312', '呼和浩特地铁', 'njdt121', 1),
       ('#######122', '呼和浩特地铁考生二', 320111111111111111, '女', 23, '13390797312', '呼和浩特地铁', 'njdt122', 0),
       ('#######123', '呼和浩特地铁考生三', 320111111111111111, '男', 23, '13390797312', '呼和浩特地铁', 'njdt123', 0);

# 裁判
INSERT INTO judge(code, name, id_card, sex, age, phone, company_name, company_id, two_dimensional_code, sign_state,
                  judge_type,
                  master)
values ('***01', '裁判01', '131128199702130000', '男', 18, '15033330965', '南京地铁', 1, '#############', 0, '未定', 0),
       ('***02', '裁判02', '131128199702130000', '男', 18, '15033330965', '北京地铁', 2, '#############', 0, '未定', 0),
       ('***03', '裁判03', '131128199702130000', '男', 18, '15033330965', '天津地铁', 3, '#############', 0, '未定', 0),
       ('***04', '裁判04', '131128199702130000', '男', 18, '15033330965', '香港地铁', 4, '#############', 0, '未定', 0),
       ('***05', '裁判05', '131128199702130000', '男', 18, '15033330965', '上海地铁', 5, '#############', 0, '未定', 0),
       ('***06', '裁判06', '131128199702130000', '男', 18, '15033330965', '广州地铁', 6, '#############', 0, '未定', 0),
       ('***07', '裁判07', '131128199702130000', '男', 18, '15033330965', '长春地铁', 7, '#############', 0, '未定', 0),
       ('***08', '裁判08', '131128199702130000', '男', 18, '15033330965', '大连地铁', 8, '#############', 0, '未定', 0),
       ('***09', '裁判09', '131128199702130000', '男', 18, '15033330965', '武汉地铁', 9, '#############', 0, '未定', 0),
       ('***10', '裁判10', '131128199702130000', '男', 18, '15033330965', '深圳地铁', 10, '#############', 0, '未定', 0),
       ('***11', '裁判11', '131128199702130000', '男', 18, '15033330965', '重庆地铁', 11, '#############', 0, '未定', 0),
       ('***12', '裁判12', '131128199702130000', '男', 18, '15033330965', '沈阳地铁', 12, '#############', 0, '未定', 0),
       ('***13', '裁判13', '131128199702130000', '男', 18, '15033330965', '成都地铁', 13, '#############', 0, '未定', 0),
       ('***14', '裁判14', '131128199702130000', '男', 18, '15033330965', '佛山地铁', 14, '#############', 0, '未定', 0),
       ('***15', '裁判15', '131128199702130000', '男', 18, '15033330965', '西安地铁', 15, '#############', 0, '未定', 0),
       ('***16', '裁判16', '131128199702130000', '男', 18, '15033330965', '苏州地铁', 16, '#############', 0, '未定', 0),
       ('***17', '裁判17', '131128199702130000', '男', 18, '15033330965', '昆明地铁', 17, '#############', 0, '未定', 0),
       ('***18', '裁判18', '131128199702130000', '男', 18, '15033330965', '杭州地铁', 18, '#############', 0, '未定', 0),
       ('***19', '裁判19', '131128199702130000', '男', 18, '15033330965', '哈尔滨地铁', 19, '#############', 0, '未定', 0),
       ('***20', '裁判20', '131128199702130000', '男', 18, '15033330965', '郑州地铁', 20, '#############', 0, '未定', 0),
       ('***21', '裁判21', '131128199702130000', '男', 18, '15033330965', '长沙地铁', 21, '#############', 0, '未定', 0),
       ('***22', '裁判22', '131128199702130000', '男', 18, '15033330965', '宁波地铁', 22, '#############', 0, '未定', 0),
       ('***23', '裁判23', '131128199702130000', '男', 18, '15033330965', '无锡地铁', 23, '#############', 0, '未定', 0),
       ('***24', '裁判24', '131128199702130000', '男', 18, '15033330965', '青岛地铁', 24, '#############', 0, '未定', 0),
       ('***25', '裁判25', '131128199702130000', '男', 18, '15033330965', '南昌地铁', 25, '#############', 0, '未定', 0),
       ('***26', '裁判26', '131128199702130000', '男', 18, '15033330965', '福州地铁', 26, '#############', 0, '未定', 0),
       ('***27', '裁判27', '131128199702130000', '男', 18, '15033330965', '东莞地铁', 27, '#############', 0, '未定', 0),
       ('***28', '裁判28', '131128199702130000', '男', 18, '15033330965', '南宁地铁', 28, '#############', 0, '未定', 0),
       ('***29', '裁判29', '131128199702130000', '男', 18, '15033330965', '合肥地铁', 29, '#############', 0, '未定', 0),
       ('***30', '裁判30', '131128199702130000', '男', 18, '15033330965', '石家庄地铁', 30, '#############', 0, '未定', 0),
       ('***31', '裁判31', '131128199702130000', '男', 18, '15033330965', '贵阳地铁', 31, '#############', 0, '未定', 0),
       ('***32', '裁判32', '131128199702130000', '男', 18, '15033330965', '厦门地铁', 32, '#############', 0, '未定', 0),
       ('***33', '裁判33', '131128199702130000', '男', 18, '15033330965', '乌鲁木齐地铁', 33, '#############', 0, '未定', 0),
       ('***34', '裁判34', '131128199702130000', '男', 18, '15033330965', '温州地铁', 34, '#############', 0, '未定', 0),
       ('***35', '裁判35', '131128199702130000', '男', 18, '15033330965', '济南地铁', 35, '#############', 0, '未定', 0),
       ('***36', '裁判36', '131128199702130000', '男', 18, '15033330965', '兰州地铁', 36, '#############', 0, '未定', 0),
       ('***37', '裁判37', '131128199702130000', '男', 18, '15033330965', '常州地铁', 37, '#############', 0, '未定', 0),
       ('***38', '裁判38', '131128199702130000', '男', 18, '15033330965', '徐州地铁', 38, '#############', 0, '未定', 0),
       ('***39', '裁判39', '131128199702130000', '男', 18, '15033330965', '陕西地铁', 39, '#############', 0, '未定', 0),
       ('***40', '裁判40', '131128199702130000', '男', 18, '15033330965', '澳门地铁', 40, '#############', 0, '未定', 0),
       ('***41', '裁判41', '131128199702130000', '男', 18, '15033330965', '呼和浩特地铁', 41, '#############', 0, '未定', 0),
       ('***42', '裁判42', '131128199702130000', '男', 18, '15033330965', '南京地铁', 1, '#############', 0, '未定', 0),
       ('***43', '裁判43', '131128199702130000', '男', 18, '15033330965', '北京地铁', 2, '#############', 0, '未定', 0),
       ('***44', '裁判44', '131128199702130000', '男', 18, '15033330965', '天津地铁', 3, '#############', 0, '未定', 0),
       ('***45', '裁判45', '131128199702130000', '男', 18, '15033330965', '香港地铁', 4, '#############', 0, '未定', 0),
       ('***46', '裁判46', '131128199702130000', '男', 18, '15033330965', '上海地铁', 5, '#############', 0, '未定', 0),
       ('***47', '裁判47', '131128199702130000', '男', 18, '15033330965', '广州地铁', 6, '#############', 0, '未定', 0),
       ('***48', '裁判48', '131128199702130000', '男', 18, '15033330965', '长春地铁', 7, '#############', 0, '未定', 0),
       ('***49', '裁判49', '131128199702130000', '男', 18, '15033330965', '大连地铁', 8, '#############', 0, '未定', 0),
       ('***50', '裁判50', '131128199702130000', '男', 18, '15033330965', '武汉地铁', 9, '#############', 0, '未定', 0),
       ('***51', '裁判51', '131128199702130000', '男', 18, '15033330965', '深圳地铁', 10, '#############', 0, '未定', 0);

# pad
INSERT INTO pad (code, ip, seat_id, type)
VALUES ('GS001', '172.18.1.228', 1, 1),
       ('GS002', '172.18.1.229', 2, 1),
       ('GS003', '172.18.1.230', 3, 1),

       ('GS015', '172.18.1.100', 4, 1),
       ('GS016', '172.18.1.101', 5, 1),
       ('GS017', '172.18.1.102', 6, 1),

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


       ('GS004', '172.18.1.231', 1, 2),
       ('GS005', '172.18.1.232', 2, 2),
       ('GS006', '172.18.1.233', 3, 2),
       ('GS007', '172.18.1.234', 4, 2),
       ('GS008', '172.18.1.235', 5, 2),
       ('GS009', '172.18.1.236', 6, 2),

       ('GS018', '172.18.1.103', 7, 2),
       ('GS019', '172.18.1.104', 8, 2),
       ('GS020', '172.18.1.105', 9, 2),
       ('GS021', '172.18.1.106', 10, 2),
       ('GS022', '172.18.1.107', 11, 2),
       ('GS023', '172.18.1.108', 12, 2),

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

       ('GS012', '172.18.1.239', 36, 3),
       ('GS013', '172.18.1.240', 36, 4),
       ('GS014', '172.18.1.241', 36, 5);

# config
INSERT INTO config(game_number, game_round, state) value (1, 0, 4);

# draw_state

INSERT INTO draw_state(draw_name, state)
values ('参赛队抽签', true),
       ('考生赛位抽签', false),
       ('裁判类型抽签', false),
       ('执行裁判抽签', false),
       ('裁判位置抽签', false);

# 裁判抽签结果
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

# 参赛队初始化
INSERT INTO company (id, code, name, introduction, leader_name, leader_phone, draw_result)
VALUES (1, '****01', '南京地铁', '南京地铁', '张三', '15032329999', 0),
       (2, '****02', '北京地铁', '北京地铁', '张三', '15032329999', 0),
       (3, '****03', '天津地铁', '天津地铁', '张三', '15032329999', 0),
       (4, '****04', '香港地铁', '香港地铁', '张三', '15032329999', 0),
       (5, '****05', '上海地铁', '上海地铁', '张三', '15032329999', 0),
       (6, '****06', '广州地铁', '广州地铁', '张三', '15032329999', 0),
       (7, '****07', '长春地铁', '长春地铁', '张三', '15032329999', 0),
       (8, '****08', '大连地铁', '大连地铁', '张三', '15032329999', 0),
       (9, '****09', '武汉地铁', '武汉地铁', '张三', '15032329999', 0),
       (10, '****10', '深圳地铁', '深圳地铁', '张三', '15032329999', 0),
       (11, '****11', '重庆地铁', '重庆地铁', '张三', '15032329999', 0),
       (12, '****12', '沈阳地铁', '沈阳地铁', '张三', '15032329999', 0),
       (13, '****13', '成都地铁', '成都地铁', '张三', '15032329999', 0),
       (14, '****14', '佛山地铁', '佛山地铁', '张三', '15032329999', 0),
       (15, '****15', '西安地铁', '西安地铁', '张三', '15032329999', 0),
       (16, '****16', '苏州地铁', '苏州地铁', '张三', '15032329999', 0),
       (17, '****17', '昆明地铁', '昆明地铁', '张三', '15032329999', 0),
       (18, '****18', '杭州地铁', '杭州地铁', '张三', '15032329999', 0),
       (19, '****19', '哈尔滨地铁', '哈尔滨地铁', '张三', '15032329999', 0),
       (20, '****20', '郑州地铁', '郑州地铁', '张三', '15032329999', 0),
       (21, '****21', '长沙地铁', '长沙地铁', '张三', '15032329999', 0),
       (22, '****22', '宁波地铁', '宁波地铁', '张三', '15032329999', 0),
       (23, '****23', '无锡地铁', '无锡地铁', '张三', '15032329999', 0),
       (24, '****24', '青岛地铁', '青岛地铁', '张三', '15032329999', 0),
       (25, '****25', '南昌地铁', '南昌地铁', '张三', '15032329999', 0),
       (26, '****26', '福州地铁', '福州地铁', '张三', '15032329999', 0),
       (27, '****27', '东莞地铁', '东莞地铁', '张三', '15032329999', 0),
       (28, '****28', '南宁地铁', '南宁地铁', '张三', '15032329999', 0),
       (29, '****29', '合肥地铁', '合肥地铁', '张三', '15032329999', 0),
       (30, '****30', '石家庄地铁', '石家庄地铁', '张三', '15032329999', 0),
       (31, '****31', '贵阳地铁', '贵阳地铁', '张三', '15032329999', 0),
       (32, '****32', '厦门地铁', '厦门地铁', '张三', '15032329999', 0),
       (33, '****33', '乌鲁木齐地铁', '乌鲁木齐地铁', '张三', '15032329999', 0),
       (34, '****34', '温州地铁', '温州地铁', '张三', '15032329999', 0),
       (35, '****35', '济南地铁', '济南地铁', '张三', '15032329999', 0),
       (36, '****36', '兰州地铁', '兰州地铁', '张三', '15032329999', 0),
       (37, '****37', '常州地铁', '常州地铁', '张三', '15032329999', 0),
       (38, '****38', '徐州地铁', '徐州地铁', '张三', '15032329999', 0),
       (39, '****39', '陕西地铁', '陕西地铁', '张三', '15032329999', 0),
       (40, '****40', '澳门地铁', '澳门地铁', '张三', '15032329999', 0),
       (41, '****41', '呼和浩特地铁', '呼和浩特地铁', '张三', '15032329999', 0);
#
# INSERT INTO test_result(game_number, game_round, question_id, question_standard_id, cent, student_id, judge_id)
# values (1,1,1,1,10,1,1),
#        (1,1,1,2,10,1,1),
#        (1,1,1,3,10,1,1),
#        (1,1,1,1,10,1,2),
#        (1,1,1,2,10,1,2),
#        (1,1,1,3,10,1,2),
#        (1,1,2,1,10,2,3),
#        (1,1,2,2,10,2,3),
#        (1,1,2,3,10,2,3),
#        (1,1,2,1,10,2,4),
#        (1,1,2,2,10,2,4),
#        (1,1,2,3,10,2,4),
#        (1,1,3,1,10,2,5),
#        (1,1,3,2,10,2,5),
#        (1,1,3,3,10,2,5),
#        (1,1,3,1,10,2,6),
#        (1,1,3,2,10,2,6),
#        (1,1,3,3,10,2,6),
#        (1,2,1,1,10,3,1),
#        (1,2,1,2,10,3,1),
#        (1,2,1,3,10,3,1),
#        (1,2,1,1,10,3,2),
#        (1,2,1,2,10,3,2),
#        (1,2,1,3,10,3,2),
#        (1,2,2,1,10,1,3),
#        (1,2,2,2,10,1,3),
#        (1,2,2,3,10,1,3),
#        (1,2,2,1,10,1,4),
#        (1,2,2,2,10,1,4),
#        (1,2,2,3,10,1,4),
#        (1,2,3,1,10,2,5),
#        (1,2,3,2,10,2,5),
#        (1,2,3,3,10,2,5),
#        (1,2,3,1,10,2,6),
#        (1,2,3,2,10,2,6),
#        (1,2,3,3,10,2,6),
#        (1,3,1,1,10,2,1),
#        (1,3,1,2,10,2,1),
#        (1,3,1,3,10,2,1),
#        (1,3,1,1,10,2,2),
#        (1,3,1,2,10,2,2),
#        (1,3,1,3,10,2,2),
#        (1,3,2,1,10,3,3),
#        (1,3,2,2,10,3,3),
#        (1,3,2,3,10,3,3),
#        (1,3,2,1,10,3,4),
#        (1,3,2,2,10,3,4),
#        (1,3,2,3,10,3,4),
#        (1,3,3,1,10,1,5),
#        (1,3,3,2,10,1,5),
#        (1,3,3,3,10,1,5),
#        (1,3,3,1,10,1,6),
#        (1,3,3,2,10,1,6),
#        (1,3,3,3,10,1,6),
#
#        (2,1,1,1,10,11,1),
#        (2,1,1,2,10,11,1),
#        (2,1,1,3,10,11,1),
#        (2,1,1,1,10,11,2),
#        (2,1,1,2,10,11,2),
#        (2,1,1,3,10,11,2),
#        (2,1,2,1,10,12,3),
#        (2,1,2,2,10,12,3),
#        (2,1,2,3,10,12,3),
#        (2,1,2,1,10,12,4),
#        (2,1,2,2,10,12,4),
#        (2,1,2,3,10,12,4),
#        (2,1,3,1,10,12,5),
#        (2,1,3,2,10,12,5),
#        (2,1,3,3,10,12,5),
#        (2,1,3,1,10,12,6),
#        (2,1,3,2,10,12,6),
#        (2,1,3,3,10,12,6),
#        (2,2,1,1,10,13,1),
#        (2,2,1,2,10,13,1),
#        (2,2,1,3,10,13,1),
#        (2,2,1,1,10,13,2),
#        (2,2,1,2,10,13,2),
#        (2,2,1,3,10,13,2),
#        (2,2,2,1,10,11,3),
#        (2,2,2,2,10,11,3),
#        (2,2,2,3,10,11,3),
#        (2,2,2,1,10,11,4),
#        (2,2,2,2,10,11,4),
#        (2,2,2,3,10,11,4),
#        (2,2,3,1,10,12,5),
#        (2,2,3,2,10,12,5),
#        (2,2,3,3,10,12,5),
#        (2,2,3,1,10,12,6),
#        (2,2,3,2,10,12,6),
#        (2,2,3,3,10,12,6),
#        (2,3,1,1,10,12,1),
#        (2,3,1,2,10,12,1),
#        (2,3,1,3,10,12,1),
#        (2,3,1,1,10,12,2),
#        (2,3,1,2,10,12,2),
#        (2,3,1,3,10,12,2),
#        (2,3,2,1,10,13,3),
#        (2,3,2,2,10,13,3),
#        (2,3,2,3,10,13,3),
#        (2,3,2,1,10,13,4),
#        (2,3,2,2,10,13,4),
#        (2,3,2,3,10,13,4),
#        (2,3,3,1,10,11,5),
#        (2,3,3,2,10,11,5),
#        (2,3,3,3,10,11,5),
#        (2,3,3,1,10,11,6),
#        (2,3,3,2,10,11,6),
#        (2,3,3,3,10,11,6);


