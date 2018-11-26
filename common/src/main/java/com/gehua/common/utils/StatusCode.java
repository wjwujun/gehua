package com.gehua.common.utils;


/*
* 返回code
* */
public class StatusCode {
    public static final int OK=200;//成功

    public static final int ERROR=201;//失败
    public static final int ADD_ERROR=202; //添加失败！
    public static final int UPDATE_ERROR=203; //更新失败!
    public static final int DELETE_ERROR=204; //删除失败!
    public static final int QUERRY_NOT_FOND=205; //查询失败!

    public static final int LOGINERROR =206;//用户名或密码错误
    public static final int ACCESSERROR =207;//权限不足
    public static final int REMOTEERROR =208;//远程调用失败
    public static final int REPERROR =209;//重复操作
    public static final int INVALID_FILE_TYPE=210; //无效的文件类型!
    public static final int UPLOAD_ERROR=211; //文件上传失败!
    public static final int DATA_IS_EMPTY=212; //文件上传失败!




}
