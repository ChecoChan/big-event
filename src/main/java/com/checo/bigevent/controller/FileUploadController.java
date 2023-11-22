package com.checo.bigevent.controller;

import com.checo.bigevent.common.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {
    @PostMapping("upload")
    public Result<String> upload(MultipartFile file) {
        // TODO: 上传文件
        return null;
    }
}
