package org.service.users.contracts;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetail {

    private Long id;
    private String name;
    private String gender;
    private String mobile;

    private Integer age;
}
