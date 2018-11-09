package com.gehua.item.service;


import com.gehua.common.enums.ExceptionEnum;
import com.gehua.common.exception.GehuaException;
import com.gehua.common.vo.PageResult;
import com.gehua.item.mapper.BrandMapper;
import com.gehua.pojo.Brand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandMapper brandMapper;

    public PageResult<Brand> queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //分页
        PageHelper.startPage(page,rows);
        //过滤
        Example example = new Example(Brand.class);
        if(StringUtils.isNotBlank(key)){
            //过滤条件
            example.createCriteria().orLike("name","%"+key+"%").orEqualTo("letter",key.toUpperCase());
        }
        //排序
         if (StringUtils.isNotBlank(sortBy)){
             String orderByClause=sortBy+(desc ? " desc" : " asc");
             example.setOrderByClause(orderByClause);
         }
        //查询
        List<Brand> list = this.brandMapper.selectByExample(example);

        if(CollectionUtils.isEmpty(list)){
            throw new GehuaException(ExceptionEnum.BRAND_NOT_FOND);
        }
        //解析分页结果
        PageInfo<Brand> info = new PageInfo<>(list);
        return new PageResult<>(info.getTotal(),list);
    }

    /*@Transactional 事物*/
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        //新增
        int count=brandMapper.insert(brand);
        if(count==0){
            throw  new GehuaException(ExceptionEnum.BRAND_SAVE_ERROR);
        }
        //新增中间表
        for(Long cid : cids){
            count=brandMapper.insertCategorybrand(cid,brand.getId());
            if(count!=1){
                throw  new GehuaException(ExceptionEnum.CATEHORY_BRAND_SAVE_ERROR);
            }
        }
    }


}
