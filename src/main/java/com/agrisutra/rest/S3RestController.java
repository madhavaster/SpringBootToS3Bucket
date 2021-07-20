package com.agrisutra.rest;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class S3RestController {

    @Autowired
    private AmazonS3 amazonS3;
    @Value("${aws.bucket.name}")
    private String bucketName;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        File f = new File(fileName);
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(file.getBytes());
        PutObjectResult putObjectResult = amazonS3.putObject(new PutObjectRequest(bucketName, fileName, f));
        f.delete();
        return ResponseEntity.ok("File uploaded successfully "+fileName);
    }
}
