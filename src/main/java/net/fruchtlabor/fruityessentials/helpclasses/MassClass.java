package net.fruchtlabor.fruityessentials.helpclasses;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

//This Class uses to make code less redundant and simplify pretty much the whole project
public class MassClass {

    public MassClass() {
    }

    //We use Luckperms, so if u want to add a permission just use this method and give the fe.name permission and the player to it
    public void giveLuckPerm(String perm, Player player){
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user "+player.getName()+" permission set "+perm);
    }

    //Has all Commandnames in it, if you need the perm, just add fe.'name' to it!
    public ArrayList<String> getAllCommands(){ // TODO: Immer aktualisieren
        ArrayList<String> list = new ArrayList<>();
        list.add("help");
        list.add("fly");
        list.add("tp");
        return list;
    }

    //Returns all commandnames based of the permission the player has (every commandname has a permission note that)
    public ArrayList<String> getAllCommandsofPlayer(Player player){
        ArrayList<String> list = getAllCommands();
        ArrayList<String> playerhaslist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if(player.hasPermission("fe."+list.get(i))){
                playerhaslist.add(list.get(i));
            }
        }
        return playerhaslist;
    }
}
