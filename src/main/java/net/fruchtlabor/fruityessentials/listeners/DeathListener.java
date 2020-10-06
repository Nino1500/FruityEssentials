package net.fruchtlabor.fruityessentials.listeners;

import net.fruchtlabor.fruityessentials.FruityEssentials;
import net.fruchtlabor.fruityessentials.utils.ChatAPI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
mport org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Player Death Listener
 */
public class DeathListener extends PreventListener implements Listener {

    public DeathListener(){
        super("Config.preventDeathWorlds");
    }
    /**
     * Player Death Listener
     * @param e
     */
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
            Player p = e.getEntity();
            EntityDamageEvent.DamageCause cause = p.getLastDamageCause().getCause();
            EntityDamageEvent evt = p.getLastDamageCause();
            Entity killer = p.getKiller();
            String msg = FruityEssentials.messages.getMessage("playerDeath_"+cause.name());
            msg = msg.replaceAll("%n",p.getName());
            if(killer != null) {
                if (killer instanceof Player) {
                    msg = msg.replaceAll("%k", ((Player) killer).getName());
                }

            } else {
                if(p.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent ev = (EntityDamageByEntityEvent) p.getLastDamageCause();
                    if(ev != null && ev.getDamager() != null && ev.getDamager().getName() != null) {
                        if(ev.getDamager().getType() == EntityType.AREA_EFFECT_CLOUD){
                            msg = FruityEssentials.messages.getMessage("playerDeath_DRAGON_BREATH").replaceAll("%n",p.getName());
                        } else {
                            msg = msg.replaceAll("%k", ev.getDamager().getName());
                        }
                    }
                }
                if(p.getLastDamageCause() instanceof EntityDamageByBlockEvent){
                    EntityDamageByBlockEvent ev = (EntityDamageByBlockEvent)p.getLastDamageCause();
                    if(ev != null && ev.getDamager() != null && ev.getDamager().getType() != null && ev.getDamager().getType().name() != null) {
                        msg = msg.replaceAll("%k", ev.getDamager().getType().name());
                    }
                }
            }
            e.setDeathMessage("");
            ChatAPI.broadcastMessageWP(msg);
    }
}
