package net.fruchtlabor.fruityessentials.data;

import net.fruchtlabor.fruityessentials.database.DBContext;
import net.fruchtlabor.fruityessentials.helpclasses.TimeLogObjectMoney;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MoneyLoggerStream {

    DBContext dbContext = null;

    public MoneyLoggerStream(DBContext dbContext) {
        this.dbContext = dbContext;
    }

    public void createMoneyLogEntry(Player sender, Player receiver, Timestamp timestamp, double money, String transaction_type){

        PreparedStatement ps = null;

        try {
            String sql = "INSERT INTO log_table (sender,receiver,time_stamp,money,trans_type) VALUES (?,?,?,?,?)";
            ps = dbContext.getPreparedStatement(sql);
            ps.setString(1, sender.getUniqueId().toString());
            ps.setString(2, receiver.getUniqueId().toString());
            ps.setTimestamp(3, timestamp);
            ps.setDouble(4, money);
            ps.setString(5, transaction_type);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<TimeLogObjectMoney> getLogAsSender(Player sender){
        String sql = "SELECT * FROM log_table WHERE sender = "+sender;
        PreparedStatement ps = null;
        try {
            ps = dbContext.getPreparedStatement(sql);
            ResultSet rs = ps.executeQuery();
            ArrayList<TimeLogObjectMoney> list = new ArrayList<>();
            while(rs.next()){
                if(rs.getString("sender").equalsIgnoreCase(sender.getUniqueId().toString())){
                    list.add(new TimeLogObjectMoney(rs.getString("sender"), rs.getString("receiver"), rs.getTimestamp("time_stamp"),rs.getDouble("money"), rs.getString("trans_type")));
                }
            }
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public ArrayList<TimeLogObjectMoney> getLogAsReceiver(Player receiver){
        String sql = "SELECT * FROM log_table WHERE sender = "+receiver;
        PreparedStatement ps = null;
        try {
            ps = dbContext.getPreparedStatement(sql);
            ResultSet rs = ps.executeQuery();
            ArrayList<TimeLogObjectMoney> list = new ArrayList<>();
            while(rs.next()){
                if(rs.getString("sender").equalsIgnoreCase(receiver.getUniqueId().toString())){
                    list.add(new TimeLogObjectMoney(rs.getString("sender"), rs.getString("receiver"), rs.getTimestamp("time_stamp"),rs.getDouble("money"), rs.getString("trans_type")));
                }
            }
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public double getAllSendedMoney(Player sender){ //sended and received
        String sql = "SELECT * FROM log_table WHERE sender = "+sender;
        PreparedStatement ps = null;
        try {
            ps = dbContext.getPreparedStatement(sql);
            ResultSet rs = ps.executeQuery();
            double amount = 0.0;
            while(rs.next()){
                if(rs.getString("sender").equalsIgnoreCase(sender.getUniqueId().toString()) && rs.getString("trans_type").equalsIgnoreCase("sended")){
                    amount+=rs.getDouble("money");
                }
            }
            return amount;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0.0;
    }
    public double getAllReceivedMoney(Player receiver) {
        String sql = "SELECT * FROM log_table WHERE sender = " + receiver;
        PreparedStatement ps = null;
        try {
            ps = dbContext.getPreparedStatement(sql);
            ResultSet rs = ps.executeQuery();
            double amount = 0.0;
            while (rs.next()) {
                if (rs.getString("sender").equalsIgnoreCase(receiver.getUniqueId().toString()) && rs.getString("trans_type").equalsIgnoreCase("received")) {
                    amount += rs.getDouble("money");
                }
            }
            return amount;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0.0;
    }
}
