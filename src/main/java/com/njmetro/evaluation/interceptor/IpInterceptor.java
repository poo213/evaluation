package com.njmetro.evaluation.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njmetro.evaluation.domain.Config;
import com.njmetro.evaluation.domain.Pad;
import com.njmetro.evaluation.exception.PadException;
import com.njmetro.evaluation.service.ConfigService;
import com.njmetro.evaluation.service.PadService;
import com.njmetro.evaluation.util.IpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 牟欢
 * @Classname IpInterceptor
 * @Description TODO
 * @Date 2020-10-14 9:30
 * <p>
 * IP 拦截器
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class IpInterceptor implements HandlerInterceptor {
    public final ConfigService configService;
    public final PadService padService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 根据ip获取pad信息
        String ip = IpUtil.getIpAddr(request);
        log.info("IpInterceptor.Class ip： {}", ip);
        //ip = "192.168.97.10" ;
        QueryWrapper<Pad> padQueryWrapper = new QueryWrapper<>();
        padQueryWrapper.eq("ip", ip);
        Pad pad = padService.getOne(padQueryWrapper);
        if (pad == null) {
            throw new PadException(ip + "没有绑定pad");
        } else {
            request.setAttribute("pad", pad);
            request.setAttribute("ip", ip);
        }

        // 获取比赛场次和轮次信息
        Config config = configService.getById(1);
        request.setAttribute("config", config);
        return true;
    }
}
