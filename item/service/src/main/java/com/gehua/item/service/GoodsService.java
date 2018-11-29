package com.gehua.item.service;


import com.gehua.common.utils.PageResult;
import com.gehua.common.utils.Result;
import com.gehua.common.utils.StatusCode;
import com.gehua.item.mapper.SkuMapper;
import com.gehua.item.mapper.SpuDetailMapper;
import com.gehua.item.mapper.SpuMapper;
import com.gehua.item.mapper.StockMapper;
import com.gehua.pojo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
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

    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;


    //注入mq模板
    @Autowired
    private AmqpTemplate amqpTemplate;

    /*商品分页获取*/
    public Result pageQuery(Integer page, Integer rows, Boolean saleable, String key) {
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
            return new Result(false,StatusCode.QUERRY_NOT_FOND,"商品不存在");
        }

        //解析分类和品牌的名称
        loadCategoryAndBrandName(spus);
        //解析分页结果
        PageInfo<Spu> info = new PageInfo<>(spus);
        return new Result(false,StatusCode.OK,"成功",new PageResult<>(info.getTotal(),spus));

    }

    /*获取商品分页中每个商品的分类名和品牌名*/
    private void loadCategoryAndBrandName(List<Spu> spus) {
        for (Spu spu : spus) {
            //处理分类名称
            List<String> names = categoryService.findByIds(Arrays.asList(spu.getCid1(), spu.getCid2()
                    , spu.getCid3())).stream().map(Category::getName).collect(Collectors.toList());

            spu.setCname(StringUtils.join(names,"/"));
            //处理品牌名称
            spu.setBname(brandService.findById(spu.getBrandId()).getName());

        }
    }

    /*添加一个商品*/
    public Result save(Spu spu) {
        System.out.println(spu);

        //新增spu
        spu.setId(null);
        spu.setSaleable(true);
        spu.setValid(true);
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        int count = this.spuMapper.insertSelective(spu);
        if (count!=1){
            return new Result(false,StatusCode.ADD_ERROR,"新增商品失败");
        }
        System.out.println(spu.getId());
        //新增spuDetail
        SpuDetail spuDetail = spu.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        spuDetailMapper.insert(spuDetail);

        //定义库存集合
        List<Stock> stockList=new ArrayList<>();

        //新增sku
        List<Sku> skus = spu.getSkus();
        for (Sku sku : skus) {
                sku.setCreateTime(new Date());
                sku.setLastUpdateTime(sku.getCreateTime());
                sku.setSpuId(spu.getId());

             count = skuMapper.insert(sku);
            if (count!=1){
                return new Result(false,StatusCode.ADD_ERROR,"新增商品失败");
            }
            //新增库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());

            stockList.add(stock);
        }
        //批量新增库存
        count=stockMapper.insertList(stockList);
        if (count!=stockList.size()){
            return new Result(false,StatusCode.ADD_ERROR,"新增商品失败");
        }


        //发送mq消息
        amqpTemplate.convertAndSend("item.insert",spu.getId());


        return new Result(false,StatusCode.OK,"新增商品成功");


    }

    /*根据spuId查询详情spu_detail    */
    public Result findDetailById(Long spuId) {
        SpuDetail spuDetail = spuDetailMapper.selectByPrimaryKey(spuId);

        if (spuDetail==null){
            return new Result(false,StatusCode.QUERRY_NOT_FOND,"商品详情不存在");
        }
        return new Result(false,StatusCode.OK,"成功",spuDetail);
    }

    /*根据spu查询下面所有的sku*/
    public Result findSku(Long spuId) {
        //查询sku
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skuList = skuMapper.select(sku);
        if(CollectionUtils.isEmpty(skuList)){
            return new Result(false,StatusCode.QUERRY_NOT_FOND,"商品SKu不存在");
        }

        //查询库存
        /*for (Sku s : list) {
            Stock stock = stockMapper.selectByPrimaryKey(s.getId());
            if (stock==null){
                throw new GehuaException(ExceptionEnum.GOODS_STOCK_NOT_FOND);
            }
            s.setStock(stock.getStock());
        }*/
        //批量查询
        List<Long> ids = skuList.stream().map(Sku::getId).collect(Collectors.toList());
        List<Stock> stockList = stockMapper.selectByIdList(ids);

        //把stock变一个map,其中key是skuid，value是库存
        Map<Long,Integer> stockMap=stockList.stream().collect(Collectors.toMap(Stock::getSkuId,Stock::getStock));
        skuList.forEach(s->s.setStock(stockMap.get(s.getId())));

        return new Result(false,StatusCode.OK,"成功",skuList);
    }

    /*修改spu*/
    @Transactional
    public Result updateSpu(Spu spu) {
        //修改spu
        spu.setValid(null);
        spu.setSaleable(null);
        spu.setLastUpdateTime(new Date());
        spu.setCreateTime(null);
        int count = spuMapper.updateByPrimaryKeySelective(spu);
        if(count!=1){
            return new Result(false,StatusCode.UPDATE_ERROR,"商品spu修改失败");
        }

        //发送mq消息
//        amqpTemplate.convertAndSend("item.update",spu.getId());
        return new Result(false,StatusCode.OK,"商品spu修改成功",spu);

    }


    /*修改sku和库存*/
    @Transactional
    public Result updateSku(Sku sku) {

        sku.setLastUpdateTime(new Date());
        sku.setCreateTime(null);
        int i = skuMapper.updateByPrimaryKeySelective(sku);

        if(i!=1){
            return new Result(false,StatusCode.UPDATE_ERROR,"商品sku修改失败");
        }
        Stock stock = new Stock();
        stock.setStock(sku.getStock());
        stock.setSkuId(sku.getId());
        stockMapper.updateByPrimaryKeySelective(stock);

        //发送mq消息
//        amqpTemplate.convertAndSend("item.update",spu.getId());
        return new Result(false,StatusCode.OK,"商品sku修改成功",sku);

    }

    /*修改spuDetail*/
    @Transactional
    public Result updateSpuDetail(SpuDetail spuDetail) {

        int i = spuDetailMapper.updateByPrimaryKeySelective(spuDetail);
        if(i!=1){
            return new Result(false,StatusCode.UPDATE_ERROR,"商品spuDetail修改失败");
        }

        //发送mq消息
//        amqpTemplate.convertAndSend("item.update",spu.getId());
        return new Result(false,StatusCode.OK,"商品spuDetail修改成功",spuDetail);

    }




}

