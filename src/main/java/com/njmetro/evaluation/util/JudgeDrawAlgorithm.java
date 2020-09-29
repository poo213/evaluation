package com.njmetro.evaluation.util;

import com.njmetro.evaluation.domain.JudgeDrawResult;
import com.njmetro.evaluation.domain.SeatGroup;
import com.njmetro.evaluation.util.judgeDrawEntity.JudgeEntity;
import com.njmetro.evaluation.util.judgeDrawEntity.SaveJudgeEntity;
import com.njmetro.evaluation.util.judgeDrawEntity.SeatGroupEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 牟欢
 * @Classname JudgeDrawAlgorithm
 * @Description TODO
 * @Date 2020-09-28 10:53
 */
@Slf4j
@Component
public class JudgeDrawAlgorithm {

    /**
     * 裁判每次抽签 抽取的最大裁判数目
     */
    public static Integer Max_JUDGE_NUMBER = 2;

    /**
     * 执行裁判抽签算法
     *
     * @param seatGroupEntityList
     * @param judgeEntityList
     * @return
     */
    public static List<SeatGroupEntity> run (List<SeatGroupEntity> seatGroupEntityList, List<JudgeEntity> judgeEntityList){
        log.info("--------------开始执行算法-----------------");
        // 每一个裁判寻找赛位
        for (JudgeEntity judgeEntity : judgeEntityList) {
            // 每个裁判是否抽签成功条件  true: 成功  false : 失败
            Boolean success = false;
            int index = 0;
            log.info("裁判 {} (所在参赛队 {})寻找赛位 ", judgeEntity.getJudgeId(), judgeEntity.getCompanyId());
            // 在每个赛组中查找 能够抽签成功的赛位
            for (SeatGroupEntity seatGroupEntity : seatGroupEntityList) {
                // 一共6个赛位，标记查找到第几个赛位
                index++;
                // 判断 seatGroupEntity 中的 companyIdList 中是否和 裁判所在 companyId 冲突
                if (seatGroupEntity.getCompanyDrawnList().contains(judgeEntity.getCompanyId())) {
                    log.info("{} 赛组中 : 冲突 !!!, 裁判 {} 不能放入 ", seatGroupEntity.getGroupName(), judgeEntity.getJudgeId());
                } else {
                    if (!seatGroupEntity.getIsFull()) {
                        // 如果赛组内裁判未满 6 个，添加裁判
                        List saveJudgeEntityList = seatGroupEntity.getSaveJudgeEntityList();
                        SaveJudgeEntity saveJudgeEntity = new SaveJudgeEntity(judgeEntity);
                        // 设置下标位置
                        saveJudgeEntity.setSeatGroupIndex(index - 1);
                        saveJudgeEntityList.add(saveJudgeEntity);
                        seatGroupEntity.setSaveJudgeEntityList(saveJudgeEntityList);
                        log.info("{} 赛组中 : 裁判 {} 放入 ", seatGroupEntity.getGroupName(), judgeEntity.getJudgeId());
                        if (saveJudgeEntityList.size() < Max_JUDGE_NUMBER) {
                            seatGroupEntity.setIsFull(false);
                        } else {
                            seatGroupEntity.setIsFull(true);
                        }
                        success = true;
                        break;
                    } else {
                        // 如果裁判不冲突，只是 赛组裁判已经满了，就赛组信息存放到指定位置
                        List<SeatGroupEntity> noConflictSeatGroupEntityList = judgeEntity.getNoConflictSeatGroupEntityList();
                        noConflictSeatGroupEntityList.add(seatGroupEntity);
                        judgeEntity.setNoConflictSeatGroupEntityList(noConflictSeatGroupEntityList);
                        log.info("{} 赛组中 : 裁判已满6人,裁判 {} 不能放入 ", seatGroupEntity.getGroupName(), judgeEntity.getJudgeId());
                    }
                }
            }
            // 没有放置成功，开始调换顺序
            if (index == 6 && !success) {
                log.info("裁判 {} 寻找赛位 失败 ！！需要调换位置。", judgeEntity.getJudgeId());
                if (judgeEntity.getNoConflictSeatGroupEntityList().size() == 0) {
                    log.info("裁判 {} 和所有赛组 冲突，算法执行失败 ！！！！！！！！！！！！！！！！", judgeEntity.getJudgeId());
                    break;
                } else {
                    log.info("############################# 裁判调换位置开始##################################");
                    List<SeatGroupEntity> NoConflictSeatGroupEntityList = judgeEntity.getNoConflictSeatGroupEntityList();
                    // 还需要把 调整好的 set 进去

                    log.info("有 {} 个可以交换的赛组", NoConflictSeatGroupEntityList.size());
                    Boolean changeResult = false;
                    for (SeatGroupEntity NoConflictSeatGroupEntity : NoConflictSeatGroupEntityList) {
                        log.info("不冲突的赛组 {} 中裁判进行尝试 调换 ----", NoConflictSeatGroupEntity.getGroupName());
                        // 赛组 中的裁判
                        List<SaveJudgeEntity> saveJudgeEntityList = NoConflictSeatGroupEntity.getSaveJudgeEntityList();

                        for (SaveJudgeEntity saveJudgeEntity : saveJudgeEntityList) {
                            log.info("{} 裁判进行尝试 调换 ==", saveJudgeEntity.getJudgeId());
                            Integer changeIndex = 0;
                            Boolean changeSuccess = false;


                            // 在同样的六个赛组内 进行查找
                            for (SeatGroupEntity changSeatGroup : seatGroupEntityList) {
                                changeIndex++;
                                if (changSeatGroup.getCompanyDrawnList().contains(saveJudgeEntity.getCompanyId())) {
                                    log.info("{} 赛组中：冲突 ！！！裁判 {} 不能放入", changSeatGroup.getGroupName(), saveJudgeEntity.getJudgeId());
                                } else {
                                    if (!changSeatGroup.getIsFull()) {
                                        log.info("{} 赛组中：可以调换", changSeatGroup.getGroupName(), saveJudgeEntity.getJudgeId());
                                        log.info("进行调换的裁判 {} ,可以放入 {} 组中 ：", saveJudgeEntity.getJudgeId(), changSeatGroup.getGroupName());
                                        List<SaveJudgeEntity> saveJudgeEntityLists = changSeatGroup.getSaveJudgeEntityList();
                                        saveJudgeEntityLists.add(new SaveJudgeEntity(saveJudgeEntity.getCompanyId(), saveJudgeEntity.getJudgeId()));
                                        changSeatGroup.setSaveJudgeEntityList(saveJudgeEntityLists);
                                        log.info("1. 进行调换的裁判 {} 进入新位置", saveJudgeEntity.getJudgeId());

                                        SeatGroupEntity ChangeSeatGroupEntity = seatGroupEntityList.get(saveJudgeEntity.getSeatGroupIndex());

                                        List<SaveJudgeEntity> ChangeSaveJudgeEntityList = ChangeSeatGroupEntity.getSaveJudgeEntityList();
                                        ChangeSaveJudgeEntityList.remove(saveJudgeEntity);
                                        log.info("2. 进行调换的裁判 {} 在老位置删除", saveJudgeEntity.getJudgeId());

                                        ChangeSaveJudgeEntityList.add(new SaveJudgeEntity(judgeEntity));
                                        log.info("3. 无法插入裁判 {} 进入老位置", judgeEntity.getJudgeId());
                                        seatGroupEntityList.get(saveJudgeEntity.getSeatGroupIndex()).setSaveJudgeEntityList(ChangeSaveJudgeEntityList);
                                        log.info("调换完成，逐层退出循环");
                                        changeSuccess = true;
                                        break;
                                    } else {
                                        log.info("{} 赛组中：裁判已满无法添加", changSeatGroup.getGroupName());
                                    }
                                }
                            }
                            if (changeIndex == 6 && !changeSuccess) {
                                log.info("赛组 {} 中 调换失败，开始一下组调换", NoConflictSeatGroupEntity.getGroupName());
                            } else {
                                log.info("赛组 {} 中 调换成功 ！！！！", NoConflictSeatGroupEntity.getGroupName());
                                changeResult = true;
                                break;
                            }
                        }
                        if (changeResult) {
                            log.info("跳出最外层循环");
                            log.info("############################# 裁判调换位置结束##################################");
                            break;
                        }
                    }
                    if (!changeResult) {
                        log.info("算法失败 ！！！ 请调整裁判构成");
                    }

                }
            }
        }
       /* log.info("--------------算法执行结束--------------------");
        for (SeatGroupEntity seatGroupEntity : seatGroupEntityList) {
            log.info(seatGroupEntity.getCompanyDrawnList().toString());
        }
        for (JudgeEntity judgeEntity : judgeEntityList) {
            System.out.println(judgeEntity.getJudgeId() + " ");
        }
        log.info("结果￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥");
        for (SeatGroupEntity seatGroupEntity : seatGroupEntityList) {
            System.out.println();
            log.info("赛组 {}中 考生所在的七个单位ID 为 ：", seatGroupEntity.getGroupName());
            for (Integer index : seatGroupEntity.getCompanyDrawnList()) {
                System.out.print(index + " ");
            }
            System.out.println();
            log.info("赛组 {}中 选中的裁判 ID 为 ：", seatGroupEntity.getGroupName());
            for (SaveJudgeEntity saveJudgeEntity : seatGroupEntity.getSaveJudgeEntityList()) {
                // 写入到数据库
                JudgeDrawResult judgeDrawResult = new JudgeDrawResult();
                judgeDrawResult.setJudgeId(saveJudgeEntity.getJudgeId());
                judgeDrawResult.setSeatId(0);
                judgeDrawResult.setPadId(0);
                judgeDrawResultService.save(judgeDrawResult);

                System.out.print(saveJudgeEntity.getJudgeId() + " ");
            }
        }*/
        log.info("--------------结束执行算法-----------------");
        return seatGroupEntityList;
    }
}
