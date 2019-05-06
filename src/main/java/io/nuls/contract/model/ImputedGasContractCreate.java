package io.nuls.contract.model;

import io.nuls.contract.utils.ContractUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("估算创建智能合约的Gas消耗的表单数据")
public class ImputedGasContractCreate {

    @ApiModelProperty(name="sender", value="交易创建者", required=true)
    private String sender;

    @ApiModelProperty(name="price", value="执行合约单价", required=true)
    private long price;

    @ApiModelProperty(name="contractCode", value="智能合约代码(字节码的Hex编码字符串)", required=true)
    private String contractCode;

    @ApiModelProperty(name="args", value="参数列表", required=false)
    private Object[] args;

    public String getSender()
    {
        return this.sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public long getPrice() {
        return this.price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getContractCode() {
        return this.contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String[][] getArgs() {
        return ContractUtil.twoDimensionalArray(this.args);
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
