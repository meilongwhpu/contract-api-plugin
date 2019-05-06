package io.nuls.contract.packages;

import io.nuls.contract.httpclient.model.Result;
import io.nuls.contract.httpclient.model.ResultGenerator;
import io.nuls.contract.utils.Hex;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;

@Service
public class PackageService {
    private final Logger logger = LoggerFactory.getLogger(PackageService.class);
    /**
     *
     * @param targetPath jar文件绝对路径，例d:\
     * @param jarName  与pom中的artifactId-version名称值
     * @return
     */
    public Result packageForJar(String mavenProjectRootPath,String targetPath,String jarName){
        Result result = new Result();
        try{
            Thread thread =new Thread(new PackageServiceThread(mavenProjectRootPath,targetPath,jarName));
            thread.start();
            result=ResultGenerator.genSuccessResult("正在打包，请在路径:"+targetPath+"下面查看jar包");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            result=ResultGenerator.genFailResult(e.getMessage());
        }
        return result;
    }

    /**
     * 根据jar包生成HEX编码
     * @param sourcePath
     * @return
     */
    public Result generateHexEncode(String sourcePath){
        Result result = new Result();
        String hexEncode="";
        try {
            InputStream jarFile = new FileInputStream(sourcePath);
            byte[] contractCode= IOUtils.toByteArray(jarFile);
            hexEncode=Hex.encode(contractCode);
            result=ResultGenerator.genSuccessResult(hexEncode);
        }catch (Exception e) {
            result=ResultGenerator.genFailResult(e.getMessage());
        }
        return result;
    }
}
