package com.example.filterdemo.dto;

import com.opencsv.bean.CsvCustomBindByName;

public class StudentDto {
    private long id;
    private String name;
    private String city;


    private String[] subjects;

    public StudentDto(long id, String name, String city, String[] subjects) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.subjects = subjects;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String[] getSubjects() {
        return subjects;
    }

    public void setSubjects(String[] subjects) {
        this.subjects = subjects;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "StudentDto{" +
                "id=" + id +
                ", name=" + name +
                ", city=" + city +
                ", subjects=" + java.util.Arrays.toString(subjects) +
                '}';
    }
}
