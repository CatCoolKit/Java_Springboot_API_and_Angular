package com.example.procedure.Repository;

import com.example.procedure.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Procedure
    int get_count_of_books(String author);

    @Procedure(procedureName = "get_count_of_books")
    int getBookCount(String author);

    @Procedure(name = "getBookCountByAuthor")
    int getCountByAuthor(@Param("auth") String author);

    @Query(value = "call get_count_of_books(:author)", nativeQuery = true)
    List<Book> getBookListByAuth(@Param("author") String author);

    @Procedure(procedureName = "GetAllBooks")
    List<Book> getAllBooksUsingProcedure();
}
