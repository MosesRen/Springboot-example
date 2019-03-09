package com.jehu.demo.controller;

import com.jehu.demo.entity.Userinfo;
import com.jehu.demo.service.UserService;
import com.sun.deploy.net.HttpResponse;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

@Controller
public class usercontroller {


    Logger logger = LoggerFactory.getLogger(usercontroller.class);
    @Autowired
    private UserService userService;
    @RequestMapping("/")
    public String index(){
        /**
         * 待完成：登录后的主页设计，session的验证问题
         * */
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/logintest")
    public String logintest(@RequestParam(value = "username")String username , @RequestParam(value = "password")String password, Map<String,Object> map){
        Userinfo userinfo = userService.findUserinfoandpassword(username);
        if(userinfo == null){
            map.put( "information","登陆失败,用户不存在" );
            return "login";
        }else{
            if(userinfo.getPassword().equals(password)){
                return "index";

            }else{
                map.put( "information","登陆失败,密码错误" );
                return "login";
            }
        }
    }

    @RequestMapping("/register")
    public String register(){
        return "register";
    }
    @RequestMapping("/registertest")
    public String register(Userinfo user, HttpServletResponse response, Map<String, Object> map) throws Exception{
        String username = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
//        logger.info(user.getUsername());
//        logger.info(user.getEmail());
//        logger.info(user.getPassword());
        Userinfo userinfo = userService.findUserinfoandpassword(username);
        if(userinfo != null){
            map.put("information","用户名已存在！");
            return "register";
        }
        if(password.length() <6){
            map.put("information","密码长度需要大于等于6位");
            return "register";
        }
        int status = userService.registerNewUser(username,password,email);
        logger.info("status="+status);
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        if(status == 1){
            out.print( "<script type=\"text/javascript\">alert('注册成功,请您登录！！！')</script>" );
            return "login";
        }else{
            out.print( "<script type=\"text/javascript\">alert('注册失败请重试')</script>" );
            return "register";
        }
    }
}
