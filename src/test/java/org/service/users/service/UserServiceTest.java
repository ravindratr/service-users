package org.service.users.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.service.users.contracts.UserDetail;
import org.service.users.domain.UserEntity;
import org.service.users.exception.ValidationException;
import org.service.users.model.Gender;
import org.service.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ExtendWith({SpringExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {UserRepository.class,UserService.class})
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

    @Test
    public void TestFindAll_Successful() {
        Page page = new PageImpl<>(List.of(ue));
        when(userRepo.findAll(PageRequest.of(0, 30))).thenReturn(page);
        assertEquals(List.of(userDetail),userService.fetchAllUsers(0,30));
    }

    @Test
    public void TestFindAll_EmptyListSuccessful() {
        Page page = new PageImpl<>(new ArrayList<>());
        when(userRepo.findAll(PageRequest.of(0, 30))).thenReturn(page);
        assertEquals(0,userService.fetchAllUsers(0,30).size());
    }
}