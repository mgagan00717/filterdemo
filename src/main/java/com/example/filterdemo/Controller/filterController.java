package com.example.filterdemo.Controller;

import com.example.filterdemo.dto.RequestDto;
import com.example.filterdemo.dto.StudentDto;
import com.example.filterdemo.model.Student;
import com.example.filterdemo.service.FilterService;
import com.example.filterdemo.service.FilterSpecification;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

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
    public List<Student> getStudentByAddress(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "City name",
                    schema = @Schema(type = "string", allowableValues = {"Bengaluru", "Mumbai", "Delhi", "Chennai", "Kolkata"})
            )
            @PathVariable String city){
        return filterService.getStudentByAddressCity(city);
    }

    @GetMapping("/getStudentBySubject/{subject}")
    public List<Student> getStudentBySubject(@PathVariable String subject){
        return filterService.getStudentBySubject(subject);
    }

    @GetMapping("/getStudentBySubjectid/{id}")
    public List<Student> getStudentBySubject(@PathVariable int id){
        return filterService.getStudentById(id);
    }

    @GetMapping("/getStudentBySubString")
    public List<Student> getSearchName(@RequestParam(value = "Search")String name){
        Specification<Student> nu = specificaton.getSearchName(name);
        return filterService.getStudentByNameDto(nu);
    }

    @PutMapping("/update")
    public Student updateStudent(@RequestBody Student student){
        return filterService.updateStudent(student);
    }




    @GetMapping("/getStudentBetweenIDs/{ids}")
    public List<Student> getSearchBetween(@PathVariable String ids){
        Specification<Student> nu = specificaton.getSearchBetween(ids);
        return filterService.getStudentBetween(nu);
    }


//    @PostMapping("/specification")
//    public List<Student> getStudent(){
//       return filterService.getStudent();
//    }

    @PostMapping("/specification")
    public List<Student> getStudentUsingDto(@RequestBody RequestDto requestDto){
        Specification<Student> newi = specificaton.getSearchSpecification(requestDto.getSearchRequestDto(),requestDto.getGlobalOperator());
        return filterService.getStudentByDto(newi);
    }

   @PostMapping("/addStudent")
   public void addStudent(@RequestBody Student student){
       filterService.addStudent(student);
  }

  @DeleteMapping("/deleteStudent/{id}")
    public void deleteStudent(@PathVariable int id){
        filterService.deleteStudent(id);

  }

    @GetMapping("/csvexport")
    public void exportCSV(HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        String fileName = "student.csv";
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        StatefulBeanToCsv<StudentDto> beanToCsv = new StatefulBeanToCsvBuilder<StudentDto>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).withSeparator(CSVWriter.DEFAULT_SEPARATOR).withOrderedResults(false).build();

        beanToCsv.write(filterService.getAllStudentCsvData());
    }
}
