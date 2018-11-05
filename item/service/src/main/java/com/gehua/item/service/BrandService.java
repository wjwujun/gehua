package com.gehua.item.service;


import com.gehua.common.enums.ExceptionEnum;
import com.gehua.common.exception.GehuaException;
import com.gehua.common.vo.PageResult;
import com.gehua.item.mapper.BrandMapper;
import com.gehua.item.mapper.CategoryMapper;
import com.gehua.pojo.Brand;
import com.gehua.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandMapper brandMapper;

    public PageResult<Brand> queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //分页

        //过滤

        //排序

        //查询

        return null;
    }
}
