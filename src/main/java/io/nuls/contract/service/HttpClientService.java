package io.nuls.contract.service;

import io.nuls.contract.configurer.ContractInfoConfigurer;
import io.nuls.contract.httpclient.HttpClientUtil;
import io.nuls.contract.httpclient.common.HttpClientUtils;
import io.nuls.contract.httpclient.common.HttpConfig;
import io.nuls.contract.kernel.model.RpcClientResult;
import io.nuls.contract.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HttpClientService {
    private final Logger logger = LoggerFactory.getLogger(HttpClientService.class);

    @Autowired
    private ContractInfoConfigurer infoConfigurer;

    public RpcClientResult getRequest(String urlPath) throws Exception {
        String url =getServicePath(urlPath);
        logger.info("getRequest URL: "+url);
        HttpConfig config = HttpConfig.initHttpConfig();
        //简单调用
        String resp = HttpClientUtil.get(config.url(url));
        RpcClientResult rpcResult = JSONUtils.json2pojo(resp, RpcClientResult.class);
        return rpcResult;
    }

    public RpcClientResult postRequest(String urlPath ,Object  call) throws Exception{
        String url =getServicePath(urlPath);
        logger.info("postRequest URL: "+url +" , paramter: "+ call.toString());
        HttpConfig config = HttpConfig.initHttpConfig();
        String jsonStr =JSONUtils.obj2json(call);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(HttpClientUtils.ENTITY_JSON, jsonStr);
        //简单调用
        String resp = HttpClientUtil.post(config.url(url).map(map));
        RpcClientResult rpcResult =JSONUtils.json2pojo(resp, RpcClientResult.class);
        return rpcResult;
    }

    /**
     *
     *
     * @param urlPath 服务端API的URL地址，例：/api/contract/upload/constructor
     * @param filePath 本地jar文件路径，例：D:\test\pocmContract-1.2.2.jar
     * @param paramString  服务器API接口的参数，例：  jarfile
     * @return
     * @throws Exception
     */
    public RpcClientResult uploadRequest(String urlPath,String filePath,String paramString)throws Exception{
        String url =getServicePath(urlPath);
        String[] filePaths = {filePath};
        HttpConfig config = HttpConfig.initHttpConfig();
        config.url(url).fileMode(1).files(filePaths,paramString,false);
        String resp = HttpClientUtil.post(config);
        RpcClientResult rpcResult =JSONUtils.json2pojo(resp, RpcClientResult.class);
        return rpcResult;
    }

    /**
     *
     * @param urlPath 服务器端的URL地址
     * @param targetPath  下载到本地的文件路径（包含文件名）
     * @return  true表示下载成功，false表示下载失败
     * @throws Exception
     */
    public boolean downloadRequest(String urlPath,String targetPath)throws Exception{
        String downUrl = getServicePath(urlPath);
        return HttpClientUtil.down(HttpConfig.initHttpConfig().url(downUrl),targetPath);
    }

    private String getServicePath(String urlPath){
        String[] ipAndport=infoConfigurer.getIpAndPort();
        return "http://"+ipAndport[0]+urlPath;
    }
}
