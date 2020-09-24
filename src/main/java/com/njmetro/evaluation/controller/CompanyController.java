package com.njmetro.evaluation.controller;


import com.njmetro.evaluation.domain.Company;
import com.njmetro.evaluation.param.company.SaveCompanyParam;
import com.njmetro.evaluation.param.company.UpdateCompanyParam;
import com.njmetro.evaluation.service.CompanyService;
import com.njmetro.evaluation.service.StudentService;
import com.njmetro.evaluation.util.DrawUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.njmetro.evaluation.util.KnuthUtil.result;

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
    private final StudentService studentService;

    @GetMapping("/getCompanyList")
    public List<Company> getCompanyList() {
        log.info("companyList:{}" + companyService.list());
        return companyService.list();
    }

    @PostMapping("/editCompany")
    public Boolean editCompany(@Valid @RequestBody UpdateCompanyParam updateCompanyParam) {
        log.info("updateCompanyParam" + updateCompanyParam);
        Company company = new Company();
        company.setId(updateCompanyParam.getId());
        company.setCode(updateCompanyParam.getCode());
        company.setName(updateCompanyParam.getName());
        company.setIntroduction(updateCompanyParam.getIntroduction());
        company.setLeaderName(updateCompanyParam.getLeaderName());
        company.setLeaderPhone(updateCompanyParam.getLeaderPhone());
        return companyService.updateById(company);

    }

    @PostMapping("/addCompany")
    public Boolean addCompany(@Valid @RequestBody SaveCompanyParam saveCompanyParam) {
        log.info("saveCompanyParam" + saveCompanyParam);
        Company company = new Company();
        company.setCode(saveCompanyParam.getCode());
        company.setName(saveCompanyParam.getName());
        company.setIntroduction(saveCompanyParam.getIntroduction());
        company.setLeaderName(saveCompanyParam.getLeaderName());
        company.setLeaderPhone(saveCompanyParam.getLeaderPhone());
        return companyService.save(company);
    }

    @DeleteMapping("/deleteCompany/{id}")
    public Boolean deleteCompany(@PathVariable Integer id) {
        log.info("id:{}", id);
        return true;
    }

    @GetMapping("/getHaveSighCompany")
    public List<String> getHaveSighCompany() {
        log.info(studentService.getHaveSighCompanyList().toString());
        return studentService.getHaveSighCompanyList();
    }

    @GetMapping("/drawCompany")
    public List<Company> drawCompany() {
        List<String> haveSighCompanyList = studentService.getHaveSighCompanyList();
        return DrawUtil.getCompanyDrawResult(haveSighCompanyList);
    }
}

