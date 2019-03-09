package com.jehu.demo.service;


import com.jehu.demo.entity.Userinfo;
import com.jehu.demo.mapper.UserinfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserinfoMapper userinfoMapper;


    public Userinfo findUserinfoandpassword(String username){
        return userinfoMapper.findByUsername(username);
    }

    public int registerNewUser(String username,String password,String email){
        return userinfoMapper.register(username,password,email);
    }

}
