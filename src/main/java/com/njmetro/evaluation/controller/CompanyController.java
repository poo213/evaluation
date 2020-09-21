package com.njmetro.evaluation.controller;


import com.njmetro.evaluation.domain.Company;
import com.njmetro.evaluation.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zc
 * @since 2020-09-21
 */
@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
@Slf4j
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("/getCompanyList")
    public List<Company> getCompanyList() {
        log.info("companyList:{}"+companyService.list());
        return  companyService.list();
    }
}

