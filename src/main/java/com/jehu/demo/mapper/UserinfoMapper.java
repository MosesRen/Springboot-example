package com.jehu.demo.mapper;

import org.apache.ibatis.annotations.*;

import com.jehu.demo.entity.*;


@Mapper
public interface UserinfoMapper {

    @Select("select * from userinfo where username=#{username}")
    public Userinfo findByUsername(String Username);

    @Select("select username,password from userinfo where id=#{id}")
    public Userinfo findById(int id);

    @Insert("insert into userinfo(username,password,email) values(#{username},#{password},#{email})")
    public int register(@Param("username") String username ,
                         @Param("password") String password ,
                         @Param("email") String email);

    @Update("update userinfo set username=#{username},password=#{password},email=#{email}")
    public void updateinfo(@Param("username") String username ,
                         @Param("password") String password ,
                         @Param("email") String email);
}
