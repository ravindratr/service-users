package org.service.users.controller;

import org.service.users.contracts.Error;
import org.service.users.contracts.GenericResponse;
import org.service.users.contracts.UserDetail;
import org.service.users.contracts.UserRequest;
import org.service.users.model.ErrorDetail;
import org.service.users.service.UserService;
import org.service.users.utils.ErrorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ErrorUtil errorUtil;

    @GetMapping(value = "/v1/users",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<List<UserDetail>>> fetchAllUsers(@RequestParam(name="page",defaultValue = "0") Integer page,
                                                                           @RequestParam(name="pageSize",defaultValue = "20") Integer pageSize){
        GenericResponse<List<UserDetail>> response = new GenericResponse<>();
        try{
            response.setData(userService.fetchAllUsers(page,pageSize));
            response.setSuccess(true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            ErrorDetail error = errorUtil.getErrorDetail(e.getMessage());
            response.setSuccess(false);
            response.addError(buildError(error));
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(error.getHttpStatus()));
        }
    }

    @GetMapping(value = "/v1/user/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<UserDetail>> fetchAllUserById(@PathVariable("id") Long userId){
        GenericResponse<UserDetail> response = new GenericResponse<>();
        try{
            response.setData(userService.fetchUserById(userId));
            response.setSuccess(true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(Exception e){
            ErrorDetail error = errorUtil.getErrorDetail(e.getMessage());
            response.setSuccess(false);
            response.addError(buildError(error));
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(error.getHttpStatus()));
        }
    }

    @DeleteMapping(value = "/v1/user/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<Boolean>> deleteUserById(@PathVariable("id") Long userId){
        GenericResponse<Boolean> response = new GenericResponse<>();
        try{
            response.setData(userService.deleteUserById(userId));
            response.setSuccess(true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(Exception e){
            ErrorDetail error = errorUtil.getErrorDetail(e.getMessage());
            response.setSuccess(false);
            response.addError(buildError(error));
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(error.getHttpStatus()));
        }
    }

    @PostMapping(value = "/v1/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<UserDetail>> addUser(@RequestBody UserRequest request){
        GenericResponse<UserDetail> response = new GenericResponse<>();
        try{
            response.setData(userService.addUser(request));
            response.setSuccess(true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(Exception e){
            ErrorDetail error = errorUtil.getErrorDetail(e.getMessage());
            response.setSuccess(false);
            response.addError(buildError(error));
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(error.getHttpStatus()));
        }
    }

    private Error buildError(ErrorDetail error){
        return Error.builder()
                .code(error.getCode())
                .value(error.getMessage())
                .build();
    }
}
