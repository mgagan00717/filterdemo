package com.example.filterdemo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;
}



//{
//        "id": 1,
//        "name": "John Doe",
//        "address": {
//        "addressId": 101,
//        "city": "Bengaluru"
//        },
//        "subjects": [
//        {
//        "id": 101,
//        "name": "Mathematics",
//        "student": {
//        "id": 1,
//        "name": "John Doe"
//        }
//        },
//        {
//        "id": 102,
//        "name": "Science",
//        "student": {
//        "id": 1,
//        "name": "John Doe"
//        }
//        }
//        ]
//        }

