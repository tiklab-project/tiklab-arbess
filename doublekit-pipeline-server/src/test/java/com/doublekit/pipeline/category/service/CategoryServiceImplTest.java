package com.doublekit.pipeline.category.service;

import com.doublekit.pipeline.category.model.Category;
import com.doublekit.apibox.client.mock.JMockit;
import com.doublekit.pipeline.config.TestConfig;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Transactional
@Rollback(false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CategoryServiceImplTest {

    private static Logger logger = LoggerFactory.getLogger(CategoryServiceImplTest.class);

    @Autowired
    CategoryService categoryService;

    static String id;

    @Test
    public void test01ForSaveCategory() {
        Category category = JMockit.mock(Category.class);

        id = categoryService.createCategory(category);

        assertNotNull(id);
    }

    @Test
    public void test02ForUpdateCategory(){
        Category category = JMockit.mock(Category.class);
        category.setId(id);

        categoryService.updateCategory(category);
    }

    @Test
    public void test03ForFindCategory() {
        Category category = categoryService.findCategory(id);

        assertNotNull(category);
    }

    @Test
    public void test04ForFindAllCategory() {
        List<Category> categoryList = categoryService.findAllCategory();

        assertNotNull(categoryList);
    }

    @Test
    public void test05ForDeleteCategory(){
        categoryService.deleteCategory(id);
    }
}