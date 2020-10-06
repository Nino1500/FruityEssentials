package net.fruchtlabor.fruityessentials.database;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.HashMap;

public class DBMoney {
    private DBContext dbContext;

    private final String TABLE = "money_table";

    public DBMoney(DBContext dbContext) {
        this.dbContext = dbContext;
    }

    public double getTokens(Player player) throws NullPointerException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = dbContext.getPreparedStatement("SELECT * FROM money_table WHERE player = ?");
            ps.setString(1, player.getUniqueId().toString());
            rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("player").equalsIgnoreCase(player.getUniqueId().toString())){
                    return rs.getDouble("money");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return 0;
    }

    public void createLogEntry(){

    }

    public void setTokens(Player player, Double money) {
        PreparedStatement ps = null;
        try {
            if(checkIfPlayerExists(player)){
                ps = dbContext.getPreparedStatement("UPDATE money_table set money = "+money+" WHERE player = ?");
                ps.setString(1, player.getUniqueId().toString());
            }
            else{
                ps = dbContext.getPreparedStatement("INSERT INTO money_table (player,money) VALUES(?,?)");
                ps.setString(1, player.getUniqueId().toString());
                ps.setDouble(2, money);
            }
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean checkIfPlayerExists(Player player){
        try {
            Statement st = dbContext.getStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM "+TABLE);

            while (rs.next()){
                if(rs.getString("player").equalsIgnoreCase(player.getUniqueId().toString())){
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void modifyTokens(Player player, Double money) { //ADD MONEY
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            double m = getTokens(player);
            double total = money + m;
            ps = dbContext.getPreparedStatement("UPDATE money_table set money ? WHERE player = ?");
            ps.setDouble(1, total);
            ps.setString(2, player.getUniqueId().toString());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean modifyTokenNegative(Player player, Double money){ //DELETE SOME MONEY
        PreparedStatement ps = null;
        try {
            double m = getTokens(player);
            double total = m - money;
            if(total<0){
                return false;
            }
            ps = dbContext.getPreparedStatement("UPDATE money_table set money ? WHERE player = ?");
            ps.setDouble(1, total);
            ps.setString(2, player.getName().toLowerCase());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public HashMap<String, Double> getAllTokens(){ //GET ALL BALANCES OF ALL PLAYERS
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = dbContext.getPreparedStatement("SELECT * FROM "+TABLE);
            rs = ps.executeQuery();
            HashMap<String, Double> map = new HashMap<>();
            while (rs.next()){
                map.put(rs.getString("player"), rs.getDouble("money"));
            }
            return map;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (rs != null)
                    rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public void close(PreparedStatement ps,ResultSet rs){
        try {
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
