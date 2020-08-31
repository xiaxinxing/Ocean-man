package com.ocean.core.controller;

import cn.hutool.core.io.FileUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;

@RequestMapping("/video")
@RestController
public class VideoController {


    @RequestMapping("/get")
    public Object getVideo() {
        String resourcesPath = System.getProperty("user.dir") + "/core/src/main/resources/video";
        BufferedInputStream inputStream = FileUtil.getInputStream(resourcesPath + "/demo.avi");

        return "";
    }

}
