package io.nuls.contract.httpclient.model;

import io.nuls.contract.kernel.model.RpcClientResult;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    private static final String DEFAULT_FAIL_MESSAGE = "SERVICE RETURN FAIL";

    public static Result genSuccessResult() {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> Result<T> genSuccessResult(T data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }

    public static Result genFailResult(ResultCode code,String message) {
        return new Result()
                .setCode(code)
                .setMessage(message);
    }

    public static<T> Result<T> genFailResult(T data) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(DEFAULT_FAIL_MESSAGE)
                .setData(data);
    }

    public static Result convertResultType(RpcClientResult rpcResult){
        Result result;
        if(rpcResult.isSuccess()){
            result= genSuccessResult(rpcResult.getData());
        }else{
            result=genFailResult(rpcResult.getData());
        }
        return result;
    }
}
