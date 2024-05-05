package com.zhangxiang.spzx.common.exception;


import com.zhangxiang.spzx.model.vo.common.Result;
import com.zhangxiang.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result error(){
        return Result.build(null, ResultCodeEnum.SYSTEM_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(ZxException.class)
    public Result error(ZxException e){
        return Result.build(null, e.getCodeEnum());
    }
}
