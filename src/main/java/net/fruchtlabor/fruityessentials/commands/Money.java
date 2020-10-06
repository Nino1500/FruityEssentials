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
                        double am = moneyStream.getBalance(player);
                        player.sendMessage(ChatColor.DARK_GREEN+"[Geld] "+ChatColor.GREEN+am); //WORKS
                        return true;
                    }
                    break;
                case 1: //money pay
                        if(strings[0].equalsIgnoreCase("log")){
                            //TODO Transactions logger
                        }
                        if(strings[0].equalsIgnoreCase("top")){ //WORKS
                            ArrayList<String> list = moneyStream.getTopBalance(9); //0-9 equals 10
                            for (int i = 0; i < list.size(); i++) {
                                commandSender.sendMessage(list.get(i));
                            }
                        }
                    break;
                case 2: //money pay name
                    break;
                case 3:
                    if(strings[0].equalsIgnoreCase("pay") || strings[0].equalsIgnoreCase("bezahlen")){ //geld bezahlen playername menge
                        if(Bukkit.getPlayer(strings[1].toLowerCase()) != null){
                            Player receiver = Bukkit.getPlayer(strings[1].toLowerCase());
                            try {
                                double amount = Double.parseDouble(strings[2]);
                                assert receiver != null;
                                if(moneyStream.deleteMoney(Bukkit.getPlayer(commandSender.getName()), amount)){
                                    moneyStream.giveMoney(receiver, amount);
                                    receiver.sendMessage(ChatColor.DARK_GREEN+"[Geld] "+ChatColor.GREEN+amount+ChatColor.WHITE+" von "+ChatColor.GOLD+commandSender.getName()+" erhalten!");
                                    commandSender.sendMessage(ChatColor.DARK_GREEN+"[Geld] "+ChatColor.DARK_RED+amount+ChatColor.WHITE+" an "+ChatColor.GOLD+commandSender.getName()+" gesendet!");
                                    return true;
                                }
                                else{
                                    commandSender.sendMessage(ChatColor.DARK_GREEN+"[Geld] "+ChatColor.WHITE+"Du hast nicht genug Geld!");
                                }
                            }catch (Exception e){
                                commandSender.sendMessage(ChatColor.DARK_GREEN+"[Geld] "+ChatColor.RED+"Du hast was falsch angegeben!");
                            }
                        }
                    }
                    if(strings[0].equalsIgnoreCase("set") && commandSender.hasPermission("fe.admin")){ //money set playername amount
                        if(Bukkit.getPlayer(strings[1].toLowerCase()) != null){
                            Player receiver = Bukkit.getPlayer(strings[1].toLowerCase());
                            try {
                                double amount = Double.parseDouble(strings[2]);
                                assert receiver != null;
                                moneyStream.setBalance(receiver, amount);
                                return true;
                            }catch (Exception e){
                                e.printStackTrace();
                                commandSender.sendMessage(ChatColor.DARK_GREEN+"[Geld] "+ChatColor.RED+"Du hast was falsch angegeben!");
                            }
                        }
                    }
                    if(strings[0].equalsIgnoreCase("remove") && commandSender.hasPermission("fe.admin")){ //money remove playername amount
                        if(Bukkit.getPlayer(strings[1].toLowerCase()) != null){
                            Player receiver = Bukkit.getPlayer(strings[1].toLowerCase());
                            try {
                                double amount = Double.parseDouble(strings[2]);
                                assert receiver != null;
                                moneyStream.deleteMoney(receiver, amount);
                                return true;
                            }catch (Exception e){
                                commandSender.sendMessage(ChatColor.DARK_GREEN+"[Geld] "+ChatColor.RED+"Du hast was falsch angegeben!");
                            }
                        }
                    }
                    break;
            }
        }
        return false;
    }
}
