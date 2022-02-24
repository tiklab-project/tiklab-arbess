package com.doublekit.pipeline.instance.mapper;

import com.doublekit.beans.annotation.Mapper;
import com.doublekit.pipeline.instance.entity.PipelineLogEntity;
import com.doublekit.pipeline.instance.model.PipelineLog;

@Mapper(source = PipelineLog.class,target = PipelineLogEntity.class)
public class PipelineLogMapper {
}
