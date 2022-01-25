package com.doublekit.pipeline.category.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.pipeline.category.model.Category;
import com.doublekit.pipeline.category.model.CategoryQuery;
import com.doublekit.pipeline.category.service.CategoryService;
import com.doublekit.common.page.Pagination;
import com.doublekit.common.Result;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * CategoryController
 */
@RestController
@RequestMapping("/category")
@Api(name = "CategoryController",desc = "知识库目录分类管理")
public class CategoryController {

    private static Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(path="/createCategory",method = RequestMethod.POST)
    @ApiMethod(name = "createCategory",desc = "创建目录")
    @ApiParam(name = "category",desc = "category",required = true)
    public Result<String> createCategory(@RequestBody @NotNull @Valid Category category){
        String id = categoryService.createCategory(category);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateCategory",method = RequestMethod.POST)
    @ApiMethod(name = "updateCategory",desc = "更新目录")
    @ApiParam(name = "category",desc = "category",required = true)
    public Result<Void> updateCategory(@RequestBody @NotNull @Valid Category category){
        categoryService.updateCategory(category);

        return Result.ok();
    }

    @RequestMapping(path="/deleteCategory",method = RequestMethod.POST)
    @ApiMethod(name = "deleteCategory",desc = "删除目录")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteCategory(@NotNull String id){
        categoryService.deleteCategory(id);

        return Result.ok();
    }

    @RequestMapping(path="/findCategory",method = RequestMethod.POST)
    @ApiMethod(name = "findCategory",desc = "根据id 查询目录")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Category> findCategory(@NotNull String id){
        Category category = categoryService.findCategory(id);

        return Result.ok(category);
    }

    @RequestMapping(path="/findCategoryDocument",method = RequestMethod.POST)
    @ApiMethod(name = "findCategoryDocument",desc = "根据id 查询下级目录文档")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<List> findCategoryDocument(@NotNull String id){
        List category = categoryService.findCategoryDocument(id);

        return Result.ok(category);
    }

    @RequestMapping(path="/findAllCategory",method = RequestMethod.POST)
    @ApiMethod(name = "findAllCategory",desc = "查询所有目录")
    public Result<List<Category>> findAllCategory(){
        List<Category> categoryList = categoryService.findAllCategory();

        return Result.ok(categoryList);
    }

    @RequestMapping(path = "/findCategoryList",method = RequestMethod.POST)
    @ApiMethod(name = "findCategoryList",desc = "条件查询目录")
    @ApiParam(name = "categoryQuery",desc = "categoryQuery",required = true)
    public Result<List<Category>> findCategoryList(@RequestBody @Valid @NotNull CategoryQuery categoryQuery){
        List<Category> categoryList = categoryService.findCategoryList(categoryQuery);

        return Result.ok(categoryList);
    }

    @RequestMapping(path = "/findCategoryListTree",method = RequestMethod.POST)
    @ApiMethod(name = "findCategoryListTree",desc = "条件查询目录树")
    @ApiParam(name = "categoryQuery",desc = "categoryQuery",required = true)
    public Result<List> findCategoryListTree(@RequestBody @Valid @NotNull CategoryQuery categoryQuery){
        List categoryListTree = categoryService.findCategoryListTree(categoryQuery);
        return Result.ok(categoryListTree);
    }

    @RequestMapping(path = "/findCategoryPage",method = RequestMethod.POST)
    @ApiMethod(name = "findCategoryPage",desc = "条件分页查询目录")
    @ApiParam(name = "categoryQuery",desc = "categoryQuery",required = true)
    public Result<Pagination<Category>> findCategoryPage(@RequestBody @Valid @NotNull CategoryQuery categoryQuery){
        Pagination<Category> pagination = categoryService.findCategoryPage(categoryQuery);

        return Result.ok(pagination);
    }

}
