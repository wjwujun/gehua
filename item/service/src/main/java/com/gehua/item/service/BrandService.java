package com.gehua.item.service;


import com.gehua.common.utils.PageResult;
import com.gehua.common.utils.Result;
import com.gehua.common.utils.StatusCode;
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

    public Result queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
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
            return new Result(false,StatusCode.BRAND_NOT_FOND,"品牌未找到");
        }
        //解析分页结果
        PageInfo<Brand> info = new PageInfo<>(list);
        return new Result(false,StatusCode.OK,"成功",new PageResult<>(info.getTotal(),list));

    }



    /*@Transactional 事物*/
    @Transactional
    public Result saveBrand(Brand brand, List<Long> cids) {
        //新增
        int count=brandMapper.insert(brand);
        if(count==0){
            return new Result(false,StatusCode.BRAND_SAVE_ERROR,"品牌新增失败");
        }
        //新增中间表
        for(Long cid : cids){
            count=brandMapper.insertCategorybrand(cid,brand.getId());
            if(count!=1){
                return new Result(false,StatusCode.CATEHORY_BRAND_SAVE_ERROR,"新增品牌分类中间失败");
            }
        }
        return new Result(false,StatusCode.OK,"新增品牌分类中间失败");
    }


    public Brand queryById(Long id){
        Brand brand = brandMapper.selectByPrimaryKey(id);
        if (brand==null){

            return new Brand();
        }
        return brand;

    }

    /*
     * 查询分类下的所有品牌
     * */
    public Result queryBrandById(Long cid) {
        List<Brand> list = brandMapper.queryByCategoryId(cid);
        if(CollectionUtils.isEmpty(list)){
            return new Result(false,StatusCode.BRAND_NOT_FOND,"品牌未找到");
        }
        return new Result(false,StatusCode.OK,"成功",list);
    }
}
