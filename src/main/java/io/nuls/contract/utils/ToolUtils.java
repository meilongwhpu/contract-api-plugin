package io.nuls.contract.utils;

import io.nuls.contract.kernel.utils.StringUtils;

public class ToolUtils {

    public static boolean ipAndPortCheck(String text) {
        // 定义正则表达式
        String numberRegex="^[0-9]*[1-9][0-9]*$";
        String ipRegex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
       String[] ipAndPort= text.split(":");
       if(ipAndPort.length!=2){
           return false;
       }

       if(StringUtils.isBlank(ipAndPort[0])||StringUtils.isBlank(ipAndPort[1])){
           return false;
       }
       if(ipAndPort[1].matches(numberRegex) && ipAndPort[0].matches(ipRegex)){
           return true;
       }else {
           return false;
       }

    }
}
