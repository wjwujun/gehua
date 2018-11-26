package com.gehua.item.web;

import com.gehua.common.utils.Result;
import com.gehua.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("list")
    public Result queryCategoryListByPid(@RequestParam("pid") Long pid){

        return  categoryService.queryCategoryListByPid(pid);

    }

}
