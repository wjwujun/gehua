package com.gehua.common.exception;

import com.gehua.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GehuaException extends  RuntimeException{

    private ExceptionEnum exceptionEnum;



}
