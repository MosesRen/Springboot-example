package com.jehu.demo.controller;


import com.jehu.demo.util.HadoopUtil;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Controller
@RequestMapping("/user")
public class hadoopcontroller {

    Logger logger = LoggerFactory.getLogger(hadoopcontroller.class);
    FileSystem fs = null;
    @RequestMapping("/hadoop")
    public ModelAndView getFileSystem()throws Exception{
        String path="/";
        fs = HadoopUtil.getFileSystem();
        FileStatus[] fileStatuses = fs.listStatus(new Path(path));
        for(FileStatus fileStatu:fileStatuses){
            logger.info(fileStatu.toString());
        }
        ModelAndView mv = new ModelAndView();
        mv.setViewName("hadoop");
        return mv;
    }

    @RequestMapping("/upload")
    public String upload(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception{
          MultipartFile file = ((MultipartRequest) httpServletRequest).getFile("file");
          logger.info(file.getOriginalFilename());
//        httpServletResponse.setContentType("text/html;charset=utf-8");
//        PrintWriter out = httpServletResponse.getWriter();
//        out.print( "<script type=\"text/javascript\">alert('注册成功,请您登录！！！')</script>" );
        return "redirect:/hadoop";
    }
}
