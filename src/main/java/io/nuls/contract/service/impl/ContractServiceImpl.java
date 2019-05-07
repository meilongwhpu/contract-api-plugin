package io.nuls.contract.service.impl;

import io.nuls.contract.configurer.ConfigurerConstant;
import io.nuls.contract.configurer.ContractInfoConfigurer;
import io.nuls.contract.httpclient.model.Result;
import io.nuls.contract.httpclient.model.ResultGenerator;
import io.nuls.contract.kernel.model.RpcClientResult;
import io.nuls.contract.model.*;
import io.nuls.contract.service.ContractService;
import io.nuls.contract.service.HttpClientDownloadThread;
import io.nuls.contract.service.HttpClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ContractServiceImpl implements ContractService {
    private final Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);
    @Resource
    private HttpClientService httpClientService;
    @Resource
    private HttpClientDownloadThread downloadThread;
    @Autowired
    private ContractInfoConfigurer infoConfigurer;

    /**
     * 创建智能合约
     * @param create
     * @return
     */
    public Result createContract(ContractCreate create){
        Result result;
        try{
            RpcClientResult resp= httpClientService.postRequest(ContractService.CONTEXT_PATH+ContractService.CREATE_CONTRACT_PATH,create);
            result =ResultGenerator.convertResultType(resp);
        }catch(Exception e){
            result = ResultGenerator.genFailResult(e.getMessage());
        }
        return result;
    }

    /**
     * 获取智能合约构造函数
     * @param code
     * @return
     */
    public Result contractConstructor(ContractCode code){
        Result result;
        try{
            RpcClientResult resp= httpClientService.postRequest(ContractService.CONTEXT_PATH+ContractService.CONTRACT_CONSTUCTOR_PATH,code);
            result =ResultGenerator.convertResultType(resp);
        }catch(Exception e){
            result = ResultGenerator.genFailResult(e.getMessage());
        }
        return result;
    }

    /**
     * 测试创建智能合约
     * @param create
     * @return
     */
    public Result preCreateContract(PreContractCreate create){
        Result result;
        try{
            RpcClientResult resp= httpClientService.postRequest(ContractService.CONTEXT_PATH+ContractService.TEST_CREATE_CONTRACT_PATH,create);
            result =ResultGenerator.convertResultType(resp);
        }catch(Exception e){
            result = ResultGenerator.genFailResult(e.getMessage());
        }
        return result;
    }

    /**
     * 估算创建智能合约的GAS消耗
     * @param paramImputedGasContractCreate
     * @return
     */
    public Result imputedGasCreateContract(ImputedGasContractCreate paramImputedGasContractCreate){
        Result result;
        try{
            RpcClientResult resp= httpClientService.postRequest(ContractService.CONTEXT_PATH+ContractService.IMPUTEDGAS_CREATE_CONTRACT_PATH,paramImputedGasContractCreate);
            result =ResultGenerator.convertResultType(resp);
        }catch(Exception e){
            result = ResultGenerator.genFailResult(e.getMessage());
        }
        return result;
    }

    /**
     * 调用智能合约
     * @param paramContractCall
     * @return
     */
    public Result callContract(ContractCall paramContractCall){
        Result result;
        try{
            RpcClientResult resp= httpClientService.postRequest(ContractService.CONTEXT_PATH+ContractService.CALL_CONTRACT_PATH,paramContractCall);
            result =ResultGenerator.convertResultType(resp);
        }catch(Exception e){
            result = ResultGenerator.genFailResult(e.getMessage());
        }
        return result;
    }

    /**
     * 估算调用智能合约的Gas消耗
     * @param paramImputedGasContractCall
     * @return
     */
    public Result imputedGasCallContract(ImputedGasContractCall paramImputedGasContractCall){
        Result result;
        try{
            RpcClientResult resp= httpClientService.postRequest(ContractService.CONTEXT_PATH+ContractService.IMPUTEDGAS_CALL_CONTRACT_PATH,paramImputedGasContractCall);
            result =ResultGenerator.convertResultType(resp);
        }catch(Exception e){
            result = ResultGenerator.genFailResult(e.getMessage());
        }
        return result;
    }



    /**
     * 获取智能合约信息
     * @param address
     * @param accountAddress
     * @return
     */
    public Result getContractInfo(String address,String accountAddress){
        String urlPath =ContractService.CONTEXT_PATH+ContractService.GET_CONTRACT_INFO_PATH+address;
        if(accountAddress!=null &&!accountAddress.equals("")){
            urlPath=urlPath+"?accountAddress="+accountAddress;
        }
        Result result;
        try{
            RpcClientResult resp= httpClientService.getRequest(urlPath);
            result =ResultGenerator.convertResultType(resp);
        }catch(Exception e){
            result = ResultGenerator.genFailResult(e.getMessage());
        }
        return result;
    }

    /**
     * 获取智能合约余额
     * @param address
     * @return
     */
    public Result getContractBalance(String address){
        String urlPath =ContractService.CONTEXT_PATH+ContractService.GET_CONTRACT_BALANCE_PATH+address;
        Result result;
        try{
            RpcClientResult resp= httpClientService.getRequest(urlPath);
            result =ResultGenerator.convertResultType(resp);
        }catch(Exception e){
            result = ResultGenerator.genFailResult(e.getMessage());
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return result;
    }

    /**
     * 获取智能合约交易详情
     * @param hash  交易hash
     * @return
     */
    public Result getContractTx(String hash){
        String urlPath =ContractService.CONTEXT_PATH+ContractService.GET_CONTRACT_TX_INFO_PATH+hash;
        Result result;
        try{
            RpcClientResult resp= httpClientService.getRequest(urlPath);
            result =ResultGenerator.convertResultType(resp);
        }catch(Exception e){
            result = ResultGenerator.genFailResult(e.getMessage());
        }
        return result;
    }

    /**
     * 获取智能合约的交易列表
     * @param contractAddress
     * @param pageNumber
     * @param pageSize
     * @param accountAddress
     * @return
     */
    public Result getTxList(String contractAddress,int pageNumber,int pageSize ,String accountAddress){
        String urlPath =ContractService.CONTEXT_PATH+ContractService.GET_CONTRACT_TX_LIST_PATH+contractAddress;
        String param;
        if(pageNumber<=0){
            pageNumber=1;
        }
        param ="?pageNumber="+pageNumber;
        if(pageSize!=0){
            param =param+"&pageSize="+pageSize;
        }
        if(accountAddress!=null && !accountAddress.equals("")){
            param =param+"&accountAddress="+accountAddress;
        }
        urlPath=urlPath+param;
        Result result;
        try{
            RpcClientResult resp= httpClientService.getRequest(urlPath);
            result =ResultGenerator.convertResultType(resp);
        }catch(Exception e){
            result = ResultGenerator.genFailResult(e.getMessage());
        }
        return result;
    }

    /**
     * 收藏智能合约地址/修改备注名称
     * @param paramContractCollection
     * @return
     */
    public Result contractCollection(ContractCollection paramContractCollection){
        Result result;
        try{
            RpcClientResult resp= httpClientService.postRequest(ContractService.CONTEXT_PATH+ContractService.CONTRANCT_COLLECTION_PATH,paramContractCollection);
            result =ResultGenerator.convertResultType(resp);
        }catch(Exception e){
            result = ResultGenerator.genFailResult(e.getMessage());
        }
        return result;
    }

    /**
     * 取消收藏智能合约地址
     * @param paramContractAddressBase
     * @return
     */
    public Result collectionCancel(ContractAddressBase paramContractAddressBase){
        Result result;
        try{
            RpcClientResult resp= httpClientService.postRequest(ContractService.CONTEXT_PATH+ContractService.CANCEL_CONTRANCT_COLLECTION_PATH,paramContractAddressBase);
            result =ResultGenerator.convertResultType(resp);
        }catch(Exception e){
            result = ResultGenerator.genFailResult(e.getMessage());
        }
        return result;
    }

    /**
     * 获取钱包账户的合约地址列表(账户创建的合约以及钱包收藏的合约)
     * @param address
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Result getContractCollectionList(String address,int pageNumber,int pageSize ){
        String urlPath =ContractService.CONTEXT_PATH+ContractService.GET_WALLET_CONTRACT_ADDRESS_LIST_PATH+address;
        String param;
        if(pageNumber<=0){
            pageNumber=1;
        }
        param ="?pageNumber="+pageNumber;
        if(pageSize!=0){
            param =param+"&pageSize="+pageSize;
        }
        urlPath=urlPath+param;
        Result result;
        try{
            RpcClientResult resp= httpClientService.getRequest(urlPath);
            result =ResultGenerator.convertResultType(resp);
        }catch(Exception e){
            result = ResultGenerator.genFailResult(e.getMessage());
        }
        return result;
    }

    /**
     *
     * @param address  合约地址
     * @param targetPath 下载到本地的文件路径（包含文件名）
     * @return
     */
    public Result export(String address,String targetPath){
        Result result = new Result();
        try{
            downloadThread.setContractAddress(address);
            downloadThread.setTargetPath(targetPath);
            Thread thread =new Thread(downloadThread);
            thread.start();
            result=ResultGenerator.genSuccessResult("正在下载，请在路径:"+targetPath+"下面查看jar包");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            result=ResultGenerator.genFailResult(e.getMessage());
        }
        return result;
    }

    @Override
    public Result addServiceNode(NodeInfo nodeInfo) {
        infoConfigurer.setIpAndPort(nodeInfo.getIpAndPort());
        return ResultGenerator.genSuccessResult("添加服务端节点信息成功");
    }


    @Override
    public Result getServiceNode() {
        return ResultGenerator.genSuccessResult(infoConfigurer.getIpAndPort());
    }

    @Override
    public Result removeServiceNode(NodeInfo nodeInfo) {
        boolean result =infoConfigurer.deleteKey(ConfigurerConstant.SERVICEIPANDPORT,nodeInfo.getIpAndPort());
        if(result){
            return ResultGenerator.genSuccessResult("删除服务端节点信息成功");
        }else{
            return ResultGenerator.genSuccessResult("删除服务端节点信息失败");
        }
    }

    @Override
    public Result addAccountAddress(String address) {
        infoConfigurer.setAccount(address);
        return ResultGenerator.genSuccessResult("添加账户地址成功");
    }


    @Override
    public Result getAccountAddress() {
        return ResultGenerator.genSuccessResult(infoConfigurer.getAccount());
    }

    @Override
    public Result removeAccountAddress(String address) {
        boolean result =infoConfigurer.deleteKey(ConfigurerConstant.ACCOUNTADDRESS,address);
        if(result){
            return ResultGenerator.genSuccessResult("删除账户地址成功");
        }else{
            return ResultGenerator.genSuccessResult("删除账户地址失败");
        }
    }
}
