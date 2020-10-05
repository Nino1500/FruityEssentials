package net.fruchtlabor.fruityessentials.data;

import net.fruchtlabor.fruityessentials.database.DBContext;
import net.fruchtlabor.fruityessentials.database.DBMoney;
import org.bukkit.plugin.Plugin;

public class BankStream {

    DBContext dbContext = new DBContext("localhost", 3306, "mc", "mc", "money_db");
    DBMoney dbMoney = new DBMoney(dbContext);

    public BankStream() {

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
