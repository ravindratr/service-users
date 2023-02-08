package org.service.users.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.service.users.model.Gender;

@Data
@Entity(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "age")
    private Integer age;

    @Column(name = "gender")
    private Gender gender;
}
