package org.service.users.contracts;

import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String mobile;
    private Integer age;
    private String gender;
}
