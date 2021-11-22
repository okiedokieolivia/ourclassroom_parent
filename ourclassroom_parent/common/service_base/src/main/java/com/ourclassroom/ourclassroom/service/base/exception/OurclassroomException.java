package com.ourclassroom.ourclassroom.service.base.exception;

import com.ourclassroom.ourclassroom.base.result.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
//@EqualsAndHashCode(callSuper = true)
public class OurclassroomException extends RuntimeException {
    private Integer code;

    public OurclassroomException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public OurclassroomException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }
}
