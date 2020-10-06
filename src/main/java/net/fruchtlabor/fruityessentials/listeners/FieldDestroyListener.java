package net.fruchtlabor.fruityessentials.listeners;

import net.fruchtlabor.fruityessentials.utils.Debug;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class FieldDestroyListener extends PreventListener implements Listener {
    public FieldDestroyListener() {
        super("Config.preventFieldDestroyWorlds");
    }

    @EventHandler
    public void noFieldDestroy(PlayerInteractEvent e)
    {
        if (enabledWorlds.contains(e.getPlayer().getLocation().getWorld())) {
            if (e.getAction() == Action.PHYSICAL && e.getClickedBlock().getType() == Material.LEGACY_SOIL) {
                e.setCancelled(true);
                Debug.print("Cancelled FieldDestroy of "+e.getPlayer().getName()+" in "+e.getPlayer().getLocation().getWorld());
            }
        }
    }
}
