package org.service.users.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.service.users.domain.UserEntity;
import org.service.users.model.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void Test_FindById(){
        UserEntity ue = new UserEntity();
        ue.setAge(10);
        ue.setGender(Gender.MALE);
        ue.setMobile("9988776655");
        ue.setName("Name");
        entityManager.persist(ue);
        entityManager.flush();
        Optional<UserEntity> user = userRepository.findById(1L);
        Assertions.assertNotNull(user);
        Assertions.assertEquals("9988776655",user.get().getMobile());
    }

}