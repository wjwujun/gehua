package com.gehua.item.web;

import com.gehua.common.utils.Result;
import com.gehua.item.service.GoodsService;
import com.gehua.pojo.Spu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    /*
    * 根据关键字分页查询spu
    * */
    @GetMapping("/spu/page")
    public Result querySpuBbyPage(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable",required = false) Boolean saleable,
            @RequestParam(value = "key",required = false) String key
    ){
        return goodsService.querySpuBbyPage(page,rows,saleable,key);
    }


    /*
    *
    * 商品新增
    * */
    @PostMapping("goods")
    public ResponseEntity<HttpStatus> saveGoods(@RequestBody Spu spu){

        goodsService.saveGoods(spu);
        return  ResponseEntity.ok(HttpStatus.CREATED);
    }

    /*
     *
     * 商品修改
     * */
    @PutMapping("goods")
    public ResponseEntity<HttpStatus> updateGoods(@RequestBody Spu spu){

        goodsService.updateGoods(spu);
        return  ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    /*
    *根据spu的id查询详情detail
    *
    * */

    @GetMapping("/spu/detail/{id}")
    public Result queryDetailById(@PathParam("id") Long spuId){
        return goodsService.queryDetailById(spuId);
    }

    /*
    * 根据spu查询下面所有的sku
    * */
    @GetMapping("sku/list")
    public Result  querySkuBySpuId(@RequestParam("id") Long spuId){
            return goodsService.querySkuBySpuId(spuId);
    }


}
