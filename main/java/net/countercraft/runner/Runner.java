package net.countercraft.runner;

//Java Imports
import java.io.File;

//Bukkit Imports
import org.bukkit.plugin.java.JavaPlugin;

public class Runner extends JavaPlugin {
    private static Runner plugin;

    @Override
    public void onLoad() {
        super.onLoad();
        plugin = this;
    }
    
    public static Runner getInstance(){
        return plugin;
    }
    
    public void onDisable() {
        Controller.getXMPPManager().sendAll("Server is now shutting down. You will be disconnected from the chat.");
        System.out.println("[" + this.toString() + "] is now disabled!");
    }

    public void onEnable() {
        
        File pluginDirectory = Controller.getDataFolder();
        
        //Config Setup
        Controller.getFileManager().makeIfNotExistFolder(pluginDirectory);
        
        Controller.getConfig().loadConfig();
        
        
        
        Controller.getEventManager().registerEvents();
        
        Controller.getXMPPManager().connect();
        
        
        System.out.println("[" + this.toString() + "] is Enabled." );
        
    }
    
    public String getVersion(){
        return "V 2.1 Release";
    }
        
  
    
    
}
