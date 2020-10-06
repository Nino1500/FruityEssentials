package net.fruchtlabor.fruityessentials.listeners;

import net.fruchtlabor.fruityessentials.utils.Debug;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;

public class FireSpreadListener extends PreventListener implements Listener {

    public FireSpreadListener(){
        super("Config.preventFireSpreadWorlds");
    }
    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent e) {
        if (e.getIgnitingBlock() != null && e.getIgnitingBlock().getWorld() != null && enabledWorlds.contains(e.getIgnitingBlock().getWorld()) && e.getCause().equals(BlockIgniteEvent.IgniteCause.SPREAD)) {
            e.setCancelled(true);
            Debug.print("Cancelled FireSpread in "+e.getIgnitingBlock().getWorld().getName());
        }
    }


}
