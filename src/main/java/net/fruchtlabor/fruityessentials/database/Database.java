package net.fruchtlabor.fruityessentials.database;

import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.*;
import java.util.HashMap;
import java.util.logging.Level;

public abstract class Database {
    Plugin plugin;
    Connection connection;
    // The name of the table we created back in SQLite class.
    public String table = "money_table";

    public Database(Plugin instance){
        plugin = instance;
    }

    public abstract Connection getSQLConnection();

    public abstract void load();

    public void initialize(){
        connection = getSQLConnection();
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " WHERE player = ?");
            ResultSet rs = ps.executeQuery();
            close(ps,rs);
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
        }
    }

    public double getTokens(String string) throws NullPointerException{
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM " + table + " WHERE player = '"+string+"';");

            rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("player").equalsIgnoreCase(string.toLowerCase())){
                    return rs.getDouble("money");
                }
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return 0;
    }

    public void setTokens(Player player, Double money) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            if(checkIfPlayerExists(player.getName().toLowerCase())){
                ps = conn.prepareStatement("REPLACE INTO " + table + " (player,money) VALUES(?,?)"); //SET MONEY (NOT ADD)
            }
            else{
                ps = conn.prepareStatement("INSERT INTO "+table+" (player,money) VALUES (?,?)");
            }
            ps.setString(1, player.getName().toLowerCase());
            ps.setDouble(2, money);
            ps.executeUpdate();
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
    }

    public boolean checkIfPlayerExists(String player){
        Connection conn = null;
        PreparedStatement ps = null;
        Statement st = null;
        conn = getSQLConnection();

        try {
            String sql = "SELECT * FROM money_table";
            //ps = conn.prepareStatement(sql);
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                if(rs.getString("player").equalsIgnoreCase(player.toLowerCase())){
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return false;
    }

    public void modifyTokens(Player player, Double money) { //ADD MONEY
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            double m = getTokens(player.getName().toLowerCase());
            double total = money + m;
            conn = getSQLConnection();
            ps = conn.prepareStatement("UPDATE "+table+" set money ? WHERE player = "+player.getName().toLowerCase());
            ps.setDouble(1, total);
            ps.executeUpdate();
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
    }

    public boolean modifyTokenNegative(Player player, Double money){ //DELETE SOME MONEY
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            double m = getTokens(player.getName().toLowerCase());
            double total = m - money;
            if(total<0){
                return false;
            }
            conn = getSQLConnection();
            ps = conn.prepareStatement("UPDATE "+table+" set money ? WHERE player = "+player.getName().toLowerCase());
            ps.setDouble(1, total);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return false;
    }

    public HashMap<String, Double> getAllTokens(){ //GET ALL BALANCES OF ALL PLAYERS
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM "+table);
            ps.executeQuery();
            rs = ps.executeQuery();
            HashMap<String, Double> map = new HashMap<>();
            while (rs.next()){
                map.put(rs.getString("player"), rs.getDouble("money"));
            }
            return map;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
                if (rs != null)
                    rs.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
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
            Error.close(plugin, ex);
        }
    }
}
