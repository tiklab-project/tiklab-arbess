package com.doublekit.pipeline.implement.mapper;

import com.doublekit.beans.annotation.Mapper;
import com.doublekit.pipeline.implement.entity.PipelineLogEntity;
import com.doublekit.pipeline.implement.model.PipelineLog;

@Mapper(source = PipelineLog.class,target = PipelineLogEntity.class)
public class PipelineLogMapper {
}
