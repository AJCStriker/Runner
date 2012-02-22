
package net.countercraft.runner;

//Java Imports
import java.util.HashMap;
import java.util.List;

//Bukkit Imports
import org.bukkit.World;

public class Settings {
    public boolean OPEN_CHANNEL;
    public String USERNAME;
    public String PASSWORD;
    public List<String> ADMIN_LIST;
    public List<String> USER_LIST;
    private HashMap<String, String> ADMIN_WORLD_LIST;
    public boolean ADMIN_INFO_ENABLED;
    
    private Settings() {
    }
    
    public static Settings getInstance() {
        return SettingsHolder.INSTANCE;
    }
    
    public boolean isAdmin(String name){
        for(String s : ADMIN_LIST){
            if(s.equalsIgnoreCase(name)){
                return true;
            }
        }
        
        return false;
    }
    
    public World getWorldForAdmin(String name){
        String worldName = ADMIN_WORLD_LIST.get(name);
        
        if(worldName != null){
            return Controller.getPluginInstance().getServer().getWorld(name);
        }else{
            return Controller.getPluginInstance().getServer().getWorlds().get(0);
        }
    }
    
    public boolean isUser(String name){
        if(isAdmin(name)){
            return true;
        }else{
            for(String s : USER_LIST){
                if(s.equalsIgnoreCase(name)){
                    return true;
                } 
            }
        }
        
        return false;
    }
    
    private static class SettingsHolder {

        private static final Settings INSTANCE = new Settings();
    }
    
    
    
    
    
   //Command Syntax
   public String WEATHER_SYNTAX_EXAMPLE = "!weather [storm]/[sun] {world name}";
   public String TIME_SYNTAX_EXAMPLE = "!time [1000] {world name}";
   
}
