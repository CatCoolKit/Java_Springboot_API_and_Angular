package com.meta.facebook.Repositories;

import com.meta.facebook.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentIRepository extends JpaRepository<Student, Integer> {

    public List<Student> findAllByFirstNameContaining(String p);
}
