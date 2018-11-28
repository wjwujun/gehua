package com.gehua.item.service;


import com.gehua.common.utils.Result;
import com.gehua.common.utils.StatusCode;
import com.gehua.item.mapper.SpecGroupMapper;
import com.gehua.item.mapper.SpecParampMapper;
import com.gehua.pojo.SpecGroup;
import com.gehua.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SpecGroupService {

    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParampMapper specParampMapper;
    /*
     * 根据分类id查询规格组
     * */
    public Result findGroupByCid(Long cid) {
        SpecGroup group = new SpecGroup();
        group.setCid(cid);
        List<SpecGroup> list = specGroupMapper.select(group);

        if(CollectionUtils.isEmpty(list)){
            return new Result(false,StatusCode.DATA_IS_EMPTY,"商品规格组没有查到");
        }
        return new Result(false,StatusCode.OK,"成功",list);

    }

    /*查询规格参数*/
    public  Result findParamsList(Long gid, Long cid, Boolean searching) {
        SpecParam param = new SpecParam();
        param.setGroupId(gid);
        param.setCid(cid);
        param.setSearching(searching);


        List<SpecParam> list = specParampMapper.select(param);
        if(CollectionUtils.isEmpty(list)){
            return new Result(false,StatusCode.DATA_IS_EMPTY,"商品规格参数没有查到");
        }

        return new Result(false,StatusCode.OK,"成功",list);
    }

    /*添加一个规格组*/
    @Transactional
    public Result addGroup(List<SpecGroup> specGroup) {

        int count=specGroupMapper.insertList(specGroup);
        if(count==0){
            return new Result(false,StatusCode.ADD_ERROR,"商品规格组添加失败");
        }
        return new Result(false,StatusCode.OK,"添加成功",specGroup);
    }

    /*修改规格组*/
    public Result updateGroup(SpecGroup specGroup) {
        int count=specGroupMapper.updateByPrimaryKeySelective(specGroup);

        if(count==0){
            return new Result(false,StatusCode.ADD_ERROR,"商品规格组修改失败");
        }
        return new Result(false,StatusCode.OK,"修改成功",specGroup);
    }
    /*删除一个规格组*/
    public Result delGroup(SpecGroup specGroup) {
        int count=specGroupMapper.delete(specGroup);

        if(count==0){
            return new Result(false,StatusCode.ADD_ERROR,"商品规格组删除失败");
        }
        return new Result(false,StatusCode.OK,"删除成功",specGroup);
    }

    /*添加一个规格参数*/
    public Result addParam(SpecParam specParam) {
        int count = specParampMapper.insertSelective(specParam);
        if(count==0){
            return new Result(false,StatusCode.ADD_ERROR,"商品规格参数添加失败");
        }
        return new Result(false,StatusCode.OK,"添加成功",specParam);

    }
}
