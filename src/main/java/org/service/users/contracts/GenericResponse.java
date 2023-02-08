package org.service.users.contracts;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse <T>{

    private boolean success;
    private List<Error> errors;

    T data;

    public void addError(Error error){
        if(null==errors){
            errors = new ArrayList<>();
        }
        errors.add(error);
    }
}
