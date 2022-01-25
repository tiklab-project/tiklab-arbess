package com.doublekit.pipeline.document.mapper;

import com.doublekit.beans.annotation.Mapper;
import com.doublekit.pipeline.document.entity.DocumentAttachEntity;
import com.doublekit.pipeline.document.model.DocumentAttach;

@Mapper(source = DocumentAttach.class,target = DocumentAttachEntity.class)
public class DocumentAttachMapper {
}
