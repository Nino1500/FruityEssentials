package net.fruchtlabor.fruityessentials.data;

import net.fruchtlabor.fruityessentials.database.DBController;
import org.bukkit.plugin.Plugin;

public class BankStream {

    private Plugin plugin;
    private DBController dbController;

    public BankStream(Plugin plugin) {
        this.plugin = plugin;
        dbController = new DBController(plugin);
    }

    public boolean createBank(String bankname){
        if(checkBankExists(bankname)){

        }
        return false;
    }
    public boolean checkBankExists(String bankname){

        return false;
    }
    public boolean getBankBalance(String bankname){
        if(checkBankExists(bankname)){

        }
        return false;
    }
    public boolean inviteToBank(String bankname, String owner, String user){
        if(checkBankExists(bankname)){

        }
        return false;
    }
    public boolean allowBankUse(String bankname, String owner, String user){
        if(checkBankExists(bankname)){

        }
        return false;
    }
    public boolean isOwner(String bankname, String owner){
        if(checkBankExists(bankname)){

        }
        return false;
    }
    public boolean hasAccses(String bankname, String player){
        if(checkBankExists(bankname)){

        }
        return false;
    }
    public String getBankOwner(String bankname){
        if(checkBankExists(bankname)){

        }
        return null;
    }
    public boolean payIn(String bankname, String amount){
        if(checkBankExists(bankname)){

        }
        return false;
    }
    public boolean payOut(String bankname, String amount){
        if(checkBankExists(bankname)){

        }
        return false;
    }
}
