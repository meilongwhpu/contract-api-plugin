package io.nuls.contract.model;

import io.nuls.contract.utils.ContractUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "创建智能合约表单数据")
public class ContractCreate extends ContractBase {

    @ApiModelProperty(name = "contractCode", value = "智能合约代码(字节码的Hex编码字符串)", required = true)
    private String contractCode;
    @ApiModelProperty(name = "args", value = "参数列表", required = false)
    private Object[] args;

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String[][] getArgs(String[] types) {
        return ContractUtil.twoDimensionalArray(args, types);
    }

}
