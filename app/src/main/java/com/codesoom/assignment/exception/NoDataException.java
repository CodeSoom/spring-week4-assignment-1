package com.codesoom.assignment.exception;

import lombok.Getter;

@Getter
public class NoDataException extends RuntimeException{

    private Long id;

    public NoDataException(Long id){

        this.id = id;
    }


}
