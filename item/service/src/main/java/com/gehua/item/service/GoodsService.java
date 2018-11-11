package com.gehua.item.service;


import com.gehua.common.enums.ExceptionEnum;
import com.gehua.common.exception.GehuaException;
import com.gehua.common.vo.PageResult;
import com.gehua.item.mapper.SpuDetailMapper;
import com.gehua.item.mapper.SpuMapper;
import com.gehua.pojo.Category;
import com.gehua.pojo.Spu;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class GoodsService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;


    public PageResult<Spu> querySpuBbyPage(Integer page, Integer rows, Boolean saleable, String key) {
        //分页
        PageHelper.startPage(page,rows);
        //过滤
        Example example = new Example(Spu.class);
         Example.Criteria criteria = example.createCriteria();
        //搜索条件过滤
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("title","%"+key+"%");
        }
        // 上下架过滤
        if(saleable !=null){
            criteria.andEqualTo("saleable",saleable);
        }
        //默认排序
        example.setOrderByClause("last_update_time desc");

        //查询
        List<Spu> spus = spuMapper.selectByExample(example);

        if(CollectionUtils.isEmpty(spus)){
            throw new GehuaException(ExceptionEnum.GOODS_NOT_FOND);
        }

        //解析分类和品牌的名称
        loadCategoryAndBrandName(spus);
        //解析分页结果
        PageInfo<Spu> info = new PageInfo<>(spus);

        return new PageResult<>(info.getTotal(),spus);
    }

    private void loadCategoryAndBrandName(List<Spu> spus) {
        for (Spu spu : spus) {
            //处理分类名称
            List<String> names = categoryService.queryByIds(Arrays.asList(spu.getCid1(), spu.getCid2()
                    , spu.getCid3())).stream().map(Category::getName).collect(Collectors.toList());
            spu.setCname(StringUtils.join(names,"/"));
            //处理品牌名称
            spu.setBname(brandService.queryById(spu.getBrandId()).getName());

        }
    }
}

