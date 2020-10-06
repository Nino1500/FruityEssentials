package net.fruchtlabor.fruityessentials.commands;

import net.fruchtlabor.fruityessentials.FruityEssentials;
import net.fruchtlabor.fruityessentials.utils.ChatAPI;
import net.fruchtlabor.fruityessentials.utils.TeleportRequest;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Teleport Command
 */
public class TeleportCommand implements CommandExecutor {
    public static HashMap<Player, TeleportRequest> tpas = new HashMap<>();


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("tp")) {
            // --- /tp <player>
            if (args.length == 1) {
                Player p = (Player) sender;
                if (args[0].equalsIgnoreCase("help")) {
                    ChatAPI.sendMessage(sender, "§7/tp <spieler>");
                    ChatAPI.sendMessage(sender, "§7/tp <spieler> <spieler>");
                    return true;
                }
                if (!(sender instanceof Player)) {
                    ChatAPI.sendMessage(sender, FruityEssentials.messages.getMessage("notSupported"));
                    return true;
                }
                if (Bukkit.getPlayer(args[0]) == null) {
                    ChatAPI.sendMessage(sender, FruityEssentials.messages.getMessage("playerOffline"));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                p.teleport(target.getLocation());
                ChatAPI.sendMessage(p, FruityEssentials.messages.getMessage("tpTeleported").replaceAll("%n", target.getName()));
                return true;
            }
            // --- /tp <player> <player>
            if (args.length == 2) {
                if (Bukkit.getPlayer(args[0]) == null) {
                    ChatAPI.sendMessage(sender, FruityEssentials.messages.getMessage("playerOffline").replaceAll("%n", args[0]));
                    return true;
                }
                Player target1 = Bukkit.getPlayer(args[0]);
                if (Bukkit.getPlayer(args[1]) == null) {
                    ChatAPI.sendMessage(sender, FruityEssentials.messages.getMessage("playerOffline").replaceAll("%n", args[1]));
                    return true;
                }
                Player target2 = Bukkit.getPlayer(args[1]);
                target1.teleport(target2);
                ChatAPI.sendMessage(sender, FruityEssentials.messages.getMessage("tpTeleportedOther").replaceAll("%n", target1.getName()).replaceAll("%m", target2.getName()));
                ChatAPI.sendMessage(target1, FruityEssentials.messages.getMessage("tpTeleported").replaceAll("%n", target2.getName()));
                return true;
            }
            ChatAPI.sendMessage(sender, "§7/tp <spieler>");
            ChatAPI.sendMessage(sender, "§7/tp <spieler> <spieler>");
        }
        if (cmd.getName().equalsIgnoreCase("tpa")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("help")) {
                    ChatAPI.sendMessage(sender, "§7/tpa <spieler>");
                    return true;
                }
                if (!(sender instanceof Player)) {
                    ChatAPI.sendMessage(sender, FruityEssentials.messages.getMessage("notSupported"));
                    return true;
                }
                Player p = (Player) sender;
                if (args[0].equalsIgnoreCase("accept")) {
                    if (!tpas.containsKey(p)) {
                        ChatAPI.sendMessage(p, FruityEssentials.messages.getMessage("tpaSuspended"));
                        return true;
                    }
                    Player requester = tpas.get(p).getRequester();
                    tpas.get(p).getTeleportSuspension().setRunning(false);
                    requester.teleport(p.getLocation());
                    ChatAPI.sendMessage(requester, FruityEssentials.messages.getMessage("tpTeleported").replaceAll("%n", p.getName()));
                    tpas.remove(p);
                    return true;
                }
                if (args[0].equalsIgnoreCase("decline")) {
                    if (!tpas.containsKey(p)) {
                        ChatAPI.sendMessage(p, FruityEssentials.messages.getMessage("tpaSuspended"));
                        return true;
                    }
                    Player requester = tpas.get(p).getRequester();
                    tpas.get(p).getTeleportSuspension().setRunning(false);
                    tpas.remove(p);
                    ChatAPI.sendMessage(p, FruityEssentials.messages.getMessage("tpaDeclined"));
                    ChatAPI.sendMessage(requester, FruityEssentials.messages.getMessage("tpaDeclined"));
                    return true;
                }
                if (Bukkit.getPlayer(args[0]) == null) {
                    ChatAPI.sendMessage(p, FruityEssentials.messages.getMessage("playerOffline").replaceAll("%n", args[0]));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                TeleportRequest request = new TeleportRequest(p, target);
                tpas.put(target, request);

                TextComponent accept = new TextComponent("\t"+"\t"+"Annehmen");
                accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/tpa accept"));
                accept.setColor(ChatColor.GREEN);
                accept.setBold(true);
                TextComponent decline = new TextComponent("\n"+"\t"+"\t"+"Ablehnen");
                decline.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/tpa decline"));
                decline.setColor(ChatColor.RED);
                decline.setBold(true);
                accept.addExtra(decline);
                ChatAPI.sendMessage(target, FruityEssentials.messages.getMessage("tpaRequestBegin").replaceAll("%n", p.getName()));
                target.spigot().sendMessage(accept);
                ChatAPI.sendMessage(target, FruityEssentials.messages.getMessage("tpaRequestEnd"));
                ChatAPI.sendMessage(p, FruityEssentials.messages.getMessage("tpaSent").replaceAll("%n", target.getName()));
                return true;
            }
            ChatAPI.sendMessage(sender, "§7/tpa <spieler>");
        }
        if (cmd.getName().equalsIgnoreCase("tpahere")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("help")) {
                    ChatAPI.sendMessage(sender, "§7/tpahere <spieler>");
                    return true;
                }
                if (!(sender instanceof Player)) {
                    ChatAPI.sendMessage(sender, FruityEssentials.messages.getMessage("notSupported"));
                    return true;
                }
                Player p = (Player) sender;
                if (args[0].equalsIgnoreCase("accept")) {
                    if (!tpas.containsKey(p)) {
                        ChatAPI.sendMessage(p, FruityEssentials.messages.getMessage("tpaSuspended"));
                        return true;
                    }
                    Player requester = tpas.get(p).getRequester();
                    tpas.get(p).getTeleportSuspension().setRunning(false);
                    p.teleport(requester.getLocation());
                    ChatAPI.sendMessage(p, FruityEssentials.messages.getMessage("tpTeleported").replaceAll("%n", requester.getName()));
                    tpas.remove(p);
                    return true;
                }
                if (args[0].equalsIgnoreCase("decline")) {
                    if (!tpas.containsKey(p)) {
                        ChatAPI.sendMessage(p, FruityEssentials.messages.getMessage("tpaSuspended"));
                        return true;
                    }
                    Player requester = tpas.get(p).getRequester();
                    tpas.get(p).getTeleportSuspension().setRunning(false);
                    tpas.remove(p);
                    ChatAPI.sendMessage(p, FruityEssentials.messages.getMessage("tpaDeclined"));
                    ChatAPI.sendMessage(requester, FruityEssentials.messages.getMessage("tpaDeclined"));
                    return true;
                }
                if (Bukkit.getPlayer(args[0]) == null) {
                    ChatAPI.sendMessage(p, FruityEssentials.messages.getMessage("playerOffline").replaceAll("%n", args[0]));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                TeleportRequest request = new TeleportRequest(p, target);
                tpas.put(target, request);

                TextComponent accept = new TextComponent("\t"+"\t"+"Annehmen");
                accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/tpahere accept"));
                accept.setColor(ChatColor.GREEN);
                accept.setBold(true);
                TextComponent decline = new TextComponent("\n"+"\t"+"\t"+"Ablehnen");
                decline.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/tpahere decline"));
                decline.setColor(ChatColor.RED);
                decline.setBold(true);
                accept.addExtra(decline);
                ChatAPI.sendMessage(target, FruityEssentials.messages.getMessage("tpaHereRequestBegin").replaceAll("%n", p.getName()));
                target.spigot().sendMessage(accept);
                ChatAPI.sendMessage(target, FruityEssentials.messages.getMessage("tpaHereRequestEnd"));
                ChatAPI.sendMessage(p, FruityEssentials.messages.getMessage("tpaSent").replaceAll("%n", target.getName()));
                return true;
            }

        }
        return false;
    }

}
