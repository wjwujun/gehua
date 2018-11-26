package com.gehua.item.web;

import com.gehua.common.utils.Result;
import com.gehua.item.service.SpecGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("spec")
public class SpecGroupController {

    @Autowired
    private SpecGroupService specGroupService;

    /*
    * 根据分类id查询规格组
    * */
    @GetMapping("groups/{cid}")
    public Result queryGroupByCid(@PathVariable("cid") Long cid){

        return  specGroupService.findGroupByCid(cid);
    }

    /*
    * 通用规格参数集合查询
    * */
    @GetMapping("params")
    public  Result queryParamsList(
            @RequestParam(value = "gid",required=false) Long gid,
            @RequestParam(value = "cid",required =false) Long cid,
            @RequestParam(value = "searching",required =false) Boolean searching
            ) {
        return specGroupService.findParamsList(gid,cid,searching);

    }


}
