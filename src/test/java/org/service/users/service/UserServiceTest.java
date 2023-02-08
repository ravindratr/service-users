package org.service.users.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.service.users.contracts.UserDetail;
import org.service.users.domain.UserEntity;
import org.service.users.exception.ValidationException;
import org.service.users.model.Gender;
import org.service.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class UserServiceTest {

    @MockBean
    private UserRepository userRepo;

    @Autowired
    private UserService userService;

    private UserEntity ue;

    private UserDetail userDetail;

    @BeforeAll
    public void setUp(){
        ue = new UserEntity();
        ue.setAge(10);
        ue.setGender(Gender.MALE);
        ue.setId(1L);
        ue.setMobile("9988776655");
        ue.setName("Name");

        userDetail = UserDetail.builder()
                .age(10)
                .name("Name")
                .gender("MALE")
                .mobile("9988776655")
                .id(1L)
                .build();
    }

    @Test
    public void TestFindById_Successful() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(ue));
        assertEquals(userDetail,userService.fetchUserById(1L));
    }

    @Test
    public void TestFindById_Exception() {
        when(userRepo.findById(1L)).thenReturn(null);
        ValidationException exception = Assertions.assertThrows(ValidationException.class,() -> {
            userService.fetchUserById(1L);});
        assertEquals("USER_NOT_FOUND",exception.getMessage());
    }
}