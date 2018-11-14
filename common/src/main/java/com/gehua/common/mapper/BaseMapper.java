package com.gehua.common.mapper;



import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;        //注意不要引用成common
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;
/*
* 通用基础mapper
* */
@RegisterMapper
public interface BaseMapper<T> extends Mapper<T>,IdListMapper<T,Long>,InsertListMapper<T> {
}
