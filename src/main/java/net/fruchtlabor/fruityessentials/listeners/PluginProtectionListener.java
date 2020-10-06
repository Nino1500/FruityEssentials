package net.fruchtlabor.fruityessentials.listeners;


import net.fruchtlabor.fruityessentials.FruityEssentials;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;


public class PluginProtectionListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (!FruityEssentials.getInstance().getConfig().getBoolean("Config.pluginProtection")) return;
        if (e.getPlayer().hasPermission("core.spy")) return;

        String cmd = e.getMessage();
        if (cmd.equalsIgnoreCase("/?")
                || cmd.equalsIgnoreCase("/plugins")
                || cmd.equalsIgnoreCase("/pl")
                || cmd.equalsIgnoreCase("/bukkit:plugins")
                || cmd.equalsIgnoreCase("/bukkit:pl")
                || cmd.equalsIgnoreCase("/version")
                || cmd.equalsIgnoreCase("/ver")
                || cmd.equalsIgnoreCase("/bukkit:version")
                || cmd.equalsIgnoreCase("/bukkit:ver")
                || cmd.equalsIgnoreCase("/about")
                || cmd.equalsIgnoreCase("/bukkit:about")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onTab(PlayerChatTabCompleteEvent e) {
        if (!FruityEssentials.getInstance().getConfig().getBoolean("Config.pluginProtection")) return;
        if (e.getPlayer().hasPermission("core.spy")) return;

        String cmd = e.getChatMessage();

        if (cmd.equalsIgnoreCase("/")
                || cmd.equalsIgnoreCase("/?")
                || cmd.equalsIgnoreCase("/plugins")
                || cmd.equalsIgnoreCase("/pl")
                || cmd.equalsIgnoreCase("/bukkit:plugins")
                || cmd.equalsIgnoreCase("/bukkit:pl")
                || cmd.equalsIgnoreCase("/version")
                || cmd.equalsIgnoreCase("/ver")
                || cmd.equalsIgnoreCase("/bukkit:version")
                || cmd.equalsIgnoreCase("/bukkit:ver")
                || cmd.equalsIgnoreCase("/about")
                || cmd.equalsIgnoreCase("/bukkit:about")) {
            return;
        }
    }
}
