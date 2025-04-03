package com.meta.facebook.Services;

import com.meta.facebook.DTO.SchoolDTO;
import com.meta.facebook.models.School;
import org.springframework.stereotype.Service;

@Service
public class SchoolMapper {

    public School toSchool(SchoolDTO dto) {
        return new School(dto.name());
    }

    public SchoolDTO toSchoolDTO(School school) {
        return new SchoolDTO(school.getName());
    }
}
