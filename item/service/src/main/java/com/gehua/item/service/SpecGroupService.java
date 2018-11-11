package com.gehua.item.service;

import com.gehua.common.enums.ExceptionEnum;
import com.gehua.common.exception.GehuaException;
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

    public List<SpecGroup> queryGroupByCid(Long cid) {
        SpecGroup group = new SpecGroup();
        group.setCid(cid);
        List<SpecGroup> list = specGroupMapper.select(group);

        if(CollectionUtils.isEmpty(list)){
            throw new GehuaException(ExceptionEnum.SPEC_GROUP_NOT_FOND);
        }

        return  list;
    }

    public  List<SpecParam> queryParamsByGid(Long gid) {
        SpecParam param = new SpecParam();
        param.setGroupId(gid);
        List<SpecParam> list = specParampMapper.select(param);
        if(CollectionUtils.isEmpty(list)){
            throw new GehuaException(ExceptionEnum.SPEC_PARAM_NOT_FOND);
        }

        return list;
    }
}
