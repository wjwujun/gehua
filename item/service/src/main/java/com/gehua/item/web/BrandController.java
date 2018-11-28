package com.gehua.item.web;

import com.gehua.common.utils.Result;
import com.gehua.item.service.BrandService;
import com.gehua.pojo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /*分页查询*/
    @GetMapping("page")
    public Result pageBrand(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy",required = false) String sortBy,
            @RequestParam(value = "desc",defaultValue = "false") Boolean desc,
            @RequestParam(value = "key",required = false) String key
    ){

        return brandService.pageQuery(page,rows,sortBy,desc,key);
    }

    /*
    * 新增
    * */
    @PostMapping
    public  Result saveBrand(@RequestBody Brand brand){
        return  brandService.add(brand);
    }


    /*删除*/
    @DeleteMapping
    public  Result delBrand(@RequestBody Brand brand){
        return  brandService.del(brand);
    }
    /*修改*/
    @PutMapping
    public  Result updateBrand(@RequestBody Brand brand){
        return  brandService.update(brand);
    }

    /*
    * 查询分类下的所有品牌
    * */
    @GetMapping("/cid/{cid}")
    public Result queryBrandById(@PathVariable("cid") Long cid){
        return  brandService.findByCid(cid);

    }

}
