package com.doublekit.pipeline.document.mapper;

import com.doublekit.beans.annotation.Mapper;
import com.doublekit.pipeline.document.entity.DocumentTemplateEntity;
import com.doublekit.pipeline.document.model.DocumentTemplate;

@Mapper(source = DocumentTemplate.class,target = DocumentTemplateEntity.class)
public class DocumentTemplateMapper {
}
