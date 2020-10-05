package net.fruchtlabor.fruityessentials.data;

import net.fruchtlabor.fruityessentials.FruityEssentials;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class FruityPlayer {
    private static Map<Player, FruityPlayer> players = new HashMap<>();

    private Player player;

    private PlayerTeleport playerTeleport = null;

    private FruityPlayer(Player player) {
        this.player = player;
        players.put(player, this);
    }

    public void requestTp(Player sender) {
        playerTeleport = new PlayerTeleport(player, sender);
        player.sendMessage(sender.getDisplayName()+" will sich zu die teleportieren!"+"\n"+"Klicke auf 'Annehmen' oder 'Ablehnen' (60 sekunden valide)");
        TextComponent msg = new TextComponent("Annehmen");
        //msg.setClickEvent(new ClickEvent(ClickEvent.Action.));
        Bukkit.getScheduler().scheduleSyncDelayedTask(FruityEssentials.getInstance(), new Runnable() {
            @Override
            public void run() {
                playerTeleport = null;
                player.sendMessage("Teleport-Anfrage abgelaufen!");
            }
        }, 20*60l);
    }

    public static FruityPlayer getPlayerInstance(Player player) {
        return players.containsKey(player) ? players.get(player) : new FruityPlayer(player);
    }

    public static boolean removePlayer(Player player) {
        if (players.containsKey(player)) {
            players.remove(player);
            return true;
        }
        return false;
    }

}
