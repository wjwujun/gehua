package com.gehua.item.service;


import com.gehua.common.enums.ExceptionEnum;
import com.gehua.common.exception.GehuaException;
import com.gehua.item.mapper.CategoryMapper;
import com.gehua.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> queryCategoryListByPid(Long pid) {
        Category t = new Category();
        t.setParentId(pid);
        //select将t对象中的非空字段,作为查询对象
        List<Category> list = categoryMapper.select(t);
        if (CollectionUtils.isEmpty(list)){
            throw new GehuaException(ExceptionEnum.CATEHORY_NOT_FOND);
        }
        return list;
    }
}
