package net.fruchtlabor.fruityessentials.commands;

import net.fruchtlabor.fruityessentials.helpclasses.MassClass;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Fe implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player;

        if(commandSender instanceof Player){
            player = ((Player) commandSender).getPlayer();
        }
        else {
            //MSG.execute("Dieser Befehl muss von einem Spieler ausgeführt werden!");
            return false;
        }

        if(command.getName().equalsIgnoreCase("fe")){
            //MSG.execute("Liste Befehle auf (nur die für die die person auch perms hat)");
            assert player != null;
            if(player.hasPermission("fe.help")){
                MassClass massClass = new MassClass();
                ArrayList<String> list = massClass.getAllCommandsofPlayer(player);
                //TODO: print out the list with a chatapi to the player maybe make it clickable
                return true;
            }
        }
        return false;
    }
}
