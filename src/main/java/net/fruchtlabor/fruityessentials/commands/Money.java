package net.fruchtlabor.fruityessentials.commands;

import net.fruchtlabor.fruityessentials.data.MoneyStream;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class Money implements CommandExecutor {

    private Plugin plugin;

    public Money(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("money") || command.getName().equalsIgnoreCase("geld")){
            MoneyStream moneyStream = new MoneyStream();
            switch (strings.length){
                case 0: //0 Param
                    if(commandSender instanceof Player){
                        Player player = ((Player) commandSender).getPlayer();
                        assert player != null;
                        double am = moneyStream.getBalance(player.getName());
                        player.sendMessage("Kontostand: "+am);
                        return true;
                    }
                    break;
                case 1: //money pay
                        if(strings[0].equalsIgnoreCase("log")){
                            //TODO Transactions logger
                        }
                        if(strings[0].equalsIgnoreCase("top")){
                            ArrayList<String> list = moneyStream.getTopBalance(10);
                            for (int i = 0; i < list.size(); i++) {
                                commandSender.sendMessage(ChatColor.BOLD+list.get(i));
                            }
                        }
                    break;
                case 2: //money pay name
                    break;
                case 3: //money pay name amount //money set player 0 //money remove player amount
                    if(strings[0].equalsIgnoreCase("pay") || strings[0].equalsIgnoreCase("bezahlen")){
                        if(Bukkit.getPlayer(strings[1].toLowerCase()) != null){
                            Player receiver = Bukkit.getPlayer(strings[1].toLowerCase());
                            try {
                                double amount = Double.parseDouble(strings[2]);
                                assert receiver != null;
                                if(moneyStream.deleteMoney(commandSender.getName(), amount)){
                                    moneyStream.giveMoney(receiver.getName(), amount);
                                    return true;
                                }
                                else{
                                    commandSender.sendMessage("Du hast nicht genug Geld!");
                                }
                            }catch (Exception e){
                                commandSender.sendMessage("Du hast was falsch angegeben!");
                            }
                        }
                    }
                    if(strings[0].equalsIgnoreCase("set") && commandSender.hasPermission("fe.admin")){
                        if(Bukkit.getPlayer(strings[1].toLowerCase()) != null){
                            Player receiver = Bukkit.getPlayer(strings[1].toLowerCase());
                            try {
                                double amount = Double.parseDouble(strings[2]);
                                assert receiver != null;
                                moneyStream.setBalance(receiver.getName().toLowerCase(), amount);
                                return true;
                            }catch (Exception e){
                                Bukkit.getConsoleSender().sendMessage(strings[1]+" "+strings[2]);
                                e.printStackTrace();
                                commandSender.sendMessage("Du hast was falsch angegeben!");
                            }
                        }
                    }
                    if(strings[0].equalsIgnoreCase("remove") && commandSender.hasPermission("fe.admin")){
                        if(Bukkit.getPlayer(strings[1].toLowerCase()) != null){
                            Player receiver = Bukkit.getPlayer(strings[1].toLowerCase());
                            try {
                                double amount = Double.parseDouble(strings[2]);
                                assert receiver != null;
                                moneyStream.deleteMoney(receiver.getName(), amount);
                                return true;
                            }catch (Exception e){
                                commandSender.sendMessage("Du hast was falsch angegeben!");
                            }
                        }
                    }
                    break;
            }
        }
        return false;
    }
}
