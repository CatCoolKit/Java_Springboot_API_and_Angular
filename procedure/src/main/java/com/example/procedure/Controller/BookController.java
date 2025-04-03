package com.example.procedure.Controller;

import com.example.procedure.Model.Book;
import com.example.procedure.Repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class BookController {

    private final BookRepository bookRepository;

    @GetMapping("/get-all-book")
    @Transactional
    public ResponseEntity<Object> getAllBook(){
        List<Book> listBook = bookRepository.getAllBooksUsingProcedure();
        return ResponseEntity.ok(listBook);
    }
}
