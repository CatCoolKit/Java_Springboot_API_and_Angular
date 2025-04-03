package com.meta.facebook.Services;

import com.meta.facebook.DTO.StudentDTO;
import com.meta.facebook.Repositories.StudentIRepository;
import com.meta.facebook.Responses.StudentResponseDto;
import com.meta.facebook.models.Student;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;
    @Mock
    private StudentMapper studentMapper;
    @Mock
    private StudentIRepository studentIRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_successfully_save_a_student(){
        //Given
        StudentDTO dto = new StudentDTO("John", "Doe", "jhon@gmail.com", 1);
        Student student = new Student("John", "Doe", "jhon@gmail.com", 20);
        Student saveStudent = new Student("John", "Doe", "jhon@gmail.com", 20);
        saveStudent.setStudent_id(1);

        //Mock the calls
        when(studentMapper.toStudent(dto))
                .thenReturn(student);
        when(studentIRepository.save(student))
                .thenReturn(saveStudent);
        when(studentMapper.toStudentResponseDto(saveStudent))
                .thenReturn(new StudentResponseDto("John","Doe","jhon@gmail.com"));

        //When
        StudentResponseDto responseDto = studentService.saveStudent(dto);

        //Then
        assertEquals(dto.firstName(), responseDto.firstName());
        assertEquals(dto.lastName(), responseDto.lastName());
        assertEquals(dto.email(), responseDto.email());

        verify(studentMapper, times(1)).toStudent(dto);
        verify(studentIRepository, times(1)).save(student);
        verify(studentMapper, times(1)).toStudentResponseDto(saveStudent);

    }

    @Test
    public void should_return_all_students(){
        //Given
        List<Student> list = new ArrayList<>();
        Student student = new Student("John", "Doe", "jhon@gmail.com", 20);
        Student student1 = new Student("John1", "Doe1", "jhon1@gmail.com", 21);
        Student student2 = new Student("John2", "Doe2", "jhon2@gmail.com", 22);
        list.add(student);
        list.add(student1);
        list.add(student2);

        //Mock the calls
        when(studentIRepository.findAll()).thenReturn(list);
        when(studentMapper.toStudentResponseDto(student))
                .thenReturn(new StudentResponseDto("John", "Doe", "jhon@gmail.com"));
        when(studentMapper.toStudentResponseDto(student1))
                .thenReturn(new StudentResponseDto("John1", "Doe1", "jhon1@gmail.com"));
        when(studentMapper.toStudentResponseDto(student2))
                .thenReturn(new StudentResponseDto("John2", "Doe2", "jhon2@gmail.com"));

        //When
        List<StudentResponseDto> result = studentService.findAllStudent();

        //Then
        assertNotNull(result);
        assertEquals(list.size(), result.size());

        assertEquals(student.getFirstName(), result.get(0).firstName());
        assertEquals(student.getLastName(), result.get(0).lastName());
        assertEquals(student.getEmail(), result.get(0).email());

        verify(studentIRepository, times(1)).findAll();

    }

    @Test
    public void should_return_student_by_id(){
        //Given
        Integer studentId = 1;
        Student student = new Student("John", "Doe", "jhon@gmail.com", 20);

        //Mock the calls
        when(studentIRepository.findById(studentId))
                .thenReturn(Optional.of(student));
        when(studentMapper.toStudentResponseDto(any(Student.class)))
                .thenReturn(new StudentResponseDto("John","Doe","jhon@gmail.com"));

        //When
        StudentResponseDto responseDto = studentService.findStudentById(studentId);

        //Then
        assertEquals(student.getFirstName(), responseDto.firstName());
        assertEquals(student.getLastName(), responseDto.lastName());
        assertEquals(student.getEmail(), responseDto.email());

        verify(studentIRepository, times(1)).findById(studentId);

    }

    @Test
    public void should_return_student_by_name(){
        //Given
        String name = "John";
        List<Student> list = new ArrayList<>();
        Student student = new Student("John", "Doe", "jhon@gmail.com", 20);
        Student student1 = new Student("John1", "Doe1", "jhon1@gmail.com", 21);
        Student student2 = new Student("John2", "Doe2", "jhon2@gmail.com", 22);
        list.add(student);
        list.add(student1);
        list.add(student2);

        //Mock the calls
        when(studentIRepository.findAllByFirstNameContaining(name)).thenReturn(list);
        when(studentMapper.toStudentResponseDto(student))
                .thenReturn(new StudentResponseDto("John", "Doe", "jhon@gmail.com"));
        when(studentMapper.toStudentResponseDto(student1))
                .thenReturn(new StudentResponseDto("John1", "Doe1", "jhon1@gmail.com"));
        when(studentMapper.toStudentResponseDto(student2))
                .thenReturn(new StudentResponseDto("John2", "Doe2", "jhon2@gmail.com"));

        //When
        var responseDto = studentService.findStudentByName(name);

        //Then
        assertNotNull(responseDto);
        assertEquals(list.size(), responseDto.size());

        verify(studentIRepository, times(1)).findAllByFirstNameContaining(name);

    }
}
