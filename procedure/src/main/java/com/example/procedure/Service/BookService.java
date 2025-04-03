package com.example.procedure.Service;

import com.example.procedure.Model.Book;
import com.example.procedure.Repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {

    private final BookRepository bookRepository;

    @Override
    public int getCountOfBooks(String author) {
        return bookRepository.get_count_of_books(author);
    }

    @Override
    public int getBookCount(String author) {
        return bookRepository.getBookCount(author);
    }

    @Override
    @Transactional
    public int getCountByAuthor(String author) {
        return bookRepository.getCountByAuthor(author);
    }

    @Override
    public List<Book> getBookListByAuth(String author) {
        return bookRepository.getBookListByAuth(author);
    }
}
