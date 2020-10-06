package net.fruchtlabor.fruityessentials.listeners;

import net.fruchtlabor.fruityessentials.FruityEssentials;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        e.setQuitMessage(FruityEssentials.messages.getMessage("quit").replaceAll("%n",e.getPlayer().getName()));
    }
}
