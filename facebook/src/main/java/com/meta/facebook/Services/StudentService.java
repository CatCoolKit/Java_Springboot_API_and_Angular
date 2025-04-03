package com.meta.facebook.Services;

import com.meta.facebook.DTO.StudentDTO;
import com.meta.facebook.Repositories.StudentIRepository;
import com.meta.facebook.Responses.StudentResponseDto;
import com.meta.facebook.models.Student;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentIRepository studentIRepository;
    private final StudentMapper studentMapper;

    public StudentService(StudentIRepository studentIRepository, StudentMapper studentMapper) {
        this.studentIRepository = studentIRepository;
        this.studentMapper = studentMapper;
    }

    public StudentResponseDto saveStudent(StudentDTO studentDto){
        var student = studentMapper.toStudent(studentDto);
        var saveStudent = studentIRepository.save(student);
        return studentMapper.toStudentResponseDto(saveStudent);
    }

    public List<StudentResponseDto> findAllStudent(){
        return studentIRepository.findAll()
                .stream()
                .map(studentMapper::toStudentResponseDto)
                .collect(Collectors.toList());
    }

    public StudentResponseDto findStudentById(Integer id){

        return studentIRepository.findById(id)
                .map(studentMapper::toStudentResponseDto)
                .orElse(null);
    }

    public List<StudentResponseDto> findStudentByName(String name){
        return studentIRepository.findAllByFirstNameContaining(name)
                .stream()
                .map(studentMapper::toStudentResponseDto)
                .collect(Collectors.toList());
    }

    public void deleteStudent(Integer id){
        studentIRepository.deleteById(id);
    }

}
