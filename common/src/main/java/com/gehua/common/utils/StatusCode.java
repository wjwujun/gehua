package com.gehua.common.utils;


/*
* 返回code
* */
public class StatusCode {
    public static final int OK=200;//成功
    public static final int ERROR =201;//失败
    public static final int LOGINERROR =202;//用户名或密码错误
    public static final int ACCESSERROR =203;//权限不足
    public static final int REMOTEERROR =204;//远程调用失败
    public static final int REPERROR =205;//重复操作
    public static final int INVALID_FILE_TYPE=400; //无效的文件类型!
    public static final int GOODS_UPDATE_ERROR=400; //更新商品失败!
    public static final int BRAND_SAVE_ERROR=400; //品牌新增失败!
    public static final int CATEHORY_BRAND_SAVE_ERROR=400; //新增品牌分类中间失败!
    public static final int UPLOAD_ERROR=400; //文件上传失败!
    public static final int SAVE_GOODS_ERROR=400; //新增商品失败!

    public static final int CATEHORY_NOT_FOND=404; //商品分类未找到!
    public static final int BRAND_NOT_FOND=404; //品牌未找到!
    public static final int SPEC_GROUP_NOT_FOND=404; //商品规格组没有查到!
    public static final int SPEC_PARAM_NOT_FOND=404; //商品规格参数没有查到!
    public static final int GOODS_NOT_FOND=404; //商品不存在!
    public static final int GOODS_DETAIL_NOT_FOND=404; //商品详情不存在!
    public static final int GOODS_SKU_NOT_FOND=404; //商品SKu不存在!
    public static final int GOODS_STOCK_NOT_FOND=404; //商品库存不存在!




}
