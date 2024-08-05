package com.example.filterdemo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequestDto {
    String column;
    String value;
    Operation operation;
    String joinTable;

    public enum Operation {
        EQUAL,LIKE,IN,GREATER_THAN,LESS_THAN,BETWEEN,JOIN
    }
}
