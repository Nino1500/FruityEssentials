package net.fruchtlabor.fruityessentials.commands;

import net.fruchtlabor.fruityessentials.data.BankStream;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class Bank implements CommandExecutor {

    private Plugin plugin;

    public Bank(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("bank")){
            BankStream bankStream = new BankStream(plugin);
            switch (strings.length){
                case 0:
                    commandSender.sendMessage(ChatColor.GOLD+"<[FruchtBank]>");
                    commandSender.sendMessage("/bank erstellen Bankname");
                    commandSender.sendMessage("/bank Bankname");
                    commandSender.sendMessage("/bank einladen Bankname Spieler");
                    commandSender.sendMessage("/bank erlauben Bankname Spieler");
                    commandSender.sendMessage("/bank einzahlen Bankname Menge");
                    commandSender.sendMessage("/bank auszahlen Bankname Menge");
                    commandSender.sendMessage(ChatColor.GOLD+"<[FruchtBank]>");
                    break;
                case 1:
                    if(bankStream.checkBankExists(strings[0])){
                        String bankname = strings[0];
                        commandSender.sendMessage(bankname+": "+ChatColor.GOLD+bankStream.getBankBalance(bankname));
                    }
                    break;
                case 2:
                    if(strings[0].equalsIgnoreCase("erstellen")){
                        String bankname = strings[1];
                        if(bankStream.createBank(bankname)){
                            commandSender.sendMessage("Die Bank: "+ChatColor.GOLD+bankname+ChatColor.WHITE+" wurde erfolgreich erstellt!");
                        }
                        else {
                            commandSender.sendMessage("Die Bank existiert schon!");
                        }
                    }
                    break;
                case 3:
                    if(strings[0].equalsIgnoreCase("einladen")){
                        String bankname = strings[1];
                        String player = strings[2];
                        if(bankStream.isOwner(bankname, commandSender.getName())){
                            if(bankStream.inviteToBank(bankname, commandSender.getName(), player)){
                                commandSender.sendMessage("Der Spieler: "+ChatColor.GOLD+player+ChatColor.WHITE+" wurde hinzugefügt!");
                            }
                            else{
                                commandSender.sendMessage("Der Spieler konnte nicht hinzugefügt werden!");
                            }
                        }
                        else{
                            commandSender.sendMessage("Du bist nicht der Besitzer dieser Bank!");
                        }
                    }
                    if(strings[0].equalsIgnoreCase("erlauben")){
                        String bankname = strings[1];
                        String player = strings[2];
                        if(bankStream.isOwner(bankname, commandSender.getName())){
                            if(bankStream.allowBankUse(bankname, bankStream.getBankOwner(bankname), player)){
                                commandSender.sendMessage("Erfolgreich Zugriff gewährt!");
                            }
                            else{
                                commandSender.sendMessage("Dem Spieler konnte kein Zugriff gewährt werden!");
                            }
                        }
                        else{
                            commandSender.sendMessage("Du bist nicht der Besitzer dieser Bank!");
                        }
                    }
                    if(strings[0].equalsIgnoreCase("einzahlen")){
                        String bankname = strings[1];
                        String amount = strings[2];
                        if(bankStream.hasAccses(bankname, commandSender.getName())){
                            if(bankStream.payIn(bankname, amount)){
                                commandSender.sendMessage(ChatColor.GOLD+amount+ChatColor.WHITE+" Euro wurden eingezahlt!");
                            }
                            else{
                                commandSender.sendMessage("Es wurde nichts eingezahlt!");
                            }
                        }
                    }
                    if(strings[0].equalsIgnoreCase("auszahlen")){
                        String bankname = strings[1];
                        String amount = strings[2];
                        if(bankStream.hasAccses(bankname, commandSender.getName()) && bankStream.payOut(bankname, amount)){
                            commandSender.sendMessage(ChatColor.GOLD+amount+ChatColor.WHITE+" Euro wurden ausgezahlt!");
                        }
                        else{
                            commandSender.sendMessage("Es wurde nichts ausgezahlt!");
                        }
                    }
                    break;
            }
        }
        return false;
    }
}
