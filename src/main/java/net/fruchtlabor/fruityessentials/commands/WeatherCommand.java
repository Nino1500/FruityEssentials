package net.fruchtlabor.fruityessentials.commands;

import net.fruchtlabor.fruityessentials.FruityEssentials;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Weather Command
 */
public class WeatherCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("weather")){
            if(strings.length == 1){
                if(strings[0].equals("sun") || strings[0].equals("clear")){
                    World w = null;
                    if(commandSender instanceof Player){
                        Player p = (Player)commandSender;
                        w = p.getWorld();
                        w.setStorm(false);
                    } else {
                        List<World> worlds = Bukkit.getWorlds();
                        for(World world : worlds){
                           if(world.getEnvironment().equals(World.Environment.NORMAL)){
                               w = world;
                               w.setStorm(false);
                           }
                        }
                    }
                    commandSender.sendMessage(FruityEssentials.messages.getMessage("weatherSetSun").replaceAll("%n",w.getName()));
                    return true;
                }
                if(strings[0].equals("storm") || strings[0].equals("thunder")){
                    World w = null;
                    if(commandSender instanceof Player){
                        Player p = (Player)commandSender;
                        w = p.getWorld();
                        w.setStorm(true);
                    } else {
                        List<World> worlds = Bukkit.getWorlds();
                        for(World world : worlds){
                            if(world.getEnvironment().equals(World.Environment.NORMAL)){
                                w = world;
                                w.setStorm(true);
                            }
                        }
                    }
                    commandSender.sendMessage(FruityEssentials.messages.getMessage("weatherSetStorm").replaceAll("%n",w.getName()));
                    return true;
                }
            }
            commandSender.sendMessage(FruityEssentials.messages.getMessage("missingParameters"));
            return false;
        }
        return false;
    }
}
