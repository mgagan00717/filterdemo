package com.example.filterdemo.Controller;

import com.example.filterdemo.dto.RequestDto;
import com.example.filterdemo.model.Student;
import com.example.filterdemo.service.FilterService;
import com.example.filterdemo.service.FilterSpecification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class filterController {

    @Autowired
    private FilterService filterService;
    @Autowired
    private FilterSpecification<Student> specificaton;



    @GetMapping("/getAll")
    public List<Student> getAllStudent(){
        return filterService.getAllStudent();
    }

    @GetMapping("/getStudentByName/{name}")
    public Student getStudentByName(@PathVariable String name){
        return filterService.getStudentByName(name);
    }

    @GetMapping("/getStudentByAddress/{city}")
    public List<Student> getStudentByAddress(@PathVariable String city){
        return filterService.getStudentByAddressCity(city);
    }

//    @GetMapping("/getStudentBySubject/{subject}")
//    public List<Student> getStudentBySubject(@PathVariable String subject){
//        return filterService.getStudentBySubject(subject);
//    }

//    @PostMapping("/specification")
//    public List<Student> getStudent(){
//       return filterService.getStudent();
//    }

    @PostMapping("/specification")
    public List<Student> getStudentUsingDto(@RequestBody RequestDto requestDto){
        Specification<Student> newi = specificaton.getSearchSpecification(requestDto.getSearchRequestDto(),requestDto.getGlobalOperator());
        return filterService.getStudentByDto(newi);
    }



}
