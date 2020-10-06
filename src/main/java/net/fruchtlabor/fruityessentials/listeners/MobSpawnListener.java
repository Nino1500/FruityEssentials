package net.fruchtlabor.fruityessentials.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class MobSpawnListener extends PreventListener implements Listener {
    public MobSpawnListener() {
        super("Config.preventCreatureSpawningWorlds");
    }

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent e) {
        if (enabledWorlds.contains(e.getEntity().getWorld())) {
            if (e.getEntity() != null && e.getEntity().getWorld() != null) {
                e.setCancelled(true);
            }
        }
    }
}
