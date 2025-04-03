package com.meta.facebook.Services;

import com.meta.facebook.DTO.StudentDTO;
import com.meta.facebook.Responses.StudentResponseDto;
import com.meta.facebook.models.School;
import com.meta.facebook.models.Student;
import org.springframework.stereotype.Service;

@Service
public class StudentMapper {

    public Student toStudent(StudentDTO dto){

        if(dto == null){
            throw new NullPointerException("This student dto should not be null");
        }

        var student = new Student();
        student.setFirstName(dto.firstName());
        student.setLastName(dto.lastName());
        student.setEmail(dto.email());

        var school = new School();
        school.setSchool_id(dto.schoolId());
        student.setSchool(school);

        return student;
    }

    public StudentResponseDto toStudentResponseDto(Student student){
        return new StudentResponseDto(
                student.getFirstName(),
                student.getLastName(),
                student.getEmail()
        );
    }
}
