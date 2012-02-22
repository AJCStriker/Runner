/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.countercraft.runner.listeners;


//Bukkit Imports

//Local Package Imports
import net.countercraft.runner.Controller;
import net.countercraft.runner.Runner;
import org.bukkit.event.Listener;

/**
 *
 * @author alexanderchristie
 */
public class EntityListener implements Listener{
  

    public EntityListener(){
         Controller.getPluginManager().registerEvents(this, Controller.getPluginInstance());
    }
    
}
