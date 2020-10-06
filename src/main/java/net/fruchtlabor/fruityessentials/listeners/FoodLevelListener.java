package net.fruchtlabor.fruityessentials.listeners;

import net.fruchtlabor.fruityessentials.utils.Debug;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelListener extends PreventListener implements Listener {

    public FoodLevelListener() {
        super("Config.preventStarveWorlds");
    }

    @EventHandler
    public void onStarve(FoodLevelChangeEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        if (enabledWorlds.contains(e.getEntity().getWorld())) {
            e.setCancelled(true);
            e.setFoodLevel(20);
            Debug.print("Cancelled Starve of " + e.getEntity().getName() + " in " + e.getEntity().getLocation().getWorld());
        }
    }
}
