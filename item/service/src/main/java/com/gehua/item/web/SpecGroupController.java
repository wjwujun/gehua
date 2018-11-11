package com.gehua.item.web;

import com.gehua.item.service.SpecGroupService;
import com.gehua.pojo.SpecGroup;
import com.gehua.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
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
    * 根据组id查询参数
    * */
    @GetMapping("params")
    public  ResponseEntity<List<SpecParam>> queryParamsByGid(@PathParam("gid") Long gid){
        return ResponseEntity.ok(specGroupService.queryParamsByGid(gid));

    }


}
