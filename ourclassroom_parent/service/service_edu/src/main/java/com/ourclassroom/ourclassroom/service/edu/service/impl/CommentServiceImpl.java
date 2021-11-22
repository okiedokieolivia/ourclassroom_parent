package com.ourclassroom.ourclassroom.service.edu.service.impl;

import com.ourclassroom.ourclassroom.service.edu.entity.Comment;
import com.ourclassroom.ourclassroom.service.edu.mapper.CommentMapper;
import com.ourclassroom.ourclassroom.service.edu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
