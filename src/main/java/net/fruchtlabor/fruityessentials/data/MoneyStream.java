package net.fruchtlabor.fruityessentials.data;

import net.fruchtlabor.fruityessentials.database.DBController;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class MoneyStream {
    Plugin plugin;

    public MoneyStream(Plugin plugin) {
        this.plugin = plugin;
    }

    DBController dbController = new DBController(plugin);

    public double getBalance(String player){
        try {
            return dbController.getTokens(player);
        }catch (NullPointerException e){
            Objects.requireNonNull(Bukkit.getPlayer(player)).sendMessage("Du hast noch kein Geld verdient!");
        }
        return 0.0;
    }
    public void setBalance(String player, double am){
        dbController.setTokens(Objects.requireNonNull(Bukkit.getPlayer(player)), am);
    }
    public void giveMoney(String player, double am){
        dbController.modifyTokens(Objects.requireNonNull(Bukkit.getPlayer(player)), am);
    }
    public boolean deleteMoney(String player, double am){
        return dbController.modifyTokenNegative(Objects.requireNonNull(Bukkit.getPlayer(player)), am);
    }
    public ArrayList<String> getTopBalance(int rows){
        HashMap<String, Double> map = dbController.getAllTokens();
        map = sortByValue(map);
        ArrayList<String> list = new ArrayList<>();
        int i = 1;
        for (Map.Entry<String, Double> entry : map.entrySet()){
            while (i++ <= rows){
                list.add("["+i+"] "+entry.getKey()+" - "+entry.getValue());
            }
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
