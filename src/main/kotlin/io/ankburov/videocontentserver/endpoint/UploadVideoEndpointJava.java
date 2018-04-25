package io.ankburov.videocontentserver.endpoint;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload-java")
public class UploadVideoEndpointJava {

    @PostMapping(consumes = "multipart/form-data")
    public void queueBatchOperations(@RequestParam MultipartFile file) {
        System.out.println(file);
    }
}
