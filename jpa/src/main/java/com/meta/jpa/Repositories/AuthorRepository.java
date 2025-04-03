package com.meta.jpa.Repositories;

import com.meta.jpa.models.Author;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer>, JpaSpecificationExecutor<Author> {

    @Transactional
    List<Author> findByNamedQuery(@Param("age") int age);

    @Modifying
    @Transactional
    @Query("update Author a set a.age = :age where a.id = :id")
    int updateAuthor(int age, int id);

    @Modifying
    @Transactional
    @Query("update Author a set a.age = :age")
    int updateAllAuthorsAges(int age);

    //select * from authors where first_name = 'ali'
    List<Author> findAllByFirstName(String fn);

    //select * from authors where first_name = 'al'
    List<Author> findAllByFirstNameIgnoreCase(String fn);

    //select * from authors where first_name like '%ali%'
    List<Author> findAllByFirstNameContainingIgnoreCase(String fn);

    //select * from authors where first_name like 'ali%'
    List<Author> findAllByFirstNameStartsWithIgnoreCase(String fn);

    //select * from authors where first_name like '%ali'
    List<Author> findAllByFirstNameEndsWithIgnoreCase(String fn);

    //select * from authors where first_name in ('ali', 'bou', 'code')
    List<Author> findAllByFirstNameInIgnoreCase(List<String> fn);


}
