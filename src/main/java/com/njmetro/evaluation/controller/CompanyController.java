package com.njmetro.evaluation.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.njmetro.evaluation.domain.Company;
import com.njmetro.evaluation.domain.DrawState;
import com.njmetro.evaluation.domain.Judge;
import com.njmetro.evaluation.param.company.SaveCompanyParam;
import com.njmetro.evaluation.param.company.UpdateCompanyParam;
import com.njmetro.evaluation.service.CompanyService;
import com.njmetro.evaluation.service.DrawStateService;
import com.njmetro.evaluation.service.StudentService;
import com.njmetro.evaluation.util.DrawUtil;
import com.njmetro.evaluation.vo.CompanyVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.util.ArrayList;
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
        System.out.println("111");
        List<String> haveSighCompanyList = studentService.getHaveSighCompanyList();
        System.out.println();
        List<Company> companyList = DrawUtil.getCompanyDrawResult(haveSighCompanyList);
        for(Company item : companyList)
        {
            UpdateWrapper<Company> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("name",item.getName()).set("draw_result",item.getDrawResult());
            companyService.update(updateWrapper);
        }
        // 设置参赛队不能抽签，设置考生赛位可以抽签、裁判类型可以抽签
        DrawState drawState = drawStateService.getById(1);
        drawState.setState(false);
        drawStateService.updateById(drawState);
        DrawState drawState2 = drawStateService.getById(2);
        drawState2.setState(true);
        drawStateService.updateById(drawState2);
        DrawState drawState3 = drawStateService.getById(3);
        drawState3.setState(true);
        drawStateService.updateById(drawState3);
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

    /**
     * 获取抽签前参赛队列表
     * @return
     */
    @GetMapping("/getBeforeCompanyList")
    public List<CompanyVO> getBeforeCompanyList(){
        List<Company> companyList = companyService.list();
        Integer rowNumber = 0;
        if((companyList.size()) % 4 == 0){
            rowNumber = companyList.size() / 4;
        }else {
            rowNumber = companyList.size() / 4 +1;
        }
        List<CompanyVO> companyVOList = new ArrayList<>(rowNumber);
        for(int i = 0; i < rowNumber ; i++){
            CompanyVO companyVO = new CompanyVO(companyList.get(i));

            companyVO.setOrderTwo(companyList.get(i+rowNumber).getDrawResult());
            companyVO.setCompanyTwoName(companyList.get(i+rowNumber).getName());

            companyVO.setOrderThree(companyList.get(i+rowNumber*2).getDrawResult());
            companyVO.setCompanyThreeName(companyList.get(i+rowNumber *2).getName());
            if(i+rowNumber *3 < companyList.size()){
                companyVO.setOrderFour(companyList.get(i+rowNumber*3).getDrawResult());
                companyVO.setCompanyFourName(companyList.get(i+rowNumber *3).getName());
            }
            companyVOList.add(companyVO);

        }

        return companyVOList;
    }

    /**
     * 获取抽签后参赛队列表
     * @return
     */
    @GetMapping("/getAfterCompanyList")
    public List<CompanyVO> getAfterCompanyList(){
        QueryWrapper<Company> companyQueryWrapper = new QueryWrapper<>();
        companyQueryWrapper.orderByAsc("draw_result");
        List<Company> companyList = companyService.list(companyQueryWrapper);
        Integer rowNumber = 0;
        if((companyList.size()) % 4 == 0){
            rowNumber = companyList.size() / 4;
        }else {
            rowNumber = companyList.size() / 4 +1;
        }
        List<CompanyVO> companyVOList = new ArrayList<>(rowNumber);
        for(int i = 0; i < rowNumber ; i++){
            CompanyVO companyVO = new CompanyVO(companyList.get(i));

            companyVO.setOrderTwo(companyList.get(i+rowNumber).getDrawResult());
            companyVO.setCompanyTwoName(companyList.get(i+rowNumber).getName());

            companyVO.setOrderThree(companyList.get(i+rowNumber*2).getDrawResult());
            companyVO.setCompanyThreeName(companyList.get(i+rowNumber *2).getName());
            if(i+rowNumber *3 < companyList.size()){
                companyVO.setOrderFour(companyList.get(i+rowNumber*3).getDrawResult());
                companyVO.setCompanyFourName(companyList.get(i+rowNumber *3).getName());
            }
            companyVOList.add(companyVO);

        }

        return companyVOList;
    }

    /**
     * 上传参赛队数据
     */
    @PostMapping("/uploadCompany")
    public void uploadCompany(@RequestParam("file") MultipartFile uploadFile) {
        log.info("上传参赛队数据！");
        try {
            //存储并解析Excel
            File file = new File("C:/UploadFile/参赛队信息表.xlsx");
            uploadFile.transferTo(file);
            ImportParams importParams = new ImportParams();
            importParams.setHeadRows(1);
            List<Company> companyList = ExcelImportUtil.importExcel(file, Company.class, importParams);
            companyService.saveBatch(companyList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

