package com.ourclassroom.ourclassroom.service.base.handler;

import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.base.result.ResultCodeEnum;
import com.ourclassroom.ourclassroom.base.util.ExceptionUtils;
import com.ourclassroom.ourclassroom.service.base.exception.OurclassroomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        log.error(ExceptionUtils.getMessage(e));
        return R.error();
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    public R error(BadSqlGrammarException e) {
        log.error(ExceptionUtils.getMessage(e));
        return R.setResult(ResultCodeEnum.BAD_SQL_GRAMMAR);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    public R error(DuplicateKeyException e) {
        log.error(ExceptionUtils.getMessage(e));
        return R.setResult(ResultCodeEnum.PARAM_ERROR);
    }

    @ExceptionHandler(OurclassroomException.class)
    @ResponseBody
    public R error(OurclassroomException e) {
        log.error(ExceptionUtils.getMessage(e));
        // display customized error message
        return R.error().message(e.getMessage()).code(e.getCode());
    }
}
