package com.njmetro.evaluation.vo.api;

import com.njmetro.evaluation.domain.Judge;
import com.njmetro.evaluation.dto.JudgeInfoDTO;
import jdk.jfr.DataAmount;
import lombok.Data;

import javax.servlet.http.PushBuilder;

/**
 * @author 牟欢
 * @Classname JudgeInformationVO
 * @Description TODO
 * @Date 2020-09-29 10:06
 */
@Data
public class JudgeInformationVO {
    public Integer id;
    public String name;
    public String code;
    public Integer seatId;
    public String phone;
    public String judgeType;
    public Integer studentSeatId;
    public Integer gameNumber;
    public Integer gameRound;

    public JudgeInformationVO(JudgeInfoDTO judgeInfoDTO,Integer gameNumber,Integer gameRound){
       this.id = judgeInfoDTO.getId();
        this.name= judgeInfoDTO.getName();
        this.code= judgeInfoDTO.getCode();
        this.seatId= judgeInfoDTO.getSeatId();
        this.phone= judgeInfoDTO.getPhone();
        this.judgeType= judgeInfoDTO.getJudgeType();
        this.studentSeatId= judgeInfoDTO.getSeatId()/6+1;
        this.gameNumber= gameNumber;
        this.gameRound= gameRound;
    }

}
