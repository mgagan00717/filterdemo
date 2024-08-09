package com.example.filterdemo.service;


import com.example.filterdemo.dto.StudentDto;
import com.example.filterdemo.model.Student;
import com.example.filterdemo.model.Subject;
import com.example.filterdemo.repo.StudentRepo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

@Service
public class FilterService {

    @Autowired
    StudentRepo repo;
    public List<Student> getAllStudent() {
        System.out.println(repo.findAll());
       return (List<Student>) repo.findAll();
    }

    public Student getStudentByName(String name) {
        return repo.findByName(name);
    }

    public List<Student> getStudentByAddressCity(String city) {
        return repo.findByAddressCity(city);
    }

    public List<Student> getStudent() {
        Specification<Student> specification = new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("name"),"mukesh");
            }
        };
        List<Student> all = repo.findAll(specification);
        return all;
    }

    public List<Student> getStudentByDto(Specification<Student> newi) {
        return repo.findAll(newi);
    }

    public List<Student> getStudentBySubject(String subject) {
        return repo.findBySubjectsName(subject);
    }

    public List<Student> getStudentById(int id) {
        return repo.findBySubjectsId(id);
    }

    public List<Student> getStudentByNameDto(Specification<Student> nu) {
        return repo.findAll(nu);
    }

    public List<Student> getStudentBetween(Specification<Student> nu) {
        return repo.findAll(nu);
    }

    public void addStudent(Student student) {
        repo.save(student);
    }

    public void deleteStudent(int id) {
        repo.deleteById((long) id);
    }

    public Student updateStudent(Student student) {
       return repo.save(student);
    }

    public List<StudentDto> getAllStudentCsvData() {
        List<Student> students = repo.findAll();
        System.out.println(students.stream()
                .map(student -> new StudentDto(
                        student.getId(),
                        student.getName(),
                        student.getAddress().getCity(),
                        student.getSubjects().stream().map(Subject::getName).toArray(String[]::new)
                ))
                .collect(Collectors.toList()));
        return students.stream()
                .map(student -> new StudentDto(
                        student.getId(),
                        student.getName(),
                        student.getAddress().getCity(),
                        student.getSubjects().stream().map(Subject::getName).toArray(String[]::new)
                ))
                .collect(Collectors.toList());
    }





//    public List<Student> getStudentBySubject(String subject) {
//        return repo.findBySubjectName(subject);
//    }
}
