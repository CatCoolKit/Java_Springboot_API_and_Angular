package com.meta.facebook.Services;

import com.meta.facebook.DTO.SchoolDTO;
import com.meta.facebook.Repositories.SchoolRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final SchoolMapper schoolMapper;

    public SchoolService(SchoolRepository schoolRepository, SchoolMapper schoolMapper) {
        this.schoolRepository = schoolRepository;
        this.schoolMapper = schoolMapper;
    }

    public SchoolDTO createSchool(SchoolDTO dto){
        var school = schoolMapper.toSchool(dto);
        schoolRepository.save(school);
        return dto;
    }

    public List<SchoolDTO> findAllSchool(){
        return schoolRepository.findAll()
                .stream()
                .map(schoolMapper::toSchoolDTO)
                .collect(Collectors.toList());
    }

}
