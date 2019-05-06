package io.nuls.contract.resource;

import io.nuls.contract.httpclient.model.Result;
import io.nuls.contract.httpclient.model.ResultCode;
import io.nuls.contract.httpclient.model.ResultGenerator;
import io.nuls.contract.model.*;
import io.nuls.contract.packages.PackageService;
import io.nuls.contract.service.ContractService;
import io.nuls.contract.utils.ToolUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import io.nuls.contract.kernel.utils.StringUtils;
import javax.annotation.Resource;
import javax.websocket.server.PathParam;

/**
* Created by CodeGenerator on 2019/04/17.
*/
@RestController
@RequestMapping(value = "/contract",produces = "application/json")
@Api(tags = "智能合约API")
public class ContractController {

    @Resource
    private ContractService contractService;

    @Resource
    private PackageService packageService;

    @PostMapping("/package")
    @ApiOperation(value="智能合约打包",notes="智能合约打包")
    public Result packageContract(@ApiParam(name="packageContractInfo", value="智能合约的源代码信息", required=true) PackageContractInfo packageContractInfo){
        if(packageContractInfo==null){
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"参数错误");
        }
        if(StringUtils.isBlank(packageContractInfo.getMvnProjectRootPath())){
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"智能合约源代码的根路径不能为空");
        }
        if(StringUtils.isBlank(packageContractInfo.getTargetPath())){
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"智能合约jar包的目标路径不能为空");
        }
        if(StringUtils.isBlank(packageContractInfo.getJarName())){
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"jar包的名称不能为空");
        }
        return  packageService.packageForJar(packageContractInfo.getMvnProjectRootPath(), packageContractInfo.getTargetPath(),packageContractInfo.getJarName());
    }

    @GetMapping("/genHexEncode")
    @ApiOperation(value="生成HEX编码",notes="生成HEX编码")
    public Result generateHexEncode(@ApiParam(name="targetPath", value="jar包的本地地址", required=true) @RequestParam("targetPath") String targetPath){
        if(StringUtils.isBlank(targetPath)){
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"jar包的本地地址不能为空");
        }
        return  packageService.generateHexEncode(targetPath);
    }

    @PostMapping("/create")
    @ApiOperation(value="创建智能合约",notes="创建智能合约")
    public Result createContract(@ApiParam(name = "createForm", value = "创建智能合约", required = true)ContractCreate create) {
        if (create == null || create.getGasLimit() < 0 || create.getPrice() < 0) {
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"参数GasLimit和price应该大于零");
        }
        String contractCode = create.getContractCode();
        if(StringUtils.isBlank(contractCode)) {
            return ResultGenerator.genFailResult(ResultCode.NULL_PARAMETER,"智能合约代码不能为空");
        }
        if(StringUtils.isBlank(create.getSender())){
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"参数sender不能为空");
        }
        return contractService.createContract(create);
    }


    @PostMapping("/constructor")
    @ApiOperation(value="获取智能合约构造函数",notes="获取智能合约构造函数")
    public Result contractConstructor(@ApiParam(name = "createForm", value = "创建智能合约", required = true)  ContractCode code) {
        if (code == null) {
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"参数不能为空");
        }
        String contractCode = code.getContractCode();
        if(StringUtils.isBlank(contractCode)) {
            return ResultGenerator.genFailResult(ResultCode.NULL_PARAMETER,"智能合约代码不能为空");
        }
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/precreate")
    @ApiOperation(value="测试创建智能合约",notes="测试创建智能合约")
    public Result preCreateContract(@ApiParam(name = "preCreateForm", value = "测试创建智能合约", required = true) PreContractCreate create) {
        if (create == null || create.getGasLimit() < 0 || create.getPrice() < 0) {
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"参数GasLimit和price应该大于零");
        }
        String contractCode = create.getContractCode();
        if(StringUtils.isBlank(contractCode)) {
            return ResultGenerator.genFailResult(ResultCode.NULL_PARAMETER,"智能合约代码不能为空");
        }
        if(StringUtils.isBlank(create.getSender())){
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"参数sender不能为空");
        }
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/imputedgas/create")
    @ApiOperation(value="估算创建智能合约的GAS消耗",notes="估算创建智能合约的GAS消耗")
    public Result imputedGasCreateContract(@ApiParam(name="imputedGasCreateForm", value="估算创建智能合约的Gas消耗", required=true) ImputedGasContractCreate paramImputedGasContractCreate) {
        if (paramImputedGasContractCreate == null) {
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"参数不能为空");
        }
        String contractCode = paramImputedGasContractCreate.getContractCode();
        if(StringUtils.isBlank(contractCode)) {
            return ResultGenerator.genFailResult(ResultCode.NULL_PARAMETER,"智能合约代码不能为空");
        }
        if(paramImputedGasContractCreate.getPrice()<0){
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"参数price错误");
        }
        if(paramImputedGasContractCreate.getSender()==null){
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"参数sender不能为空");
        }
        return contractService.imputedGasCreateContract(paramImputedGasContractCreate);
    }

    @PostMapping("/call")
    @ApiOperation(value="调用智能合约",notes="调用智能合约")
    public Result callContract(@ApiParam(name="callFrom", value="调用智能合约", required=true) ContractCall paramContractCall){
        if (paramContractCall == null || paramContractCall.getValue() < 0 || paramContractCall.getGasLimit() < 0 || paramContractCall.getPrice() < 0) {
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"参数value或GasLimit或price错误");
        }
        if(paramContractCall.getMethodName()==null){
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"参数methodName不能为空");
        }
        return  contractService.callContract(paramContractCall);
    }

    @PostMapping("/imputedgas/call")
    @ApiOperation(value="估算调用智能合约的Gas消耗",notes="估算调用智能合约的Gas消耗")
    public Result imputedGasCallContract(@ApiParam(name="imputedGasCallForm", value="估算调用智能合约的Gas消耗", required=true) ImputedGasContractCall paramImputedGasContractCall){
        if (paramImputedGasContractCall == null || paramImputedGasContractCall.getSender()==null ||
                paramImputedGasContractCall.getContractAddress()==null || paramImputedGasContractCall.getMethodName()==null
                ||paramImputedGasContractCall.getPrice() < 0) {
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"参数错误");
        }
        return contractService.imputedGasCallContract(paramImputedGasContractCall);
    }


    @PostMapping("/collection")
    @ApiOperation(value="收藏智能合约地址/修改备注名称",notes="收藏智能合约地址/修改备注名称")
    public Result contractCollection(@ApiParam(name="collection", value="收藏智能合约地址/修改备注名称", required=true) ContractCollection paramContractCollection){
        if (paramContractCollection == null) {
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"参数不能为空");
        }
        if(paramContractCollection.getAccountAddress()==null){
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"钱包账户地址不能为空");
        }
        if(paramContractCollection.getContractAddress()==null){
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"智能合约地址不能为空");
        }
        return contractService.contractCollection(paramContractCollection);
    }

    @PostMapping("/collection/cancel")
    @ApiOperation(value="取消收藏智能合约地址",notes="取消收藏智能合约地址")
    public Result collectionCancel(@ApiParam(name="collectionBase", value="取消收藏参数", required=true) ContractAddressBase paramContractAddressBase){
        if (paramContractAddressBase == null) {
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"参数不能为空");
        }
        if(paramContractAddressBase.getAccountAddress()==null){
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"钱包账户地址不能为空");
        }
        if(paramContractAddressBase.getContractAddress()==null){
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"智能合约地址不能为空");
        }
        return contractService.collectionCancel(paramContractAddressBase);
    }

    @PostMapping("/wallet/list/{address}")
    @ApiOperation(value="获取钱包账户的合约地址列表(账户创建的合约以及钱包收藏的合约)",notes="获取钱包账户的合约地址列表(账户创建的合约以及钱包收藏的合约)")
    public Result getContractCollectionList(@ApiParam(name="address", value="钱包账户地址", required=true) @PathParam("address") String address,
                                            @ApiParam(name="pageNumber", value="页码", required=true) @RequestParam(value="pageNumber",required=true) Integer pageNumber,
                                            @ApiParam(name="pageSize", value="每页条数", required=false) @RequestParam(value="pageSize", required=false) Integer pageSize){
        if (null == pageNumber || pageNumber == 0) {
            pageNumber = 1;
        }
        if (null == pageSize || pageSize == 0) {
            pageSize = 10;
        }
        if (pageNumber < 0 || pageSize < 0 || pageSize > 100) {
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"参数pageSize的值只能在1-100之间");
        }

        if (StringUtils.isBlank(address)) {
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"钱包账户地址不能为空");
        }
        return contractService.getContractCollectionList(address,pageNumber,pageSize);
    }


    @GetMapping("/info/{address}")
    @ApiOperation(value="获取智能合约信息",notes="获取智能合约信息")
    public Result getContractInfo(@ApiParam(name="address", value="合约地址", required=true) @RequestParam("address") String address,
                                  @ApiParam(name="accountAddress", value="钱包账户地址", required=false) String accountAddress){
        if (StringUtils.isBlank(address)) {
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"钱包账户地址不能为空");
        }
        return contractService.getContractInfo(address,accountAddress);
    }

    @GetMapping("/balance/{address}")
    @ApiOperation(value="获取智能合约余额",notes="获取智能合约余额")
    public Result getContractBalance(@ApiParam(name="address", value="合约地址", required=true) @RequestParam("address") String address){
        if (StringUtils.isBlank(address)) {
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"合约地址不能为空");
        }
        return contractService.getContractBalance(address);
    }

    @GetMapping("/tx/{hash}")
    @ApiOperation(value="获取智能合约交易详情",notes="获取智能合约交易详情")
    public Result getContractTx(@ApiParam(name="hash", value="交易hash", required=true) @RequestParam("hash") String hash){
        if (StringUtils.isBlank(hash)) {
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"交易hash不能为空");
        }
        return contractService.getContractTx(hash);
    }

    @GetMapping("/tx/list/{contractAddress}")
    @ApiOperation(value="获取智能合约的交易列表",notes="获取智能合约的交易列表")
    public Result getTxList(@ApiParam(name="contractAddress", value="智能合约地址", required=true) @RequestParam("contractAddress") String contractAddress,
                            @ApiParam(name="pageNumber", value="页码", required=true) @RequestParam(value="pageNumber",required=true) Integer pageNumber,
                            @ApiParam(name="pageSize", value="每页条数", required=false) @RequestParam(value="pageSize", required=false) Integer pageSize,
                            @ApiParam(name="accountAddress", value="钱包账户地址") @RequestParam(value="accountAddress") String accountAddress){
        if (null == pageNumber || pageNumber == 0) {
            pageNumber = 1;
        }
        if (null == pageSize || pageSize == 0) {
            pageSize = 10;
        }
        if (pageNumber < 0 || pageSize < 0 || pageSize > 100) {
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"参数pageSize的值只能在1-100之间");
        }
        if (StringUtils.isBlank(contractAddress)) {
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"合约地址不能为空");
        }
        return contractService.getTxList(contractAddress,pageNumber,pageSize,accountAddress);
    }

    @GetMapping(value="/export/{address}")
    @ApiOperation(value="导出合约编译代码的jar包",notes="导出合约编译代码的jar包")
    public Result export(@ApiParam(name = "address", value = "账户地址", required = true)
                                    @RequestParam("address") String address,
                         @ApiParam(name="targetPath", value="本地文件路径", required=true) @RequestParam(value="targetPath",required=true) String targetPath){
        if (StringUtils.isBlank(address)) {
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"合约地址不能为空");
        }
        if (StringUtils.isBlank(targetPath)) {
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"本地文件路径不能为空");
        }
        return contractService.export(address,targetPath);
    }

    @PostMapping("/addServiceNode")
    @ApiOperation(value="添加服务器端的ip:port",notes="添加服务器端的ip:port")
    public Result addServiceNode(@ApiParam(name="NodeInfo", value="服务端节点信息", required=true) NodeInfo nodeInfo){
       if(StringUtils.isBlank(nodeInfo.getIpAndPort())){
           return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"服务端的节点地址不能为空");
       }
        if(!ToolUtils.ipAndPortCheck(nodeInfo.getIpAndPort())){
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"服务端的节点的信息不合法");
        }
        return contractService.addServiceNode(nodeInfo);
    }

    @PostMapping("/removeServiceNode")
    @ApiOperation(value="删除服务器端的ip:port",notes="删除服务器端的ip:port")
    public Result removeServiceNode(@ApiParam(name="NodeInfo", value="服务端节点信息", required=true) NodeInfo nodeInfo){
        if(StringUtils.isBlank(nodeInfo.getIpAndPort())){
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"服务端的节点地址不能为空");
        }
        if(!ToolUtils.ipAndPortCheck(nodeInfo.getIpAndPort())){
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"服务端的节点的信息不合法");
        }
        return contractService.removeServiceNode(nodeInfo);
    }

    @PostMapping("/addAccount/{address}")
    @ApiOperation(value="添加账户地址",notes="添加账户地址")
    public Result addAccountAddress(@ApiParam(name="address", value="账户地址", required=true) @RequestParam("address") String address){
        if(StringUtils.isBlank(address)){
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"账户地址不能为空");
        }
        return  contractService.addAccountAddress(address);
    }

    @PostMapping("/delAccount/{address}")
    @ApiOperation(value="删除账户地址",notes="删除账户地址")
    public Result delAccountAddress(@ApiParam(name="address", value="账户地址", required=true) @RequestParam("address") String address){
        if(StringUtils.isBlank(address)){
            return ResultGenerator.genFailResult(ResultCode.PARAMETER_ERROR,"账户地址不能为空");
        }
        return  contractService.removeAccountAddress(address);
    }

}
