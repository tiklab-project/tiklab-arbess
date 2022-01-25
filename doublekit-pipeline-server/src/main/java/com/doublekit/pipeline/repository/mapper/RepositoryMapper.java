package com.doublekit.pipeline.repository.mapper;

import com.doublekit.beans.annotation.Mapper;
import com.doublekit.pipeline.repository.entity.RepositoryEntity;
import com.doublekit.pipeline.repository.model.Repository;

@Mapper(source = Repository.class,target = RepositoryEntity.class)
public class RepositoryMapper {
}
