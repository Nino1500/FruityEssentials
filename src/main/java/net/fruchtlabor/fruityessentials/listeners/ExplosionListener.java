package net.fruchtlabor.fruityessentials.listeners;


import net.fruchtlabor.fruityessentials.utils.Debug;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class ExplosionListener extends PreventListener implements Listener {

    public ExplosionListener() {
        super("Config.preventExplosionWorlds");
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent e) {
        if (enabledWorlds.contains(e.getLocation().getWorld())) {
            e.setCancelled(true);
            Debug.print("Cancelled Explode of "+e.getEntity().getName()+" in "+e.getLocation().getWorld());
        }
    }
}
