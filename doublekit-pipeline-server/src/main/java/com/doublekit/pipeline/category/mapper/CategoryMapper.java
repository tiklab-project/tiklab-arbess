package com.doublekit.pipeline.category.mapper;

import com.doublekit.beans.annotation.Mapper;
import com.doublekit.pipeline.category.entity.CategoryEntity;
import com.doublekit.pipeline.category.model.Category;

@Mapper(source = Category.class,target = CategoryEntity.class)
public class CategoryMapper {
}
