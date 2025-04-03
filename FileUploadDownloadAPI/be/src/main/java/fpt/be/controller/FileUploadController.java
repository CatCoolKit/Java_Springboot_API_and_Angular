package fpt.be.controller;

import fpt.be.response.FileUploadResponse;
import fpt.be.service.FileUploadUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileUploadController {

    @PostMapping("/uploadFile")
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam("file")MultipartFile multipartFile
            ) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();

        String fileCode = FileUploadUtil.saveFile(fileName, multipartFile);

        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUrl("/downloadFile/" + fileCode);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
