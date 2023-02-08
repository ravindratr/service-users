package org.service.users.utils;

import org.service.users.contracts.UserDetail;
import org.service.users.domain.UserEntity;

public class ConvertUtil {

    public static UserDetail toUserDetail(UserEntity userEntity){
        return UserDetail.builder()
                .id(userEntity.getId())
                .gender(userEntity.getGender().name())
                .mobile(userEntity.getMobile())
                .age(userEntity.getAge())
                .name(userEntity.getName())
                .build();
    }
}
