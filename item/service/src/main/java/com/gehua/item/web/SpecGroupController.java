package com.gehua.item.web;

import com.gehua.common.utils.Result;
import com.gehua.item.service.SpecGroupService;
import com.gehua.pojo.SpecGroup;
import com.gehua.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /* 通用规格参数集合查询*/
    @GetMapping("params")
    public  Result queryParamsList(
            @RequestParam(value = "gid",required=false) Long gid,
            @RequestParam(value = "cid",required =false) Long cid,
            @RequestParam(value = "searching",required =false) Boolean searching
            ) {
        return specGroupService.findParamsList(gid,cid,searching);

    }

    /* 添加一个规格组*/
    @PostMapping
    public Result addGroup(@RequestBody List<SpecGroup> specGroup){
        return specGroupService.addGroup(specGroup);
    }
    /*添加一个规格参数*/
    @PostMapping("/addParam")
    public Result addParam(@RequestBody SpecParam specParam){
        return specGroupService.addParam(specParam);
    }

    /* 修改一个规格组*/
    @PutMapping
    public Result updateGroup(@RequestBody SpecGroup specGroup){
        return specGroupService.updateGroup(specGroup);
    }

    /*删除一个规格组*/
    @DeleteMapping
    public Result delGroup(@RequestBody SpecGroup specGroup){
        return specGroupService.delGroup(specGroup);
    }

}
