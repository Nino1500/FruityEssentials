package net.fruchtlabor.fruityessentials.listeners;

import net.fruchtlabor.fruityessentials.utils.Debug;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Damage Listener
 */
public class DamageListener extends PreventListener implements Listener {

    public DamageListener(){
        super("Config.preventDamageWorlds");
    }
    /**
     * EntityDamageEvent
     * @param e
     */
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (enabledWorlds.contains(e.getEntity().getWorld())) {
            e.setCancelled(true);
            Debug.print("Cancelled Damage in "+e.getEntity().getWorld());
        }
    }
}
