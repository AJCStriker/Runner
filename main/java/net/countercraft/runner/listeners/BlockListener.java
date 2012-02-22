
package net.countercraft.runner.listeners;

//Local Imports
import net.countercraft.runner.Controller;

//Bukkit Imports
import org.bukkit.event.Listener;

public class BlockListener implements Listener{
    

    public BlockListener() {
         Controller.getPluginManager().registerEvents(this, Controller.getPluginInstance());
    }

}
