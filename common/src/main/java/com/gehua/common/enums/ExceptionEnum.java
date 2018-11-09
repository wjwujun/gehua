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
    BRAND_SAVE_ERROR(400,"品牌新增失败！"),
    CATEHORY_BRAND_SAVE_ERROR(400,"新增品牌分类中间失败！"),
    UPLOAD_ERROR(400,"文件上传失败！"),
    INVALID_FILE_TYPE(400,"无效的文件类型！"),
    ;

    private  int code;
    private String msg;


}
