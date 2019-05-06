package io.nuls.contract.httpclient.model;

/**
 * 响应码枚举，参考HTTP状态码的语义
 */
public enum ResultCode {
    SUCCESS(200),//成功
    FAIL(400),//失败
    UNAUTHORIZED(401),//未认证（签名错误）
    NOT_FOUND(404),//接口不存在
    INTERNAL_SERVER_ERROR(500),//服务器内部错误

    PARAMETER_ERROR(10012), //参数错误
    ADDRESS_ERROR(50004), //地址错误
    NULL_PARAMETER(10013);//参数为空

    private final int code;

    ResultCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
