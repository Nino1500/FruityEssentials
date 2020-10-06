package net.fruchtlabor.fruityessentials.listeners;

import net.fruchtlabor.fruityessentials.FruityEssentials;
import net.fruchtlabor.fruityessentials.utils.Debug;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener extends PreventListener implements Listener {

    public WeatherChangeListener(){
        super("Config.preventWeatherChangeWorlds");
    }
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        for (String s : FruityEssentials.getInstance().getConfig().getStringList("Config.preventWeatherChangeWorlds")) {
            if (Bukkit.getWorld(s) == null) {
                continue;
            }
            if (Bukkit.getWorld(s).equals(e.getWorld())) {
                e.setCancelled(true);
                Debug.print("Cancelled WeatherChange in "+e.getWorld());
            }
        }
    }
}
