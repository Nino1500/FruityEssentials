package net.fruchtlabor.fruityessentials.utils;

import net.fruchtlabor.fruityessentials.FruityEssentials;
import org.bukkit.ChatColor;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Messages Class
 */
public class Messages {
    private HashMap<String,String> messages = new HashMap<>();

    /**
     * Constructor
     */
    public Messages() {
        Debug.print("Loading Messages...");
        for(String key : getMessages()){
            if(!FruityEssentials.getInstance().getConfig().contains("Messages."+key)){ Debug.print("Warning: Messages."+key+" is empty!"); FruityEssentials.getInstance().getConfig().set("Messages."+key,""); FruityEssentials.getInstance().saveConfig(); FruityEssentials.getInstance().reloadConfig(); }
            messages.put(key,FruityEssentials.getInstance().getConfig().getString("Messages."+key));
        }
        Debug.print(messages.size()+" Messages loaded.");
    }

    /**
     * Get all Messages
     * @return List of String
     */
    private ArrayList<String> getMessages(){
        Debug.print("getMessages()");
        ArrayList<String> temp = new ArrayList<>();
        //Add Messages below//
        temp.add("prefix");
        temp.add("noPermission");
        temp.add("missingParameters");
        temp.add("notSupported");
        temp.add("join");
        temp.add("quit");
        temp.add("mwListBegin");
        temp.add("mwListItem");
        temp.add("mwListEnd");
        temp.add("mwWorldNotFound");
        temp.add("mwWorldDeleted");
        temp.add("mwWorldCouldNotBeDeleted");
        temp.add("mwTeleported");
        temp.add("playerOffline");
        temp.add("weatherSetSun");
        temp.add("weatherSetStorm");
        temp.add("gameModeChanged");
        temp.add("gameModeChangedOther");
        temp.add("tpTeleported");
        temp.add("tpTeleportedOther");
        temp.add("tpaSuspensionCounter");
        temp.add("tpaSuspended");
        temp.add("tpaDeclined");
        temp.add("tpaRequestBegin");
        temp.add("tpaRequestEnd");
        temp.add("tpaHereRequestBegin");
        temp.add("tpaHereRequestEnd");
        temp.add("tpaSent");
        temp.add("spawnTeleported");
        temp.add("spawnNotSet");
        temp.add("spawnSubTitle");
        temp.add("spawnSet");
        temp.add("stopSpamming");
        temp.add("flyingSpeed");
        temp.add("walkingSpeed");
        temp.add("warpTeleported");
        temp.add("warpNotExist");
        temp.add("setWarpExist");
        temp.add("setWarpDone");
        temp.add("flyOff");
        temp.add("flyOn");
        temp.add("kitCreated");
        for(EntityDamageEvent.DamageCause c : EntityDamageEvent.DamageCause.values()){
            temp.add("playerDeath_"+c.name());
        }
        //End of Messages//
        Debug.print(temp.size()+" Messages added.");
        return temp;
    }

    /**
     * Get Message
     * @param string Name
     * @return Message
     */
    public String getMessage(String string){
        Debug.print("Get Message "+string);
        if(messages.containsKey(string)) {
            return messages.get(string);
        } else {
            return ChatColor.RED+"Facepalm: Entwickler schreibt Nachricht falsch oder vergisst sie einzutragen xD "+ChatColor.AQUA+"("+string+")";
            }
    }
}
