package com.doublekit.pipeline.document.mapper;

import com.doublekit.beans.annotation.Mapper;
import com.doublekit.pipeline.document.entity.LikeEntity;
import com.doublekit.pipeline.document.model.Like;

@Mapper(source = Like.class,target = LikeEntity.class)
public class LikeMapper {
}
