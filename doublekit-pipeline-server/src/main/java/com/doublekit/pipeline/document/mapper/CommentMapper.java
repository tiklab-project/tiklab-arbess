package com.doublekit.pipeline.document.mapper;

import com.doublekit.beans.annotation.Mapper;
import com.doublekit.pipeline.document.entity.CommentEntity;
import com.doublekit.pipeline.document.model.Comment;

@Mapper(source = Comment.class,target = CommentEntity.class)
public class CommentMapper {
}
