package com.example.filterdemo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestDto {

    private List<SearchRequestDto> searchRequestDto;
    public GlobalOperator globalOperator;
    public enum GlobalOperator{
        AND,OR
    }


}
