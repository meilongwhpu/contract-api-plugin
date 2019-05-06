package io.nuls.contract.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientDownloadThread extends HttpClientService implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(HttpClientDownloadThread.class);
    private String targetPath;
    private String contractAddress;

    public HttpClientDownloadThread(String contractAddress,String targetPath){
        this.targetPath=targetPath;
        this.contractAddress=contractAddress;
    }

    @Override
    public void run() {
        try{
            this.downloadRequest("/export/"+contractAddress,targetPath);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }
}
