package com.gehua.item.web;

import com.gehua.item.service.SpecGroupService;
import com.gehua.pojo.SpecGroup;
import com.gehua.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<SpecGroup>> queryGroupByCid(@PathVariable("cid") Long cid){

        return  ResponseEntity.ok(specGroupService.queryGroupByCid(cid));
    }

    /*
    * 通用规格参数集合查询
    * */
    @GetMapping("params")
    public  ResponseEntity<List<SpecParam>> queryParamsList(
            @RequestParam(value = "gid",required=false) Long gid,
            @RequestParam(value = "cid",required =false) Long cid,
            @RequestParam(value = "searching",required =false) Boolean searching
            ) {
        return ResponseEntity.ok(specGroupService.queryParamsList(gid,cid,searching));

    }


}
