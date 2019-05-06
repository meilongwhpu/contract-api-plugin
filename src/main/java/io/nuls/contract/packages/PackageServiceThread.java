package io.nuls.contract.packages;

import io.nuls.contract.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class PackageServiceThread implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(PackageServiceThread.class);
    private String mavenProjectRootPath;
    private String targetPath;
    private String jarName;

    public  PackageServiceThread(String mavenProjectRootPath,String targetPath,String jarName){
        if("".equals(mavenProjectRootPath)||mavenProjectRootPath==null){
            this.mavenProjectRootPath=System.getProperty("user.dir");
        }else{
            this.mavenProjectRootPath=mavenProjectRootPath;
        }
        this.targetPath=targetPath;
        this.jarName=jarName;
    }

    @Override
    public void run() {
        String disk=mavenProjectRootPath.substring(0,mavenProjectRootPath.indexOf(":")+1);
        String command ="cmd /c "+disk+" && cd "+mavenProjectRootPath+" && mvn clean package -Dmaven.test.skip=true";
        try{
            Process process=Runtime.getRuntime().exec(command);
            process.waitFor();
            String sourcePath=mavenProjectRootPath+"\\target\\"+jarName+".jar";
            targetPath=targetPath+"\\"+jarName+".jar";
            File source=new File(sourcePath);
            File target=new File(targetPath);
            if(target.exists()){
                target.delete();
            }
            FileUtil.copyFile(source,target);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }
}
