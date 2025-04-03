package UploadFile.demo.Controller;

import UploadFile.demo.Module.Brand;
import UploadFile.demo.Module.Document;
import UploadFile.demo.Repository.BrandRepository;
import UploadFile.demo.Repository.DocumentRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@ControllerAdvice
public class AppController {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @Autowired
    private DocumentRepository repository;

    @Autowired
    private BrandRepository brandRepository;

    @GetMapping("/")
    public String viewHomePage(Model model){
        List<Document> listDocs = repository.findAll();
        model.addAttribute("listDocs", listDocs);
        List<Brand> listBrands = brandRepository.findAll();
        model.addAttribute("listBrands", listBrands);
        return "home";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("document")MultipartFile file,
                             RedirectAttributes ra) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        Document document = new Document();
        document.setName(fileName);
        document.setContent(file.getBytes());
        document.setSize(file.getSize());
        document.setUploadTime(new Date());

        repository.save(document);

        ra.addFlashAttribute("message", "This file has been uploaded successfully.");

        return "redirect:/";
    }

    @GetMapping("/download")
    public void downloadFile(
            @Param("id") Long id, HttpServletResponse response
    ) throws Exception {
        Optional<Document> result = repository.findById(id);
        if(!result.isPresent()){
            throw new Exception("Could not find document with ID: " + id);
        }

        Document document = result.get();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + document.getName();

        response.setHeader(headerKey, headerValue);

        ServletOutputStream outputStream = response.getOutputStream();

        outputStream.write(document.getContent());
        outputStream.close();

    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleFileUploadError(RedirectAttributes ra){
        System.out.println("Caught file upload error");
        ra.addFlashAttribute("error", "You could not upload file bigger than " + maxFileSize);
        return "redirect:/";
    }

}
