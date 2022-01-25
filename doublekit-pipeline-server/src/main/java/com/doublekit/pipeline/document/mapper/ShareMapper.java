package com.doublekit.pipeline.document.mapper;

import com.doublekit.beans.annotation.Mapper;
import com.doublekit.pipeline.document.entity.ShareEntity;
import com.doublekit.pipeline.document.model.Share;

@Mapper(source = Share.class,target = ShareEntity.class)
public class ShareMapper {
}
