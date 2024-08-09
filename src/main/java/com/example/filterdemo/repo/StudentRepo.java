package com.example.filterdemo.repo;

import com.example.filterdemo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    Student findByName(String name);

    List<Student> findByAddressCity(String City);
    List<Student> findBySubjectsName(String name);
    List<Student> findBySubjectsId(int id);
}
