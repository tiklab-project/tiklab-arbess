package com.doublekit.pipeline.document.mapper;

import com.doublekit.beans.annotation.Mapper;
import com.doublekit.pipeline.document.entity.DocumentEntity;
import com.doublekit.pipeline.document.model.Document;

@Mapper(source = Document.class,target = DocumentEntity.class)
public class DocumentMapper {
}
