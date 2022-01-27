package com.doublekit.pipeline.definition.mapper;

import com.doublekit.beans.annotation.Mapper;
import com.doublekit.pipeline.definition.entity.PipelineConfigureEntity;
import com.doublekit.pipeline.definition.model.PipelineConfigure;

@Mapper(source = PipelineConfigure.class,target = PipelineConfigureEntity.class)
public class PipelineConfigureMapper {
}
