package com.gehua.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    CATEHORY_NOT_FOND(404,"商品分类未找到！"),
    BRAND_NOT_FOND(404,"品牌未找到！"),
    BRAND_SAVE_ERROR(404,"品牌新增失败！"),
    CATEHORY_BRAND_SAVE_ERROR(404,"新增品牌分类中间失败！"),
    ;
    private  int code;
    private String msg;


}
