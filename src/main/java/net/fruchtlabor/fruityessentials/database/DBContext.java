package net.fruchtlabor.fruityessentials.database;

import java.sql.*;

public class DBContext {
    private String connectionString;
    private Connection connection;

    public DBContext(String host, int port, String user, String pass, String database) {
        connectionString = "jdbc:mysql://"+host+":"+port+"/"+database+"?user="+user+"&password="+pass;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the connection is established
     * @return database connected
     */
    public boolean isConnected() {
        return connection != null;
    }


    /**
     * Returns a SQL statement
     * @return SQL Statement
     */
    public Statement getStatement() {
        if (isConnected()) {
            try {
                return connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        if (isConnected()) {
            return connection.prepareStatement(sql);
        }
        return null;
    }
}
