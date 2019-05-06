package io.nuls.contract.service;

import io.nuls.contract.httpclient.model.Result;
import io.nuls.contract.model.*;

public interface ContractService {

      String CONTEXT_PATH="/api/contract";
      String CREATE_CONTRACT_PATH="/create";
      String CONTRACT_CONSTUCTOR_PATH="/constructor";
      String TEST_CREATE_CONTRACT_PATH="/precreate";
      String IMPUTEDGAS_CREATE_CONTRACT_PATH="/imputedgas/create";
      String CALL_CONTRACT_PATH="/call";
      String IMPUTEDGAS_CALL_CONTRACT_PATH="/imputedgas/call";
      String CONTRANCT_COLLECTION_PATH="/collection";
      String CANCEL_CONTRANCT_COLLECTION_PATH="/collection/cancel";
      String GET_CONTRACT_INFO_PATH="/info/wallet/";
      String GET_CONTRACT_BALANCE_PATH="/balance/";
      String GET_CONTRACT_TX_INFO_PATH="/tx/";
      String GET_CONTRACT_TX_LIST_PATH="/tx/list/";
      String GET_WALLET_CONTRACT_ADDRESS_LIST_PATH="/wallet/list/";

    public Result createContract(ContractCreate create);
    public Result contractConstructor(ContractCode code);
    public Result preCreateContract(PreContractCreate create);
    public Result imputedGasCreateContract(ImputedGasContractCreate paramImputedGasContractCreate);
    public Result callContract(ContractCall paramContractCall);
    public Result imputedGasCallContract(ImputedGasContractCall paramImputedGasContractCall);
    public Result getContractInfo(String address,String accountAddress);
    public Result getContractBalance(String address);
    public Result getContractTx(String hash);
    public Result getTxList(String contractAddress,int pageNumber,int pageSize ,String accountAddress);
    public Result contractCollection(ContractCollection paramContractCollection);
    public Result collectionCancel(ContractAddressBase paramContractAddressBase);
    public Result getContractCollectionList(String address,int pageNumber,int pageSize );

    public Result export(String address,String targetPath);

    public Result addServiceNode(NodeInfo nodeInfo);
    public Result removeServiceNode(NodeInfo nodeInfo);
    public Result addAccountAddress(String address);
    public Result removeAccountAddress(String address);

}
