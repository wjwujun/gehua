package com.gehua.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    PRICE_CNANOT_NE_NULL(400,"价格不能为空!"),
    CATEHORY_NOT_FOND(404,"商品分类未找到！"),
    ;
    private  int code;
    private String msg;
}
