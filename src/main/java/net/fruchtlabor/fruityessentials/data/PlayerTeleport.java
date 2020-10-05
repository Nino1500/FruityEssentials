package net.fruchtlabor.fruityessentials.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerTeleport {
    private long timeToAccept;
    private long currentTime;

    private Player target;
    private Player sender;




    public PlayerTeleport(Player target, Player sender) {
        this.target = target;
        this.sender = sender;

        currentTime = System.currentTimeMillis();
        timeToAccept = currentTime + 60*1000;
    }

    private void countDown() {
    }
}
