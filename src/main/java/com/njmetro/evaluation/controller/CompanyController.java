package com.njmetro.evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.njmetro.evaluation.domain.Company;
import com.njmetro.evaluation.domain.DrawState;
import com.njmetro.evaluation.param.company.SaveCompanyParam;
import com.njmetro.evaluation.param.company.UpdateCompanyParam;
import com.njmetro.evaluation.service.CompanyService;
import com.njmetro.evaluation.service.DrawStateService;
import com.njmetro.evaluation.service.StudentService;
import com.njmetro.evaluation.util.DrawUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
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
    private final StudentService studentService;
    private final DrawStateService drawStateService;



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
    /**
     * 获取已签到代表队
     */
    @GetMapping("/getHaveSighCompany")
    public List<String> getHaveSighCompany() {
        log.info(studentService.getHaveSighCompanyList().toString());
        return studentService.getHaveSighCompanyList();
    }

    /**
     * 代表队顺序抽签
     */
    @GetMapping("/drawCompany")
    public List<Company> drawCompany() {
        List<String> haveSighCompanyList = studentService.getHaveSighCompanyList();
        List<Company> companyList = DrawUtil.getCompanyDrawResult(haveSighCompanyList);
        for(Company item : companyList)
        {
            UpdateWrapper<Company> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("name",item.getName()).set("draw_result",item.getDrawResult());
            companyService.update(updateWrapper);

        }
        // 修改抽签状态
        DrawState drawState = drawStateService.getById(1);
        drawState.setState(false);
        drawStateService.updateById(drawState);
        return companyList;
    }

    /**
     * 查询抽签结果
     */
    @GetMapping("/getDrawResult")
    public List<Company> getDrawResult()
    {
        QueryWrapper<Company> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("draw_result",0)
                    .orderByAsc("draw_result");
       return companyService.list(queryWrapper);

    }
}

