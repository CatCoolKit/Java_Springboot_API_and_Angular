package com.meta.facebook.Controllers;

import com.meta.facebook.DTO.StudentDTO;
import com.meta.facebook.Repositories.StudentIRepository;
import com.meta.facebook.Responses.StudentResponseDto;
import com.meta.facebook.Services.StudentMapper;
import com.meta.facebook.Services.StudentService;
import com.meta.facebook.models.Order;
import com.meta.facebook.models.OrderRecord;
import com.meta.facebook.models.School;
import com.meta.facebook.models.Student;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/students")
    public StudentResponseDto saveStudent(
            @Valid @RequestBody StudentDTO studentDto
    ){
        return studentService.saveStudent(studentDto);
    }

    @GetMapping("/students")
    public List<StudentResponseDto> findAllStudent(){
        return studentService.findAllStudent();
    }

    @GetMapping("/students/{student-id}")
    public StudentResponseDto findStudentById(
            @PathVariable("student-id") Integer id
    ){
        return studentService.findStudentById(id);
    }

    @GetMapping("/students/search/{student-name}")
    public List<StudentResponseDto> findStudentByName(
            @PathVariable("student-name") String name
    ){
        return studentService.findStudentByName(name);
    }

    @DeleteMapping("/students/{student-id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStudent(
            @PathVariable("student-id") Integer id
    ){
        studentService.deleteStudent(id);
    }

    //@GetMapping("/hello")
    public String sayHello(){
        return "Hello, I am from Viet Name. 1";
    }

    @GetMapping("/hello-2")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String sayHello2(){
        return "Hello, I am from Viet Name. 2";
    }

    @PostMapping("/post")
    public String post(
            @RequestBody String message
    ){
        return "Request accepted and message is : " + message;
    }

    @PostMapping("/post-order")
    public String postOrder(
            @RequestBody Order order
    ){
        return "Request accepted and order is : " + order.toString();
    }

    @PostMapping("/post-order-record")
    public String postOrderRecord(
            @RequestBody OrderRecord orderRecord
    ){
        return "Request accepted and order record is : " + orderRecord.toString();
    }

    //@GetMapping("/hello/{user-name}")
    public String pathVar(
            @PathVariable("user-name") String userName
    ){
        return "My value : " + userName;
    }

    @GetMapping("/hello")
    public String paramVar(
            @RequestParam("user-name") String userName,
            @RequestParam("user-lastname") String userLastName
    ){
        return "My value : " + userName + " " + userLastName;
    }

}
