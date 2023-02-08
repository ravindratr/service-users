package org.service.users.model;

import lombok.Data;

@Data
public class ErrorDetail {

    private String code;
    private String message;
    private Integer httpStatus;
}
