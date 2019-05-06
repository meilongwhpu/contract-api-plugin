package io.nuls.contract.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "智能合约Jar包的信息")
public class PackageContractInfo {

    @ApiModelProperty(name = "mvnProjectRootPath", value = "智能合约源代码的根路径", required = true)
    private String mvnProjectRootPath;

    @ApiModelProperty(name = "targetPath", value = "智能合约jar包的目标路径", required = true)
    private String targetPath;

    @ApiModelProperty(name = "jarName", value = "jar包的名称（由pom.xml中的artifactId值和version值组成）", required = true)
    private String jarName;

    public String getMvnProjectRootPath() {
        return mvnProjectRootPath;
    }

    public void setMvnProjectRootPath(String mvnProjectRootPath) {
        this.mvnProjectRootPath = mvnProjectRootPath;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public String getJarName() {
        return jarName;
    }

    public void setJarName(String jarName) {
        this.jarName = jarName;
    }
}
