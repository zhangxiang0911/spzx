package com.zhangxiang.spzx.common.exception;

import com.zhangxiang.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;

@Data
public class ZxException extends RuntimeException{
    private Integer code;
    private String message;
    private ResultCodeEnum codeEnum;

    public ZxException(ResultCodeEnum codeEnum) {
        this.codeEnum = codeEnum;
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMessage();
    }
}
