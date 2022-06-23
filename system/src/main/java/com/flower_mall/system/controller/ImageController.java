package com.flower_mall.system.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.MultiFileResource;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.flower_mall.system.common.Result;
import com.flower_mall.system.entity.User;
import com.flower_mall.system.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Value("${server.port}")
    private String port;

    /**
     * 上传接口
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) {
        String filename = file.getOriginalFilename();   //获取源文件名称
        String flag = IdUtil.fastSimpleUUID();   //定义文件的唯一标识符(前缀)
        String rootFilePath = System.getProperty("user.dir") + "/system/src/main/resources/static/image/" + flag + "_" + filename;   //获取文件路径
        try {
            FileUtil.writeBytes(file.getBytes(), rootFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String ip = "http://localhost";
        return Result.success(ip + ":" + port + "/image/" + flag);
    }

    /**
     * 下载接口
     *
     * @param flag
     * @param response
     */
    @GetMapping("/{flag}")
    public void getFiles(@PathVariable String flag, HttpServletResponse response) {
        OutputStream os;    //新建一个输出流
        String basePath = System.getProperty("user.dir") + "/system/src/main/resources/static/image/";    //文件上传根路径
        List<String> fileNames = FileUtil.listFileNames(basePath);   //获取所有的文件名称
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");  //找到跟参数一致的文件
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filenames" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + fileName); //通过文件的路径读取文件字节流
                os = response.getOutputStream();    //通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("文件下载失败");
        }
    }
}
