package net.fruchtlabor.fruityessentials.database;

import net.fruchtlabor.fruityessentials.FruityEssentials;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.HashMap;
import java.util.logging.Level;

public class DBMoney {
    private DBContext dbContext;

    private final String TABLE = "money_TABLE";

    public DBMoney(DBContext dbContext) {
        this.dbContext = dbContext;
    }

    public void initialize(){
        try{
            PreparedStatement ps = dbContext.getPreparedStatement("SELECT * FROM " + TABLE + " WHERE player = ?");
            ResultSet rs = ps.executeQuery();
            close(ps,rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public double getTokens(String string) throws NullPointerException{
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = dbContext.getPreparedStatement("SELECT * FROM " + TABLE + " WHERE player = '"+string+"';");

            rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("player").equalsIgnoreCase(string.toLowerCase())){
                    return rs.getDouble("money");
                }
            }
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
        return 0;
    }

    public void setTokens(Player player, Double money) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            if(checkIfPlayerExists(player.getName().toLowerCase())){
                ps = dbContext.getPreparedStatement("REPLACE INTO " + TABLE + " (player,money) VALUES(?,?)"); //SET MONEY (NOT ADD)
            }
            else{
                ps = dbContext.getPreparedStatement("INSERT INTO "+TABLE+" (player,money) VALUES (?,?)");
            }
            ps.setString(1, player.getName().toLowerCase());
            ps.setDouble(2, money);
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

    public boolean checkIfPlayerExists(String player){
        Connection conn = null;
        PreparedStatement ps = null;
        Statement st = null;

        try {
            String sql = "SELECT * FROM money_TABLE";
            //ps = dbContext.getPreparedStatement(sql);
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
                ex.printStackTrace();
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
            ps = dbContext.getPreparedStatement("UPDATE "+TABLE+" set money ? WHERE player = "+player.getName().toLowerCase());
            ps.setDouble(1, total);
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
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            double m = getTokens(player.getName().toLowerCase());
            double total = m - money;
            if(total<0){
                return false;
            }
            ps = dbContext.getPreparedStatement("UPDATE "+TABLE+" set money ? WHERE player = "+player.getName().toLowerCase());
            ps.setDouble(1, total);
            ps.executeUpdate();
            return true;
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
        return false;
    }

    public HashMap<String, Double> getAllTokens(){ //GET ALL BALANCES OF ALL PLAYERS
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = dbContext.getPreparedStatement("SELECT * FROM "+TABLE);
            ps.executeQuery();
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
                if (conn != null)
                    conn.close();
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
