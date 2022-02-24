package com.doublekit.pipeline.instance.mapper;

import com.doublekit.beans.annotation.Mapper;
import com.doublekit.pipeline.instance.entity.PipelineHistoryEntity;
import com.doublekit.pipeline.instance.model.PipelineHistory;

@Mapper(source = PipelineHistory.class,target = PipelineHistoryEntity.class)
public class PipelineHistoryMapper {
}
