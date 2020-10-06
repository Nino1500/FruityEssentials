package net.fruchtlabor.fruityessentials.utils;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Message Handler Class
 */
public class MessageHandler {
    public FileConfiguration config;
    public Messages messages;

    /**
     * Constructor
     * @param config Configuration
     */
    public MessageHandler(FileConfiguration config){
        this.config = config;
    }

    /**
     * Constructor
     * @param messages Instance of Messages Class
     */
    public MessageHandler(Messages messages){
        this.messages = messages;
    }

    /**
     * Get String
     * @param s Config Path
     * @return String
     */
    public String getString(String s){
        if(config != null){
            return config.getString(s);
        }
        if(messages != null){
            return messages.getMessage(s);
        }
        return null;
    }
}
