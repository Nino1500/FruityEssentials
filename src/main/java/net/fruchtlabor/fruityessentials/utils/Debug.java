package net.fruchtlabor.fruityessentials.utils;

import net.fruchtlabor.fruityessentials.FruityEssentials;

public class Debug {
    /**
     * If Debug is activated, log Message
     * @param s Message
     */
    public static void print(String s) {
        if (FruityEssentials.debug) {
            FruityEssentials.getInstance().getServer().getLogger().info(s);
        }
    }
}
