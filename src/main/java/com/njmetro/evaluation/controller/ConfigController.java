package com.njmetro.evaluation.controller;


import com.njmetro.evaluation.domain.Config;
import com.njmetro.evaluation.service.ConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 主裁配置场次和轮次
 * </p>
 *
 * @author zc
 * @since 2020-09-27
 */
@RestController
@RequestMapping("/config")
@RequiredArgsConstructor
@Slf4j
public class ConfigController {

    private final ConfigService configService;

    /**
     * 获取场次和轮次信息
     * @return
     */
    @GetMapping("/getInfo")
    public Config getInfo() {
        log.info(configService.getById(1).toString());
        return configService.getById(1);
    }

    /**
     * 配置场次和轮次信息
     * @return
     */
    @PostMapping("/setInfo")
    public Boolean setInfo(@RequestBody Config config) {
        log.info(config.toString());
        return configService.updateById(config);
    }
}

