package net.fruchtlabor.fruityessentials.data;

import net.fruchtlabor.fruityessentials.database.DBContext;
import net.fruchtlabor.fruityessentials.database.DBMoney;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class MoneyStream {

    DBContext dbContext = new DBContext("49.12.124.252", 3306, "minecraft", "Test99?!", "money_db");
    DBMoney dbMoney = new DBMoney(dbContext);

    public double getBalance(Player player){
        try {
            return dbMoney.getTokens(player);
        }catch (NullPointerException e){
            player.sendMessage(ChatColor.DARK_GREEN+"[Geld] "+ChatColor.GRAY+"Du hast noch kein Geld verdient!");
        }
        return 0.0;
    }

    public void setBalance(Player player, double am){
        dbMoney.setTokens(Objects.requireNonNull(player), am);
    }

    public void giveMoney(Player player, double am){
        dbMoney.modifyTokens(Objects.requireNonNull(player), am);
    }

    public boolean deleteMoney(Player player, double am){
        return dbMoney.modifyTokenNegative(Objects.requireNonNull(player), am);
    }



    public ArrayList<String> getTopBalance(int rows){
        HashMap<String, Double> map = dbMoney.getAllTokens();
        map = sortByValue(map);
        ArrayList<String> list = new ArrayList<>();
        int i = 0;
        for (Map.Entry<String, Double> entry : map.entrySet()){
            i++;
            String uuid = entry.getKey();
            Player player = Bukkit.getPlayer(UUID.fromString(uuid)); //USE THIS //TODO IMPORTANT FFS DONT KNOW WHY BUT USE IT
            if(player != null){
                list.add(
                        ChatColor.BOLD+"" +
                                ChatColor.GOLD+ "["+i+"] "+
                                ChatColor.WHITE+
                                player.getName()+
                                ChatColor.DARK_GRAY+" - "+
                                ChatColor.DARK_GREEN+
                                entry.getValue()+
                                "");
            }
            /*while (i++ <= rows){
                String uuid = entry.getKey();
                Player player = Bukkit.getPlayer(uuid);
                assert player != null;
                list.add(ChatColor.BOLD+""+ChatColor.GOLD+
                        "["+i+"] " + ChatColor.WHITE+
                      player.getName()+ChatColor.GRAY+" - "+ChatColor.GREEN+entry.getValue());
            }*/
        }
        return list;
    }

    public HashMap<String, Double> sortByValue(HashMap<String, Double> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Double> > list =
                new LinkedList<Map.Entry<String, Double> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Double> >() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Double> temp = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
