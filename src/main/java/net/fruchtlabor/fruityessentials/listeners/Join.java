package net.fruchtlabor.fruityessentials.listeners;

import net.fruchtlabor.fruityessentials.data.MoneyStream;
import net.fruchtlabor.fruityessentials.gatherclasses.MassClass;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class Join implements Listener {

    Plugin plugin;

    public Join(Plugin plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(!player.hasPlayedBefore()){
            MassClass massClass = new MassClass();
            massClass.giveLuckPerm("fe.help", player);
            MoneyStream moneyStream = new MoneyStream(plugin);
            event.getPlayer().sendMessage(ChatColor.GOLD+"Willkommen auf Fruchtlabor, dir wurden"+ChatColor.GREEN+"500€"+ChatColor.GOLD +"auf dein Konto überwiesen!");
            moneyStream.giveMoney(event.getPlayer().getName(), 500);
        }
    }
}
