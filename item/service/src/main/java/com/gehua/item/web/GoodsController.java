package com.gehua.item.web;

import com.gehua.common.vo.PageResult;
import com.gehua.item.service.GoodsService;
import com.gehua.pojo.Spu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<Spu>> querySpuBbyPage(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable",required = false) Boolean saleable,
            @RequestParam(value = "key",required = false) String key
    ){
        return ResponseEntity.ok(goodsService.querySpuBbyPage(page,rows,saleable,key));
    }


}
