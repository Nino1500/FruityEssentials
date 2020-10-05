package net.fruchtlabor.fruityessentials.data;

import org.bukkit.entity.Player;

public class PlayedTime {
    private Player player;

    public PlayedTime(Player player) {
        this.player = player;
    }

    public String getPlayerTimePlayed() {

        Long now = System.currentTimeMillis();
        long date = now - player.getFirstPlayed();

        long seconds = date / 1000 % 60;
        long minutes = date / (60 * 1000) % 60;
        long hours = date / (60 * 60 * 1000) % 24;
        long days = date / (24 * 60 * 60 * 1000);

        return days+" Tage "+hours+" Stunden "+minutes+" Minuten "+seconds+" Sekunden";
    }
}
