package io.nuls.contract.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;

public class ContractUtil {


    /**
     * 此长度来源于BlockExtendsData中定长变量的字节总数
     */
    private static final int BLOCK_EXTENDS_DATA_FIX_LENGTH = 28;

    private static final String STRING = "String";

    public static String[][] twoDimensionalArray(Object[] args, String[] types) {
        if (args == null) {
            return null;
        } else {
            int length = args.length;
            String[][] two = new String[length][];
            Object arg;
            for (int i = 0; i < length; i++) {
                arg = args[i];
                if(arg == null) {
                    two[i] = new String[0];
                    continue;
                }
                if(arg instanceof String) {
                    String argStr = (String) arg;
                    // 非String类型参数，若传参是空字符串，则赋值为空一维数组，避免数字类型转化异常 -> 空字符串转化为数字
                    if(types != null && StringUtils.isBlank(argStr) && !STRING.equalsIgnoreCase(types[i])) {
                        two[i] = new String[0];
                    } else {
                        two[i] = new String[]{argStr};
                    }
                } else if(arg.getClass().isArray()) {
                    int len = Array.getLength(arg);
                    String[] result = new String[len];
                    for(int k = 0; k < len; k++) {
                        result[k] = valueOf(Array.get(arg, k));
                    }
                    two[i] = result;
                } else if(arg instanceof ArrayList) {
                    ArrayList resultArg = (ArrayList) arg;
                    int size = resultArg.size();
                    String[] result = new String[size];
                    for(int k = 0; k < size; k++) {
                        result[k] = valueOf(resultArg.get(k));
                    }
                    two[i] = result;
                } else {
                    two[i] = new String[]{valueOf(arg)};
                }
            }
            return two;
        }
    }

    public static String[][] twoDimensionalArray(Object[] args) {
        return twoDimensionalArray(args, null);
    }

    public static String valueOf(Object obj) {
        return (obj == null) ? null : obj.toString();
    }

    public static String bigInteger2String(BigInteger bigInteger) {
        if(bigInteger == null) {
            return null;
        }
        return bigInteger.toString();
    }

    public static String simplifyErrorMsg(String errorMsg) {
        String resultMsg = "contract error - ";
        if(StringUtils.isBlank(errorMsg)) {
            return resultMsg;
        }
        if(errorMsg.contains("Exception:")) {
            String[] msgs = errorMsg.split("Exception:", 2);
            return resultMsg + msgs[1].trim();
        }
        return resultMsg + errorMsg;
    }

}
