package com.doublekit.pipeline.implement.mapper;

import com.doublekit.beans.annotation.Mapper;
import com.doublekit.pipeline.implement.entity.PipelineHistoryEntity;
import com.doublekit.pipeline.implement.model.PipelineHistory;

@Mapper(source = PipelineHistory.class,target = PipelineHistoryEntity.class)
public class PipelineHistoryMapper {
}
