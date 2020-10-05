package net.fruchtlabor.fruityessentials.commands;

import net.fruchtlabor.fruityessentials.data.PlayedTime;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Time implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player = ((Player) commandSender).getPlayer();

            assert player != null;
            if(player.hasPermission("fe.time") || player.hasPermission("fe.user")){ //TODO: Implement fe.user perm in all user specific commands
                if(strings.length == 1){
                    Player otherplayer = Bukkit.getPlayer(strings[0]);
                    PlayedTime time = new PlayedTime(otherplayer);
                    player.sendMessage(time.getPlayerTimePlayed());

                } else {
                    PlayedTime time = new PlayedTime(player);
                    player.sendMessage(time.getPlayerTimePlayed());
                }
            }
        }
        return false;
    }
}
