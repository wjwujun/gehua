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
    ;
    private  int code;
    private String msg;
}
