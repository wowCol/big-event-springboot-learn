package org.finalwork.controller;

import org.finalwork.pojo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        // 保证文件的名字唯一，从而防止文件覆盖
        String filename = UUID.randomUUID() +
                originalFilename.substring(originalFilename.lastIndexOf("."));
        // 把文件的内容存储到本地磁盘上
        try {
            file.transferTo(new File("D:\\OtherLearn\\Spring\\" +
                    "learn01\\big-event\\files\\" + filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.success("url访问地址...");
    }
}
