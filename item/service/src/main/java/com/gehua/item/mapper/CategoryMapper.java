package com.gehua.item.mapper;

import com.gehua.pojo.Category;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface CategoryMapper extends Mapper<Category>,IdListMapper<Category,Long> {
}
