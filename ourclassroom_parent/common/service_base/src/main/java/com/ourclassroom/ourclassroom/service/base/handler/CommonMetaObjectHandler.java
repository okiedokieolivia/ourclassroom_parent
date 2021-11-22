package com.ourclassroom.ourclassroom.service.base.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;


@Component
public class CommonMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        this.setFieldValByName("gmtCreate", new Timestamp(new Date().getTime()), metaObject);
        this.setFieldValByName("gmtModified", new Timestamp(new Date().getTime()), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified", new Timestamp(new Date().getTime()), metaObject);
    }
}