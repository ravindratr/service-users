package org.service.users.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.service.users.model.ErrorCode;
import org.service.users.model.ErrorDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class ErrorUtil implements InitializingBean {
    Logger logger = LoggerFactory.getLogger(ErrorUtil.class);
    Map<String,ErrorDetail> errorMap = new HashMap<>();

    public ErrorDetail getErrorDetail(String code){
        if(StringUtils.isNoneBlank(code) && errorMap.containsKey(code)){
            return errorMap.get(code);
        }
        return errorMap.get(ErrorCode.GENERIC_ERROR.name());
    }

    @Override
    public void afterPropertiesSet(){
        try{
            InputStream stream = getClass().getClassLoader().getResourceAsStream("error.json");
            List<ErrorDetail> errors = Arrays.asList(new ObjectMapper().readValue(IOUtils.toString(stream, StandardCharsets.UTF_8), ErrorDetail[].class));
            for(ErrorDetail error:errors){
                errorMap.put(error.getCode(),error);
            }
        }catch (Exception e){
            logger.error("Error while loading json file",e);
        }
    }
}
