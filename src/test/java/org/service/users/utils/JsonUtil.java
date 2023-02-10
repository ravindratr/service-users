package org.service.users.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.service.users.model.ErrorDetail;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class JsonUtil {

    public static <T> T mapFromJson(String json, Class<T> clazz)
            throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    public static String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    public static String getResourceDataAsString(String filePath){
        try {
            InputStream stream = JsonUtil.class.getClassLoader().getResourceAsStream(filePath);
            return IOUtils.toString(stream, StandardCharsets.UTF_8);
        } catch (Exception e){
            return "";
        }
    }
}
