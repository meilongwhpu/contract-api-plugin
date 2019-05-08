package io.nuls.contract.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class HttpClientDownloadThread implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(HttpClientDownloadThread.class);

    @Resource
    private HttpClientService httpClientService;


    private String targetPath;
    private String contractAddress;

    public HttpClientDownloadThread(){

    }

    public HttpClientDownloadThread(String contractAddress,String targetPath){
        this.targetPath=targetPath;
        this.contractAddress=contractAddress;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    @Override
    public void run() {
        logger.info("start HttpClientDownloadThread");
        try{
            boolean result =httpClientService.downloadRequest(ContractService.CONTEXT_PATH+ContractService.EXPORT+contractAddress,targetPath);
            if(result){
                logger.info("end HttpClientDownloadThread,download finish");
            }else{
                logger.info("end HttpClientDownloadThread,download fail");
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }
}
