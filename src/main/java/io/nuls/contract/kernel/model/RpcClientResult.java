package io.nuls.contract.kernel.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class RpcClientResult {

    private boolean success;

    private Object data;

    public RpcClientResult() {
    }

    public RpcClientResult(boolean success, ErrorData errorData) {
        this.success = success;
        this.data = errorData;
    }

    public RpcClientResult(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    public static RpcClientResult getFailed(ErrorData errorData) {
        return new RpcClientResult(false, errorData);
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }

    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean dataToBooleanValue() {
        return (boolean) ((Map) data).get("value");
    }

    public String dataToStringValue() {
        Object object = ((Map) data).get("value");
        if (null != object) {
            return (String) object;
        }
        return null;
    }
}
