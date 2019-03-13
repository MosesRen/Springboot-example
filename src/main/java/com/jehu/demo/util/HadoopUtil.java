package com.jehu.demo.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URI;

/**
 * hadoop工具类，主要注入相关参数，提供一个返回hadoop文件系统的方法。
 * */
@Component
public class HadoopUtil {


    /*通过value将proprietary文件中的值注入，
     *但是由于这个配置是通用的，所以需要设置为static的变量
     * 采用postconstruct的方式来构造
     */
    @Value("${hdfs.path}")
    private String path;

    @Value("${hdfs.username}")
    private String username;

    private static String hdfsPath;
    private static String hdfsName;

    @PostConstruct
    public void gethdfsPath(){
        hdfsPath = this.path;
    }

    @PostConstruct
    public void gethdfsName(){
        hdfsName = this.username;
    }
    /**
     * 获取hadoop配置信息
     * */
    private static Configuration getConfiguration(){
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", hdfsPath);
        return configuration;
    }

    public static FileSystem getFileSystem() throws Exception {
        FileSystem fs = FileSystem.get(new URI(hdfsPath), getConfiguration(), hdfsName);
        return fs;
    }

    public static int uploadFile(FileSystem fs) throws Exception{

        return 0;
    }
}
