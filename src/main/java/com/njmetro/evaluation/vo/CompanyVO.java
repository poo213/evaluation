package com.njmetro.evaluation.vo;

import com.njmetro.evaluation.domain.Company;
import lombok.Data;

/**
 * @author 牟欢
 * @Classname CompanyVO
 * @Description TODO
 * @Date 2020-10-26 13:33
 */
@Data
public class CompanyVO {
    public Integer orderOne;
    public String companyOneName;
    public Integer orderTwo;
    public String companyTwoName;
    public Integer orderThree;
    public String companyThreeName;
    public Integer orderFour;
    public String companyFourName;
    public CompanyVO(){}

    public CompanyVO(Company companyOne){
        this.orderOne = companyOne.getDrawResult();
        this.companyOneName = companyOne.getName();
        this.orderTwo = null;
        this.companyTwoName = null;
        this.orderThree = null;
        this.companyThreeName = null;
        this.orderFour = null;
        this.companyFourName = null;
    }
}
