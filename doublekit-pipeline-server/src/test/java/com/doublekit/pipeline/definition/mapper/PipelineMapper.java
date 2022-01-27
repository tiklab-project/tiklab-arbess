package com.doublekit.pipeline.definition.mapper;

import com.doublekit.beans.annotation.Mapper;
import com.doublekit.pipeline.definition.entity.PipelineEntity;
import com.doublekit.pipeline.definition.model.Pipeline;

@Mapper(source = Pipeline.class ,target = PipelineEntity.class)
public class PipelineMapper {
}
