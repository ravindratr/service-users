package org.service.users.service;

import org.apache.commons.lang3.StringUtils;
import org.service.users.contracts.UserDetail;
import org.service.users.contracts.UserRequest;
import org.service.users.domain.UserEntity;
import org.service.users.exception.ValidationException;
import org.service.users.model.ErrorCode;
import org.service.users.model.Gender;
import org.service.users.repositories.UserRepository;
import org.service.users.utils.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepo;

    public List<UserDetail> fetchAllUsers(Integer pageNumber,Integer pageSize){
        List<UserDetail> users = new ArrayList<>();
        Page<UserEntity> page = userRepo.findAll(PageRequest.of(pageNumber, pageSize));
        if(null==page || page.isEmpty()){
            return users;
        }
        for(UserEntity user: page.getContent()){
                users.add(ConvertUtil.toUserDetail(user));
        }
        return users;
    }

    public UserDetail fetchUserById(Long id){
        logger.info("Request received for userId : {}",id);
        Optional<UserEntity> optionalUser = userRepo.findById(id);
        if(null==optionalUser || !optionalUser.isPresent()){
            throw new ValidationException(ErrorCode.USER_NOT_FOUND.name());
        }
        return ConvertUtil.toUserDetail(optionalUser.get());
    }

    public Boolean deleteUserById(Long id){
        logger.info("Request received for delete userId : {}",id);
        Optional<UserEntity> optionalUser = userRepo.findById(id);
        if(null==optionalUser || !optionalUser.isPresent()){
            throw new ValidationException(ErrorCode.USER_NOT_FOUND.name());
        }
        userRepo.deleteUser(id);
        return true;
    }

    public UserDetail addUser(UserRequest userRequest){
        if(!isValidUserAddRequest(userRequest)){
            throw new ValidationException(ErrorCode.USER_CREATE_MISSING_PARAM.name());
        }
        UserEntity userEn = userRepo.findByMobile(userRequest.getMobile());
        if(null!=userEn){
            throw new ValidationException(ErrorCode.DUPLICATE_USER.name());
        }
        UserEntity user = new UserEntity();
        user.setAge(userRequest.getAge());
        user.setName(userRequest.getName());
        user.setGender(Gender.valueOf(userRequest.getGender()));
        user.setMobile(userRequest.getMobile());
        user = userRepo.save(user);
        return ConvertUtil.toUserDetail(user);
    }

    private boolean isValidUserAddRequest(UserRequest request) {
        if (StringUtils.isBlank(request.getMobile()) ||
                request.getMobile().length()!=10 ||
                StringUtils.isBlank(request.getName()) ||
                StringUtils.isBlank(request.getGender()) ||
                null == request.getAge()) {
            return false;
        }
        return true;
    }
}
