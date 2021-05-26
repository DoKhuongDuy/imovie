package com.ptit.service.interceptor;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Log4j2
public class WatchInterceptor implements HandlerInterceptor {
    private static final String STOP_WATCHER = "stop_watch";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        request.setAttribute(STOP_WATCHER, stopWatch);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        StopWatch watch = (StopWatch) request.getAttribute(STOP_WATCHER);
        if (watch != null && watch.isRunning()) {
            watch.stop();
            log.info("URI " + request.getRequestURI() + ", query = " + StringUtils.defaultString(request.getQueryString()) + ", Time Request = " + watch.getLastTaskTimeMillis());
        }
    }
}
