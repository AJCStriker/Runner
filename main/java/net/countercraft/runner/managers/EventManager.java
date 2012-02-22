
package net.countercraft.runner.managers;

//Local Imports
import net.countercraft.runner.listeners.BlockListener;
import net.countercraft.runner.listeners.EntityListener;
import net.countercraft.runner.listeners.PlayerListener;
import net.countercraft.runner.listeners.WeatherListener;

public class EventManager {
    private BlockListener blockListener;
    private EntityListener entitylistener;
    private PlayerListener playerListener;
    private WeatherListener weatherListener;
    
    private EventManager() {
    }
    
    public static EventManager getInstance() {
        return EventManagerHolder.INSTANCE;
    }
    
    private static class EventManagerHolder {

        private static final EventManager INSTANCE = new EventManager();
    }
    
    public void registerEvents(){
        blockListener = new BlockListener();
        entitylistener = new EntityListener();
        playerListener = new PlayerListener();
        weatherListener = new WeatherListener();
    }
}
