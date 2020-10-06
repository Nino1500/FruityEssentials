package net.fruchtlabor.fruityessentials.utils;

import net.fruchtlabor.fruityessentials.FruityEssentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatAPI {
    /**
     * Count the Number of capsed Chars in a String
     * @param s The String
     * @return The Number of capsed Chars
     */
    public static int capsedChars(String s){
        s = s.trim();
        int counter = 0;
        for(int i = 0; i<s.length(); i++){
            char c = s.charAt(i);
            if(Character.isUpperCase(c)){
                counter++;
            }
        }
        return counter;
    }

    /**
     * Translates Colorcode
     * @param s String with old Colorcode
     * @return Translated String
     */
    public static String translateColor(String s) {
        s = ChatColor.translateAlternateColorCodes('&', s);
        return s;
    }

    /**
     * Send Message with prefix to a Player
     * @param p Player
     * @param msg Message
     */
    public static void sendMessage(Player p, String msg) {
        p.sendMessage(FruityEssentials.messages.getMessage("prefix")+translateColor(msg));
    }

    /**
     * Send Message with prefix to Console
     * @param sender ConsoleSender
     * @param msg Message
     */
    public static void sendMessage(CommandSender sender, String msg) {
        sender.sendMessage(FruityEssentials.messages.getMessage("prefix")+translateColor(msg));
    }

    /**
     * Broadcast Message without Prefix
     * @param msg Message
     */
    public static void broadcastMessage(String msg) {
        Bukkit.broadcastMessage(translateColor(msg));
    }

    /**
     * Broadcast Message with Prefix
     * @param msg Message
     */
    public static void broadcastMessageWP(String msg){ broadcastMessage(FruityEssentials.messages.getMessage("prefix")+msg);}

    /**
     * Send No Permission Message to a Player
     * @param p Player
     */
    public static void sendNoPermissionMessage(Player p){ sendMessage(p, FruityEssentials.messages.getMessage("noPermission")); }

    /**
     * Send No Permission Message to Console
     * @param sender ConsoleSender
     */
    public static void sendNoPermissionMessage(CommandSender sender){ sendMessage(sender, FruityEssentials.messages.getMessage("noPermission")); }
}
