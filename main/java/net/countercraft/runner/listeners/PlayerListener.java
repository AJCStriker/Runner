/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.countercraft.runner.listeners;

//Local Imports

//Bukkit Imports
import net.countercraft.runner.Controller;
import net.countercraft.runner.Runner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

/**
 *
 * @author alexanderchristie
 */
public class PlayerListener implements Listener{

    
    public PlayerListener() {
       Controller.getPluginManager().registerEvents(this, Controller.getPluginInstance());
    }   
    
    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        Controller.getXMPPManager().sendAll("[" + event.getPlayer().getDisplayName() + "] : " + event.getMessage());
        
    }

    
    
                                  
    
}
