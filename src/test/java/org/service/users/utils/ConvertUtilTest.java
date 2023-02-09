package org.service.users.utils;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.service.users.contracts.UserDetail;
import org.service.users.domain.UserEntity;
import org.service.users.model.Gender;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
class ConvertUtilTest {


    @Test
    public void toUserDetail() {
        UserEntity ue = new UserEntity();
        ue.setId(1L);
        ue.setMobile("999");
        ue.setGender(Gender.MALE);
        ue.setName("Name");
        ue.setAge(30);

        UserDetail user =  UserDetail.builder()
                .id(1l)
                .name("Name")
                .mobile("999")
                .gender("MALE")
                .age(30)
                .build();
        assertEquals(user,ConvertUtil.toUserDetail(ue));
    }
}