package com.njmetro.evaluation.util;

import com.njmetro.evaluation.domain.Company;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static com.njmetro.evaluation.util.KnuthUtil.result;

/**
 * @program: evaluation
 * @description: 抽签用封装函数
 * @author: zc
 * @create: 2020-09-24 15:17
 **/
@Slf4j
public class DrawUtil {

    public static List<Company> getCompanyDrawResult(List<String> haveSighCompanyList)
    {
        int n = haveSighCompanyList.size();
        Integer[] baseArray = new Integer[n];
        for (int i = 0; i < baseArray.length; i++) {
            System.out.println(i);
            baseArray[i] = i;
        }
        result(baseArray);

        List<Company> companyList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Company company = new Company();
            company.setName(haveSighCompanyList.get(i));
            company.setDrawResult(baseArray[i] + 1);
            companyList.add(company);
        }
        return companyList;

    }
}
