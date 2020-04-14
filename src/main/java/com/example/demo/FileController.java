package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    FileUploadService fileUploadService;

    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Map<String, Object> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, Object> map = new LinkedHashMap<>();
        try {
            fileUploadService.uploadFile(file);
            map.put("result", "file uploaded");
        } catch (IOException e) {
            map.put("result", "error while uploading : "+e.getMessage());
        }
        return map;
    }
}
