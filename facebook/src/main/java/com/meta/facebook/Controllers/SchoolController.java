package com.meta.facebook.Controllers;

import com.meta.facebook.DTO.SchoolDTO;
import com.meta.facebook.Repositories.SchoolRepository;
import com.meta.facebook.Services.SchoolService;
import com.meta.facebook.models.School;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping("/schools")
    public SchoolDTO createSchool(
            @RequestBody SchoolDTO dto
    ){
        return schoolService.createSchool(dto);
    }

    @GetMapping("/schools")
    public List<SchoolDTO> findAllSchool(){
        return schoolService.findAllSchool();
    }
}
