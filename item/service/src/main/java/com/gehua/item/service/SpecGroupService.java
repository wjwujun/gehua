package com.gehua.item.service;


import com.gehua.common.utils.Result;
import com.gehua.common.utils.StatusCode;
import com.gehua.item.mapper.SpecGroupMapper;
import com.gehua.item.mapper.SpecParampMapper;
import com.gehua.pojo.SpecGroup;
import com.gehua.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import java.util.List;

@Service
public class SpecGroupService {

    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParampMapper specParampMapper;

    public Result findGroupByCid(Long cid) {
        SpecGroup group = new SpecGroup();
        group.setCid(cid);
        List<SpecGroup> list = specGroupMapper.select(group);

        if(CollectionUtils.isEmpty(list)){
            return new Result(false,StatusCode.SPEC_GROUP_NOT_FOND,"商品规格组没有查到");
        }
        return new Result(false,StatusCode.OK,"成功",list);

    }

    public  Result findParamsList(Long gid, Long cid, Boolean searching) {
        SpecParam param = new SpecParam();
        param.setGroupId(gid);
        param.setCid(cid);
        param.setSearching(searching);


        List<SpecParam> list = specParampMapper.select(param);
        if(CollectionUtils.isEmpty(list)){
            return new Result(false,StatusCode.SPEC_PARAM_NOT_FOND,"商品规格参数没有查到");
        }

        return new Result(false,StatusCode.OK,"成功",list);
    }


}
