package org.service.users.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.service.users.contracts.GenericResponse;
import org.service.users.contracts.UserDetail;
import org.service.users.contracts.UserRequest;
import org.service.users.exception.ValidationException;
import org.service.users.model.ErrorCode;
import org.service.users.model.ErrorDetail;
import org.service.users.service.UserService;
import org.service.users.utils.ErrorUtil;
import org.service.users.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {UserController.class})
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ErrorUtil errorUtil;

    private UserDetail userDetail;

    @BeforeEach
    public void setUp(){
        userDetail = UserDetail.builder()
                .id(10L)
                .age(50)
                .mobile("9638527412")
                .gender("MALE")
                .name("Test User")
                .build();
    }

    @Test
    public void Test_FetchUserThrow4xxForInvalidUser() throws Exception {
        when(userService.fetchUserById(2L)).thenThrow(new ValidationException(ErrorCode.USER_NOT_FOUND.name()));
        when(errorUtil.getErrorDetail(ErrorCode.USER_NOT_FOUND.name())).thenReturn(ErrorDetail.builder()
                .code("USER_NOT_FOUND")
                .message("User Not Found")
                .httpStatus(404)
                .build());

        mvc.perform(get("/v1/user/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    public void Test_FetchUserSuccessfully() throws Exception {
        when(userService.fetchUserById(10L)).thenReturn(userDetail);

        MvcResult mvcResult = mvc.perform(get("/v1/user/10")
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();

        GenericResponse<UserDetail> response = JsonUtil.mapFromJson(mvcResult.getResponse().getContentAsString(),GenericResponse.class);

        assertEquals(200,mvcResult.getResponse().getStatus());
        assertEquals(true,response.isSuccess());
        assertNull(response.getErrors());
        assertNotNull(response.getData());
    }

    @Test
    public void Test_AddUserThrow4xxForInvalidRequest() throws Exception {
        UserRequest request = new UserRequest();
        request.setMobile("8989");
        String requestString = JsonUtil.mapToJson(request);

        when(userService.addUser(request)).thenThrow(new ValidationException(ErrorCode.USER_CREATE_MISSING_PARAM.name()));
        when(errorUtil.getErrorDetail(ErrorCode.USER_CREATE_MISSING_PARAM.name())).thenReturn(ErrorDetail.builder()
                .code("USER_CREATE_MISSING_PARAM")
                .message("Missing mandatory params")
                .httpStatus(400)
                .build());

        MvcResult mvcResult = mvc.perform(post("/v1/user")
                        .contentType(MediaType.APPLICATION_JSON).content(requestString)).andReturn();

        GenericResponse<UserDetail> response = JsonUtil.mapFromJson(mvcResult.getResponse().getContentAsString(),GenericResponse.class);
        assertEquals(400,mvcResult.getResponse().getStatus());
        assertEquals(false,response.isSuccess());
        assertEquals("USER_CREATE_MISSING_PARAM",response.getErrors().get(0).getCode());
        assertEquals("Missing mandatory params",response.getErrors().get(0).getValue());
    }


}