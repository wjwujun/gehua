package com.gehua.pojo;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.security.PrivateKey;

@Table(name="tb_spec_group")
public class SpecGroup {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long cid;

    private String name;



    public void setId(Long id) {
        this.id = id;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Long getCid() {
        return cid;
    }

    public String getName() {
        return name;
    }







}
