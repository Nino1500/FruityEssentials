package net.fruchtlabor.fruityessentials.listeners;

import net.fruchtlabor.fruityessentials.utils.WorldManager;
import org.bukkit.World;

import java.util.ArrayList;

public abstract class PreventListener {
    protected ArrayList<World> enabledWorlds = new ArrayList<>();

    public PreventListener(String path) {
        enabledWorlds = WorldManager.getWorldsFromConfigList(path);
    }
}
