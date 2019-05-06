package com.conpany.project;

import io.nuls.contract.httpclient.HttpClientUtil;
import io.nuls.contract.httpclient.common.HttpClientUtils;
import io.nuls.contract.httpclient.common.HttpConfig;
import io.nuls.contract.httpclient.exception.HttpProcessException;
import io.nuls.contract.model.ContractCall;
import io.nuls.contract.utils.JSONUtils;

import java.util.HashMap;
import java.util.Map;

public class HttpClientTest {

    public static void testGet() throws HttpProcessException {
            System.out.println("--------简单方式调用（默认post）--------");
            String url = "http://10.3.2.244:8001/api/contract/TTbBkNJH4VJUZ31Adsm6X16B6PuRkzNE";
            HttpConfig config = HttpConfig.initHttpConfig();
            //简单调用
            String resp = HttpClientUtil.get(config.url(url));

            System.out.println("请求结果内容长度："+ resp.length());

            System.out.println("\n#################################\n");
             System.out.println("请求结果内容："+ resp.toString());
            System.out.println("\n#################################\n");
    }

    public static void testPost() throws Exception {
        System.out.println("--------简单方式调用（默认post）--------");
        String url = "http://10.3.2.244:8001/api/contract/call";
        ContractCall call =new ContractCall();
        call.setContractAddress("TTbBkNJH4VJUZ31Adsm6X16B6PuRkzNE");
        call.setMethodName("name");
        call.setSender("TTaiUkbGNvDC1oPDMWuu2N323s3wUPBZ");
        call.setPrice(25);
        call.setPassword("meilong1985");
        HttpConfig config = HttpConfig.initHttpConfig();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(HttpClientUtils.ENTITY_JSON, JSONUtils.obj2json(call));
        //简单调用
        String resp = HttpClientUtil.post(config.url(url).map(map));

        System.out.println("请求结果内容长度："+ resp.length());

        System.out.println("\n#################################\n");
        System.out.println("请求结果内容："+ resp.toString());
        System.out.println("\n#################################\n");
    }

    public static void main(String[] args) throws Exception  {
        testPost();
        //testGet();
    }
}
