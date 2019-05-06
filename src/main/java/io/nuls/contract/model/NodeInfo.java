package io.nuls.contract.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("服务端节点信息")
public class NodeInfo {

    @ApiModelProperty(name="ipAndPort", value="服务端节点信息，格式为IP：PORT", required=true)
    private String ipAndPort;

    public String getIpAndPort() {
        return ipAndPort;
    }

    public void setIpAndPort(String ipAndPort) {
        this.ipAndPort = ipAndPort;
    }

}
