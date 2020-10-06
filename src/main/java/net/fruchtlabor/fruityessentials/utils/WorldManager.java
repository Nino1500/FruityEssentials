package net.fruchtlabor.fruityessentials.utils;

import net.fruchtlabor.fruityessentials.FruityEssentials;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * World Manager
 */
public class WorldManager {
    private World world;
    private File worldFolder;



    /**
     * Constructor
     * @param world World
     */
    public WorldManager(World world) {
        setWorld(world);
        setWorldFolder(world.getWorldFolder());
    }

    public static ArrayList<World> getWorldsFromConfigList(String path) {
        List<String> worldStringList = FruityEssentials.getInstance().getConfig().getStringList(path);
        ArrayList<World> worlds = new ArrayList<>();
        for (String worldString : worldStringList) {
            worlds.add(Bukkit.getWorld(worldString));
        }
        return worlds;
    }

    /**
     * Set World
     * @param world World
     */
    public void setWorld(World world) {
        if (world == null) return;
        this.world = world;
    }

    /**
     * Set World Folder
     * @param worldFolder Folder
     */
    public void setWorldFolder(File worldFolder) {
        if (worldFolder == null) return;
        this.worldFolder = worldFolder;
    }

    /**
     * Get World
     * @return World
     */
    public World getWorld() {
        return world;
    }

    /**
     * Get World Folder
     * @return World Folder
     */
    public File getWorldFolder() {
        return worldFolder;
    }


    /**
     * Unload World
     */
    public void unloadWorld() {
        if (world != null) {
            Bukkit.getServer().unloadWorld(world, true);
        }
    }

    /**
     * Delete World
     * @param path World Folder
     * @return true if deleted, false if an error occurred
     */
    public boolean deleteWorld(File path) {
        if(path.exists()) {
            File files[] = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return(path.delete());
    }
}
