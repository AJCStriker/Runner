
package net.countercraft.runner.listeners;


//Bukkit Imports
import org.bukkit.event.Listener;
//Local Imports
import net.countercraft.runner.Controller;

public class EntityListener implements Listener{
  

    public EntityListener(){
         Controller.getPluginManager().registerEvents(this, Controller.getPluginInstance());
    }
    
}
