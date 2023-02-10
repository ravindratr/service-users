package org.service.users.integration;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.service.users.domain.UserEntity;
import org.service.users.model.Gender;
import org.service.users.repositories.UserRepository;
import org.service.users.utils.JsonUtil;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FindUserByIdIntegrationTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private UserRepository userRepository;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp(){
        UserEntity ue = new UserEntity();
        ue.setAge(10);
        ue.setGender(Gender.MALE);
        ue.setId(1L);
        ue.setMobile("9988776655");
        ue.setName("Name");
        userRepository.save(ue);
    }

    @Test
    public void findStudentById4xxError(){
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                buildUrl("/v1/user/10"), HttpMethod.GET, entity, String.class);
        Assertions.assertEquals(404,response.getStatusCode().value());
    }

    @Test
    public void findStudentByIdSuccessfully() throws JSONException {
        String expectedResponse = JsonUtil.getResourceDataAsString("response/find-user-by-id-response.json");

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                buildUrl("/v1/user/1"), HttpMethod.GET, entity, String.class);

        Assertions.assertEquals(200,response.getStatusCode().value());
        JSONAssert.assertEquals(expectedResponse,response.getBody(), JSONCompareMode.LENIENT);
    }

    private String buildUrl(String path){
        return "http://localhost:"+port+path;
    }
}
