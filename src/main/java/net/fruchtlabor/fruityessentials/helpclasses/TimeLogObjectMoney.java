package net.fruchtlabor.fruityessentials.helpclasses;


import java.sql.Timestamp;

public class TimeLogObjectMoney {

    private String sender;
    private String receiver;
    private Timestamp timestamp;
    private double money;
    private String trans_type;

    public TimeLogObjectMoney(String sender, String receiver, Timestamp timestamp, double money, String trans_type) {
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = timestamp;
        this.money = money;
        this.trans_type = trans_type; //sent and received
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public double getMoney() {
        return money;
    }

    public String getTrans_type() {
        return trans_type;
    }
}
