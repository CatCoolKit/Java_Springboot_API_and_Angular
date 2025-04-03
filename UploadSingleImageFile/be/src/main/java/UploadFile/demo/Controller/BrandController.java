package UploadFile.demo.Controller;

import UploadFile.demo.Module.Brand;
import UploadFile.demo.Module.Document;
import UploadFile.demo.Repository.BrandRepository;
import UploadFile.demo.Service.FIileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@ControllerAdvice
public class BrandController {

    @Autowired
    private BrandRepository brandRepository;

    @PostMapping("/brands/save")
    public String saveBrand(
            @ModelAttribute(name = "brand")Brand brand,
            RedirectAttributes ra,
            @RequestParam(name = "fileImage")MultipartFile multipartFile,
            @RequestParam(name = "fileName")String name
            ) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        brand.setName(fileName);
        brand.setLogo(name);
        Brand saveBrand = brandRepository.save(brand);

        String uploadDir = "./brand-logos/" + saveBrand.getId();

        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        try {
            InputStream inputStream = multipartFile.getInputStream();
            Path filePath = uploadPath.resolve(fileName);
            System.out.println(filePath.toFile().getAbsolutePath());
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            throw new IOException("Could not save uploaded file: " + fileName);
        }

        ra.addFlashAttribute("message", "The brand has been saved successfully.");

        return "redirect:/";
    }

    @PostMapping("/brands/save/multiple")
    public String saveBrandMultiple(
            @ModelAttribute(name = "brand")Brand brand,
            RedirectAttributes ra,
            @RequestParam(name = "mainImage")MultipartFile[] extraMultipartFile
    ) throws IOException {
        int count = 1;
        for (MultipartFile exMultipart : extraMultipartFile){
            String extraImageName = StringUtils.cleanPath(exMultipart.getOriginalFilename());
            if (count == 1) brand.setExtraImage1(extraImageName);
            if (count == 2) brand.setExtraImage2(extraImageName);
            if (count == 3) brand.setExtraImage3(extraImageName);
            count++;
        }
        brand.setLogo("Test Logo");
        brand.setName("Test Name");
        Brand saveBrand = brandRepository.save(brand);

        String uploadDir = "./brand-logos/" + saveBrand.getId();

        for (MultipartFile exMultipart : extraMultipartFile){
            String fileName = StringUtils.cleanPath(exMultipart.getOriginalFilename());
            FIileUploadUtil.saveFile(uploadDir, exMultipart, fileName);
        }

        ra.addFlashAttribute("message", "The brand has been saved successfully.");

        return "redirect:/";
    }

    @GetMapping("/images/{folder}/{filename}")
    @ResponseBody
    public Resource serveFile(@PathVariable String folder, @PathVariable String filename) throws Exception {
        Path filePath = Paths.get("./brand-logos/" + folder + "/" + filename);
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("File not found: " + filename);
        }

        return resource;
    }
}
