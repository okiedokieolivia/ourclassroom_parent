package com.ourclassroom.ourclassroom.service.transaction.service.impl;

import com.ourclassroom.ourclassroom.service.transaction.entity.Log;
import com.ourclassroom.ourclassroom.service.transaction.mapper.LogMapper;
import com.ourclassroom.ourclassroom.service.transaction.service.LogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

}
