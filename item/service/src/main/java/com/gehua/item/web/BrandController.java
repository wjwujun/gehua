package com.gehua.item.web;

import com.gehua.common.vo.PageResult;
import com.gehua.item.service.BrandService;
import com.gehua.pojo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /*
    * 分页查询
    * */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> querybrandByPage(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy",required = false) String sortBy,
            @RequestParam(value = "desc",defaultValue = "false") Boolean desc,
            @RequestParam(value = "key",required = false) String key
    ){

        PageResult<Brand>  result=brandService.queryBrandByPage(page,rows,sortBy,desc,key);
        return  ResponseEntity.ok(result);

    }

    @PostMapping
    public  ResponseEntity<Void> saveBrand(Brand brand,@RequestParam("cids") List<Long> cids){
        brandService.saveBrand(brand,cids);

        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }



}
