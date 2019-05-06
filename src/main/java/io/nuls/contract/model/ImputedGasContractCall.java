package io.nuls.contract.model;

import io.nuls.contract.utils.ContractUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("估算调用智能合约的Gas消耗的表单数据")
public class ImputedGasContractCall {

    @ApiModelProperty(name="sender", value="交易创建者", required=true)
    private String sender;

    @ApiModelProperty(name="contractAddress", value="智能合约地址", required=true)
    private String contractAddress;

    @ApiModelProperty(name="value", value="交易附带的货币量", required=false)
    private long value;

    @ApiModelProperty(name="methodName", value="方法名", required=true)
    private String methodName;

    @ApiModelProperty(name="methodDesc", value="方法签名，如果方法名不重复，可以不传", required=false)
    private String methodDesc;

    @ApiModelProperty(name="price", value="执行合约单价", required=true)
    private long price;

    @ApiModelProperty(name="args", value="参数列表", required=false)
    private Object[] args;

    public String getSender()
    {
        return this.sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContractAddress() {
        return this.contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public long getValue() {
        return this.value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodDesc() {
        return this.methodDesc;
    }

    public void setMethodDesc(String methodDesc) {
        this.methodDesc = methodDesc;
    }

    public long getPrice() {
        return this.price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String[][] getArgs() {
        return ContractUtil.twoDimensionalArray(this.args);
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
