package net.fruchtlabor.fruityessentials.listeners;

import net.fruchtlabor.fruityessentials.utils.Debug;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class XPDropListener extends PreventListener implements Listener {
    public XPDropListener(){
        super("Config.preventXpDropWorlds");
    }
    @EventHandler
    public void onXPDrop(EntityDeathEvent e) {
        if(enabledWorlds.contains(e.getEntity().getWorld())){
                e.setDroppedExp(0);
                Debug.print("Cancelled XP Drop from "+e.getEntity().getName()+" in "+e.getEntity().getWorld().getName());
        }
    }
}
