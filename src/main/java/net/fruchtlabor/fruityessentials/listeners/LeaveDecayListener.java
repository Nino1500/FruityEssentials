package net.fruchtlabor.fruityessentials.listeners;

import net.fruchtlabor.fruityessentials.utils.Debug;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

public class LeaveDecayListener extends PreventListener implements Listener {

    public LeaveDecayListener() {
        super("Config.preventLeaveDecayWorlds");
    }

    @EventHandler
    public void onLeaveDecay(LeavesDecayEvent e) {
        if (enabledWorlds.contains(e.getBlock().getWorld())) {
            e.setCancelled(true);
            Debug.print("Cancelled LeaveDecay in " + e.getBlock().getWorld());
        }
    }
}
