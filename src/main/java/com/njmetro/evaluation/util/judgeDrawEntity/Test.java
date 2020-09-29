/*
package com.njmetro.evaluation.util.judgeDrawEntity;



import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

*/
/**
 * @author mubaisama
 *//*

@Slf4j
public class Test {
    @Resource
    JudgeEntity judgeEntity;

    @Resource
    SeatGroupEntity seatGroupEntity;

    */
/**
     * 每个赛组最多有6个裁判
     *//*

    public static Integer Max_JUDGE_NUMBER = 6;








    public static void main(String[] args) {
        // 1. 模拟从数据库中读取 全部36个裁判 放入 List<JudgeEntity> 中
        List<JudgeEntity> judgeEntityList = new ArrayList<>();
        for(int i = 1; i<= 36 ; i++){
            JudgeEntity judgeEntity = new JudgeEntity();
            judgeEntity.setCompanyId(i);
            judgeEntity.setJudgeId(i);
            List<SeatGroupEntity> noConflictSeatGroupEntityList = new ArrayList<>();
            judgeEntity.setNoConflictSeatGroupEntityList(noConflictSeatGroupEntityList);
            judgeEntityList.add(judgeEntity);
        }
        // 2. 模拟抽签结束后的 赛组 列表 List<SeatGroupEntity>
        List<SeatGroupEntity> seatGroupEntityList = new ArrayList<>();
        Integer auto = 0;
        for(int i = 1; i<= 6 ; i++){

            SeatGroupEntity seatGroupEntity = new SeatGroupEntity();
            // 模拟抽签结果
            List<Integer> companyIdList = new ArrayList<>();
            for(int j = 1; j<=7;j++){
                auto ++;
                companyIdList.add(auto);
            }
            seatGroupEntity.setCompanyDrawnList(companyIdList);
            switch (i){
                case  1:
                    seatGroupEntity.setGroupName("A");
                    break;
                case  2:
                    seatGroupEntity.setGroupName("B");
                    break;
                case  3:
                    seatGroupEntity.setGroupName("C");
                    break;
                case  4:
                    seatGroupEntity.setGroupName("D");
                    break;
                case  5:
                    seatGroupEntity.setGroupName("E");
                    break;
                case  6:
                    seatGroupEntity.setGroupName("F");
                    break;

            }
            seatGroupEntity.setHaveJudgeNumber(0);
            seatGroupEntity.setLeftJudgeNumber(6);
            seatGroupEntity.setIsFull(false);
            List<SaveJudgeEntity> saveJudgeEntityList = new ArrayList<>();
            seatGroupEntity.setSaveJudgeEntityList(saveJudgeEntityList);
            seatGroupEntityList.add(seatGroupEntity);
        }

        log.info("----------------------------算法开始------------------------------------");
        // 算法开始
        for (JudgeEntity judgeEntity : judgeEntityList){
            Boolean success = false;
            int index = 0;
            log.info("裁判 {} 寻找赛位 ",judgeEntity.getJudgeId());
            // 1. 根据裁判所在参赛队 在seatGroupEntityList 中找到 可以进入的赛组
            for(SeatGroupEntity seatGroupEntity:seatGroupEntityList){

                index ++;
                // 判断 seatGroupEntity 中的 companyIdList 中是否含有裁判所在 companyId
                if(seatGroupEntity.getCompanyDrawnList().contains(judgeEntity.getCompanyId())){
                    log.info("{} 赛组中 : 冲突 !!!, 裁判 {} 不能放入 ",seatGroupEntity.getGroupName(),judgeEntity.getJudgeId());
                }else {
                    if(!seatGroupEntity.getIsFull()){
                        // 如果赛组内裁判未满 6 个，添加裁判
                        List saveJudgeEntityList = seatGroupEntity.getSaveJudgeEntityList();
                        SaveJudgeEntity saveJudgeEntity = new  SaveJudgeEntity(judgeEntity);
                        // 设置下标位置
                        saveJudgeEntity.setSeatGroupIndex(index-1);
                        saveJudgeEntityList.add(saveJudgeEntity);
                        seatGroupEntity.setSaveJudgeEntityList(saveJudgeEntityList);
                        log.info("{} 赛组中 : 裁判 {} 放入 ",seatGroupEntity.getGroupName(),judgeEntity.getJudgeId());
                        if(saveJudgeEntityList.size()<Max_JUDGE_NUMBER){
                            seatGroupEntity.setIsFull(false);
                        }else {
                            seatGroupEntity.setIsFull(true);
                        }
                        success = true;
                        break;
                    }else {
                        // 如果裁判不冲突，只是 赛组裁判已经满了，就赛组信息存放到指定位置
                        List<SeatGroupEntity>  noConflictSeatGroupEntityList = judgeEntity.getNoConflictSeatGroupEntityList();
                        noConflictSeatGroupEntityList.add(seatGroupEntity);
                        judgeEntity.setNoConflictSeatGroupEntityList(noConflictSeatGroupEntityList);
                        log.info("{} 赛组中 : 裁判已满6人,裁判 {} 不能放入 ",seatGroupEntity.getGroupName(),judgeEntity.getJudgeId());
                    }
                }
            }
            // 没有放置成功，开始调换顺序
            if(index == 6 && !success){
                log.info("裁判 {} 寻找赛位 失败 ！！需要调换位置。",judgeEntity.getJudgeId());
                if(judgeEntity.getNoConflictSeatGroupEntityList().size() == 0){
                    log.info("裁判 {} 和所有赛组 冲突，算法执行失败 ！！！！！！！！！！！！！！！！",judgeEntity.getJudgeId());
                    break;
                }else {
                    log.info("############################# 裁判调换位置开始##################################");
                    List<SeatGroupEntity> NoConflictSeatGroupEntityList = judgeEntity.getNoConflictSeatGroupEntityList();
                    // 还需要把 调整好的 set 进去

                    log.info("有 {} 个可以交换的赛组",NoConflictSeatGroupEntityList.size());
                    Boolean changeResult = false;
                    for (SeatGroupEntity NoConflictSeatGroupEntity : NoConflictSeatGroupEntityList){
                        log.info("不冲突的赛组 {} 中裁判进行尝试 调换 ----",NoConflictSeatGroupEntity.getGroupName());
                        // 赛组 中的裁判
                        List<SaveJudgeEntity> saveJudgeEntityList = NoConflictSeatGroupEntity.getSaveJudgeEntityList();

                        for(SaveJudgeEntity saveJudgeEntity : saveJudgeEntityList){
                            log.info("{} 裁判进行尝试 调换 ==",saveJudgeEntity.getJudgeId());
                            Integer changeIndex = 0;
                            Boolean changeSuccess = false;


                            // 在同样的六个赛组内 进行查找
                            for(SeatGroupEntity changSeatGroup : seatGroupEntityList){
                                changeIndex ++ ;
                                if(changSeatGroup.getCompanyDrawnList().contains(saveJudgeEntity.getCompanyId())){
                                    log.info("{} 赛组中：冲突 ！！！裁判 {} 不能放入",changSeatGroup.getGroupName(),saveJudgeEntity.getJudgeId());
                                }else {
                                    if(!changSeatGroup.getIsFull()){
                                        log.info("{} 赛组中：可以调换",changSeatGroup.getGroupName(),saveJudgeEntity.getJudgeId());
                                        log.info("进行调换的裁判 {} ,可以放入 {} 组中 ：",saveJudgeEntity.getJudgeId(),changSeatGroup.getGroupName());
                                        List<SaveJudgeEntity> saveJudgeEntityLists = changSeatGroup.getSaveJudgeEntityList();
                                        saveJudgeEntityLists.add(new SaveJudgeEntity(saveJudgeEntity.getCompanyId(),saveJudgeEntity.getJudgeId()));
                                        changSeatGroup.setSaveJudgeEntityList(saveJudgeEntityLists);
                                        log.info("1. 进行调换的裁判 {} 进入新位置",saveJudgeEntity.getJudgeId());

                                        SeatGroupEntity ChangeSeatGroupEntity = seatGroupEntityList.get(saveJudgeEntity.getSeatGroupIndex());

                                        List<SaveJudgeEntity> ChangeSaveJudgeEntityList = ChangeSeatGroupEntity.getSaveJudgeEntityList();
                                        ChangeSaveJudgeEntityList.remove(saveJudgeEntity);
                                        log.info("2. 进行调换的裁判 {} 在老位置删除",saveJudgeEntity.getJudgeId());

                                        ChangeSaveJudgeEntityList.add(new SaveJudgeEntity(judgeEntity));
                                        log.info("3. 无法插入裁判 {} 进入老位置",judgeEntity.getJudgeId());
                                        seatGroupEntityList.get(saveJudgeEntity.getSeatGroupIndex()).setSaveJudgeEntityList(ChangeSaveJudgeEntityList);
                                        log.info("调换完成，逐层退出循环");
                                        changeSuccess = true;
                                        break;
                                    }else {
                                        log.info("{} 赛组中：裁判已满无法添加",changSeatGroup.getGroupName());
                                    }
                                }
                            }
                            if(changeIndex == 6 && !changeSuccess){
                                log.info("赛组 {} 中 调换失败，开始一下组调换",NoConflictSeatGroupEntity.getGroupName());
                            }else {
                                log.info("赛组 {} 中 调换成功 ！！！！",NoConflictSeatGroupEntity.getGroupName());
                                changeResult = true;
                                break;
                            }
                        }
                        if(changeResult){
                            log.info("跳出最外层循环");
                            log.info("############################# 裁判调换位置结束##################################");
                            break;
                        }
                    }
                    if(!changeResult){
                        log.info("算法失败 ！！！ 请调整裁判构成");
                    }

                }
            }else {

            }
        }

        log.info("---------------------算法执行结束，开始显示最终结果-------------------------------");
        for(SeatGroupEntity seatGroupEntity : seatGroupEntityList){
            System.out.println();
            log.info("赛组 {}中 考生所在的七个单位ID 为 ：",seatGroupEntity.getGroupName());
            for(Integer index : seatGroupEntity.getCompanyDrawnList()){
                System.out.print(index + " ");
            }
            System.out.println();
            log.info("赛组 {}中 选中的裁判 ID 为 ：",seatGroupEntity.getGroupName());
            for(SaveJudgeEntity saveJudgeEntity : seatGroupEntity.getSaveJudgeEntityList()){
                System.out.print(saveJudgeEntity.getJudgeId() + " ");
            }
        }
        System.out.println("");

        for(SeatGroupEntity seatGroupEntity : seatGroupEntityList){
            for(int i= 0;i< seatGroupEntity.getSaveJudgeEntityList().size();i++){
                Random rand = new Random();
                int randomNum = rand.nextInt(10) ;
                Integer index = (randomNum+i)%6;
                System.out.print(seatGroupEntity.getSaveJudgeEntityList().get(index).getJudgeId() + " ");
            }
            System.out.println("");
        }






    }
}
*/
