
package net.countercraft.runner.config;

//Java Import
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

//Local Imports
import net.countercraft.runner.Controller;

//Snakeyaml Imports
import org.yaml.snakeyaml.Yaml;

public class Config {
    private Map<String, Object> dataChain = new HashMap<String, Object>();
    
    private Config() {
        initMap();
    }
    
    public static Config getInstance() {
        return ConfigDefaultHolder.INSTANCE;
    }
    
    private static class ConfigDefaultHolder {

        private static final Config INSTANCE = new Config();
    }

    public Map<String, Object> getDataChain() {
        return dataChain;
    }

    public void setDataChain(Map<String, Object> dataChain) {
        this.dataChain = dataChain;
    }
    
    private void initMap(){
        //Username
            String username = "example@gmail.com";
            dataChain.put("username", username);
   
        //Password
            String password = "YourPassword";
            dataChain.put("password", password);
            
        //Admin List
            List<String> adminList = new ArrayList<String>();
            adminList.add("admin@gmail.com");
            adminList.add("webadmin@countercraft.net");
            adminList.add("alex@countercraft.net");
            dataChain.put("admins", adminList);
            
        //Open Channel Mode
            boolean openChannel = false;
            dataChain.put("openChannel", openChannel);
            
        //User List
            List<String> userList = new ArrayList<String>();
            userList.add("normalGuy@gmail.com");
            userList.add("test@googleAppServices.com");
            dataChain.put("users", userList);
            
        //Admin Transmissions
            boolean adminInfo = false;
            dataChain.put("admin_info", adminInfo);
    }
    
    public void loadConfig(){
        
            File config = new File(Controller.getDataFolder(), "config.yml");
            boolean exists = Controller.getFileManager().makeIfNotExistFile(config);
            
            if(!exists){
                createConfig(config);
                System.out.println(
                        "[" + 
                        Controller.getPluginInstance().toString() + 
                        "] will now shut down as a new config file has been created.");
                Controller.getPluginInstance().getServer().shutdown();
            }else{
                Map informationRoot = parseFile(config);
                
                Controller.getSettings().USERNAME = (String) informationRoot.get("username");
                Controller.getSettings().PASSWORD = (String) informationRoot.get("password");
                Controller.getSettings().OPEN_CHANNEL = (Boolean) informationRoot.get("openChannel");
                Controller.getSettings().ADMIN_LIST = (List<String>) informationRoot.get("admins");
                Controller.getSettings().USER_LIST = (List<String>) informationRoot.get("users");
                Controller.getSettings().ADMIN_INFO_ENABLED = (Boolean) informationRoot.get("admin_info");
            }
            
        
    }
    
    private void createConfig(File config){
        Yaml yaml = new Yaml();
        FileWriter fstream = null;

            try {
                fstream= new FileWriter(config);
            } catch (IOException ex) {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            }

        BufferedWriter out = new BufferedWriter(fstream);
        yaml.dump(dataChain, out);

            try {
                fstream.close();
            } catch (IOException ex) {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private Map parseFile(File config){
        Yaml yaml = new Yaml();
        Reader reader = null;
        try {
            reader = new FileReader(config);
            Map<String, Object> map = new HashMap<String, Object>();
            map = (Map) yaml.load(reader);
            return map;
        } catch (FileNotFoundException fnfe) {
        } finally {
            
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException ioe) {
                }
            }
        }
        
        return null;
    }
}
