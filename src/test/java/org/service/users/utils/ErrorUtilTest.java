package org.service.users.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.service.users.model.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {ErrorUtil.class})
class ErrorUtilTest {

    @Autowired
    private ErrorUtil errorUtil;

    @Test
    public void populateData() {
        Assertions.assertEquals(500,errorUtil.getErrorDetail(ErrorCode.GENERIC_ERROR.name()).getHttpStatus());
        Assertions.assertEquals(500,errorUtil.getErrorDetail(null).getHttpStatus());
        Assertions.assertEquals(404,errorUtil.getErrorDetail(ErrorCode.USER_NOT_FOUND.name()).getHttpStatus());
        Assertions.assertEquals("Missing mandatory params",errorUtil.getErrorDetail(ErrorCode.USER_CREATE_MISSING_PARAM.name()).getMessage());
    }
}