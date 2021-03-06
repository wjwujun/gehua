package com.gehua.item.web;

import com.gehua.common.utils.Result;
import com.gehua.item.service.GoodsService;
import com.gehua.pojo.Sku;
import com.gehua.pojo.Spu;
import com.gehua.pojo.SpuDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spu")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    /*根据关键字分页查询spu* */
    @GetMapping("/page")
    public Result querySpuBbyPage(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable",required = false) Boolean saleable,
            @RequestParam(value = "key",required = false) String key
    ){
        return goodsService.pageQuery(page,rows,saleable,key);
    }


    /*商品新增* */
    @PostMapping("/goods")
    public Result saveGoods(@RequestBody Spu spu){
        return goodsService.save(spu);
    }

    /* 修改spu*/
    @PutMapping("/goods/spu")
    public Result updateSpu(@RequestBody Spu spu){
        return   goodsService.updateSpu(spu);
    }

    /* 修改spuDetail*/
    @PutMapping("/goods/spuDetail")
    public Result updateGoods(@RequestBody SpuDetail spuDetail){
        return   goodsService.updateSpuDetail(spuDetail);
    }
    /* 修改sku*/
    @PutMapping("/goods/sku")
    public Result updateSku(@RequestBody Sku sku){
        return   goodsService.updateSku(sku);
    }





    /*根据spuId查询详情spu_detail */
    @GetMapping("/detail/{spuId}")
    public Result queryDetailById(@PathVariable("spuId") Long spuId){
        return goodsService.findDetailById(spuId);
    }

    /*根据spu查询下面所有的sku* */
    @GetMapping("/sku/list")
    public Result  querySkuBySpuId(@RequestParam("id") Long spuId){
        return goodsService.findSku(spuId);
    }


}
