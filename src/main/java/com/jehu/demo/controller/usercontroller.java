package com.jehu.demo.controller;

import com.jehu.demo.entity.Userinfo;
import com.jehu.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Map;

@Controller
public class usercontroller {


    Logger logger = LoggerFactory.getLogger(usercontroller.class);
    @Autowired
    private UserService userService;
    @RequestMapping("/")
    public String index(HttpServletRequest httpServletRequest){
        /**
         * 待完成：登录后的主页设计，session的验证问题
         * */
        //检查session 是否存在
        HttpSession session = httpServletRequest.getSession();
        String username = (String)session.getAttribute("username");
        if(username == null){
            return "redirect:/login";
        }else{
            return "redirect:/user";
        }

    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/logintest")
    public String logintest(HttpServletRequest httpServletRequest, Map<String,Object> map){

        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");
        Userinfo userinfo = userService.findUserinfoandpassword(username);
        if(userinfo == null){
            map.put( "information","登陆失败,用户不存在" );
            return "login";
        }else{
            if(userinfo.getPassword().equals(password)){
                HttpSession session = httpServletRequest.getSession();
                session.setAttribute("username",username);
                session.setMaxInactiveInterval(10000);
                return "redirect:/";

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

    @RequestMapping("/user")
    public ModelAndView userinfo(HttpServletRequest httpServletRequest){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("userinfo");
        HttpSession session = httpServletRequest.getSession();
        String username = (String)session.getAttribute("username");
        Userinfo user = userService.findUserinfoandpassword(username);
        String name = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        mv.addObject("Username",name);
        mv.addObject("Password",password);
        mv.addObject("Email",email);
        return mv;
    }
}
