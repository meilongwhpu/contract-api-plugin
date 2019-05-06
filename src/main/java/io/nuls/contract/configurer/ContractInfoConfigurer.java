package io.nuls.contract.configurer;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ContractInfoConfigurer implements InitializingBean {

    private static PropertiesConfiguration propConfig;

    private static final ContractInfoConfigurer CONFIG = new ContractInfoConfigurer();

    /**
     * 自动保存
     */
    private static boolean autoSave = true;

    private ContractInfoConfigurer(){
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    private static void init(){
        String propertiesFile=System.getProperty("user.dir")+ File.separator+"Contract.properties";
        File properties=new File(propertiesFile);
        try {
            if(!properties.exists()){
                properties.createNewFile();
            }
            propConfig = new PropertiesConfiguration(propertiesFile);
            //自动重新加载 
            propConfig.setReloadingStrategy(new FileChangedReloadingStrategy());
            //自动保存 
            propConfig.setAutoSave(autoSave);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String[] getIpAndPort() {
        Object ipAndPort =propConfig.getProperty(ConfigurerConstant.SERVICEIPANDPORT);
        if(ipAndPort!=null &&!ipAndPort.toString().equals("")){
            return   ipAndPort.toString().split("|");
        }else{
            return new String[1];
        }
    }

    public void setIpAndPort(String ipAndPort) {
        Object oldValue =propConfig.getProperty(ConfigurerConstant.SERVICEIPANDPORT);
        if(oldValue!=null && !"".equals(oldValue)){
            if(oldValue.toString().indexOf(ipAndPort)>0){
                return;
            }
            propConfig.setProperty(ConfigurerConstant.SERVICEIPANDPORT, oldValue.toString()+"|"+ipAndPort);
        }else {
            propConfig.setProperty(ConfigurerConstant.SERVICEIPANDPORT, ipAndPort);
        }
    }

    public String[] getAccount() {
        Object account =propConfig.getProperty(ConfigurerConstant.ACCOUNTADDRESS);
        if(account!=null &&!account.toString().equals("")){
            return   account.toString().split("|");
        }else{
            return new String[1];
        }
    }

    public void setAccount(String account) {
        Object oldValue =propConfig.getProperty(ConfigurerConstant.ACCOUNTADDRESS);
        if(oldValue!=null && !"".equals(oldValue)){
            if(oldValue.toString().indexOf(account)>0){
                return;
            }
            propConfig.setProperty(ConfigurerConstant.ACCOUNTADDRESS, oldValue.toString()+"|"+account);
        }else {
            propConfig.setProperty(ConfigurerConstant.ACCOUNTADDRESS, account);
        }
    }

    public boolean deleteKey(String key,String value){
        Object oldValue =propConfig.getProperty(key);
        if(!oldValue.equals("")){
            if(oldValue.toString().indexOf(value)>0){
                String[] values= oldValue.toString().split("|");
                String nodeStr="";
                for(int i=0;i<values.length;i++){
                    if(!values[i].equals(value)){
                        nodeStr=nodeStr+values[i]+"|";
                    }
                }
                propConfig.setProperty(key, nodeStr.substring(0,nodeStr.length()-1));
                return true;
            }else{
                return true;
            }
        }else {
            return false;
        }
    }

    public String getContract() {
        Object port =propConfig.getProperty(ConfigurerConstant.CONTRACT);
        if(port!=null &&!port.toString().equals("")){
            return  port.toString();
        }else{
            return "";
        }
    }

    public void setContract(String contract) {
        propConfig.setProperty(ConfigurerConstant.CONTRACT, contract);
    }

    public Object getValue(String key){
        return propConfig.getProperty(key);
    }

    public void setProperty(String key, String value){
        propConfig.setProperty(key, value);
    }
}
