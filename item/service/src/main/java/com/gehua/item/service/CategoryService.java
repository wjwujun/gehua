package com.gehua.item.service;


import com.gehua.common.utils.Result;
import com.gehua.common.utils.StatusCode;
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

    public Result queryCategoryListByPid(Long pid) {
        Category t = new Category();
        t.setParentId(pid);
        //select将t对象中的非空字段,作为查询对象
        List<Category> list = categoryMapper.select(t);
        if (CollectionUtils.isEmpty(list)){
            return new Result(false,StatusCode.CATEHORY_NOT_FOND,"数据为空");
        }
        return new Result(false,StatusCode.OK,"成功",list);
    }


    /*
    * 通过分类查id
    * */
    public List<Category> queryByIds(List<Long> ids){
        List<Category> list = categoryMapper.selectByIdList(ids);
        return list;
    }

}
