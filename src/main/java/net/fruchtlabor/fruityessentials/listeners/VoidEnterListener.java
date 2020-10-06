package net.fruchtlabor.fruityessentials.listeners;

import net.fruchtlabor.fruityessentials.utils.Debug;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class VoidEnterListener extends PreventListener implements Listener {

    public VoidEnterListener() {
        super("Config.preventVoidEnterWorlds");
    }

    @EventHandler
    public void onVoidEnter(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
            if (enabledWorlds.contains(e.getEntity().getWorld())) {
                e.setCancelled(true);
                //getMaxHealth() is deprecated, look at: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/entity/Damageable.html#getMaxHealth()
                Player p = (Player)e.getEntity();
                double max_health = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                p.setHealth(max_health);
                Debug.print("Cancelled VoidEnter of " + e.getEntity().getName() + " in " + e.getEntity().getLocation().getWorld());
                p.performCommand("spawn");
            }
        }
    }
}
