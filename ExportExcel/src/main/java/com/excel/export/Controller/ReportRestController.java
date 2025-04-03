package com.excel.export.Controller;

import com.excel.export.Service.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@ControllerAdvice
public class ReportRestController {

    @GetMapping("/")
    public String showUploadForm() {
        return "home"; // Tên file HTML không cần phần mở rộng .html
    }

    @Autowired
    private ReportService reportService;

    @GetMapping("/excel")
    public void generateExcelReport(HttpServletResponse response) throws Exception{

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=course.xls";

        response.setHeader(headerKey, headerValue);

        reportService.generateExcel(response);
    }

    @GetMapping("/export-docx")
    public void exportDocx(HttpServletResponse response) throws IOException {
        reportService.generateDocx(response);
    }

    @PostMapping("/upload-excel")
    public String handleFileUpload(@RequestParam("excelFile") MultipartFile file) {
        try {
            // Kiểm tra định dạng file (xls hoặc xlsx)
            String fileType = file.getOriginalFilename().split("\\.")[1];
            if ("xls".equalsIgnoreCase(fileType)) {
                reportService.readXlsFile(file.getInputStream());
            } else if ("xlsx".equalsIgnoreCase(fileType)) {
                reportService.readXlsxFile(file.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/";  // Sau khi xử lý, chuyển hướng về trang chủ
    }

    @PostMapping("/upload-word")
    public String handleWordUpload(@RequestParam("wordFile") MultipartFile wordFile) {
        try {
            if (!wordFile.isEmpty()) {
                reportService.readDocxFile(wordFile.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/";  // Sau khi xử lý, chuyển hướng về trang chủ
    }

}
