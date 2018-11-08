package com.gehua.pojo;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_brand")
public class Brand {
    /*
    *
    * SpringBoot的@GeneratedValue 是不需要加参数的
    * @GeneratedValue(strategy = GenerationType.IDENTITY)
    *
    * @GeneratedValue注解的strategy属性提供四种值:
    * -AUTO主键由程序控制, 是默认选项 ,不设置就是这个
    * -IDENTITY 主键由数据库生成, 采用数据库自增长, Oracle不支持这种方式
    * -SEQUENCE 通过数据库的序列产生主键, MYSQL  不支持
    * -Table 提供特定的数据库产生主键, 该方式更有利于数据库的移植
    * */
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String name;// 品牌名称
    private String image;// 品牌图片
    private Character letter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Character getLetter() {
        return letter;
    }

    public void setLetter(Character letter) {
        this.letter = letter;
    }
}