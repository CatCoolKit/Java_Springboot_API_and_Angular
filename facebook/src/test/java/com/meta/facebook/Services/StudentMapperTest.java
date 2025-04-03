package com.meta.facebook.Services;

import com.meta.facebook.DTO.StudentDTO;
import com.meta.facebook.Responses.StudentResponseDto;
import com.meta.facebook.models.Student;
import org.junit.jupiter.api.*;

public class StudentMapperTest {

    private StudentMapper studentMapper;

    @BeforeEach
    void setUp() {
        studentMapper = new StudentMapper();
    }

//    @AfterAll
//    static void afterAll() {
//        System.out.println("Inside the after all each method");
//    }

    //    @BeforeEach
//    void setUp() {
//        System.out.println("Inside the before each method");
//    }
//
//    @AfterEach
//    void tearDown() {
//        System.out.println("Inside the After each method");
//    }

    @Test
    public void shouldMapStudentDtoToStudent(){
        StudentDTO dto = new StudentDTO("John", "Doe", "jhon@gmail.com", 1);

        Student student = studentMapper.toStudent(dto);

        Assertions.assertEquals(dto.firstName(), student.getFirstName());
        Assertions.assertEquals(dto.lastName(), student.getLastName());
        Assertions.assertEquals(dto.email(), student.getEmail());
        Assertions.assertNotNull(student.getSchool());
        Assertions.assertEquals(dto.schoolId(), student.getSchool().getSchool_id());
    }

    @Test
    public void should_map_studentDto_to_student_when_studentDto_is_null(){
        Student student = studentMapper.toStudent(null);

        Assertions.assertEquals("", student.getFirstName());
        Assertions.assertEquals("", student.getLastName());
    }

    @Test
    public void should_throw_studentDto_to_student_when_studentDto_is_null(){

        var msg = Assertions.assertThrows(NullPointerException.class, () -> studentMapper.toStudent(null));
        Assertions.assertEquals("This student dto should not be null", msg.getMessage());

    }

    @Test
    public void shouldMapStudentDtoToStudentResponseDto(){
        //Given
        Student student = new Student("John", "Doe", "jhon@gmail.com", 20);

        //When
        StudentResponseDto response = studentMapper.toStudentResponseDto(student);

        //Then
        Assertions.assertEquals(response.firstName(), student.getFirstName());
        Assertions.assertEquals(response.lastName(), student.getLastName());
        Assertions.assertEquals(response.email(), student.getEmail());
    }
}
