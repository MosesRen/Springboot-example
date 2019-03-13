package com.jehu.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Component
@WebFilter(urlPatterns = {"/*"})
public class SessionFilter implements Filter {
    Logger logger = LoggerFactory.getLogger(SessionFilter.class);
    String[] includeUrls = new String[]{"/login","/register","/logintest","/registertest","/"};
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("过滤器启动");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String url = request.getRequestURI();
        boolean needFilter = isNeedFilter(url);
        if (!needFilter){
            chain.doFilter(request,response);
        }else{
            if(session != null && session.getAttribute("username") != null){
                chain.doFilter(request,response);
                logger.info("不过滤");
            }else{
                response.sendRedirect(request.getContextPath()+"login");
            }
        }

    }
    public boolean isNeedFilter(String uri) {

        for (String includeUrl : includeUrls) {
            if(includeUrl.equals(uri)) {
                return false;
            }
        }

        return true;
    }
    @Override
    public void destroy() {

    }
}
