package net.fruchtlabor.fruityessentials.database;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class DBController extends Database {

    public DBController(Plugin instance) {
        super(instance);
    }

    @Override
    public Connection getSQLConnection() {
        return null;
    }

    @Override
    public void load() {

    }

    public static class SQLite extends Database {
        String dbname;

        public SQLite(Plugin instance) {
            super(instance);
            dbname = "money_db";
        }

        public String SQLiteCreateTokensTable = "CREATE TABLE IF NOT EXISTS money_table (" +
                "`player` varchar(32) NOT NULL," +
                "`money` double(11) NOT NULL," +
                "PRIMARY KEY (`player`)" +
                ");";

        public String SQLiteCreateBanksTable = "CREATE TABLE IF NOT EXISTS bank_table (" +
                "`owner` varchar(32) NOT NULL," +
                "`bankname` varchar(32) NOT NULL," +
                "`players` varchar(32) NOT NULL," +
                "PRIMARY KEY (`bankname`)" +
                ");";

        public Connection getSQLConnection() {
            File dataFolder = new File(plugin.getDataFolder()+"money_db.db");
            if (!dataFolder.exists()) {
                try {
                    dataFolder.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //plugin.saveResource(dbname+".db", false);
            }
            try {
                if (connection != null && !connection.isClosed()) {
                    return connection;
                }
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
                return connection;
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, "SQLite exception on initialize", ex);
            } catch (ClassNotFoundException ex) {
                plugin.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
            }
            return null;
        }

        public void load() {
            connection = getSQLConnection();
            try {
                Statement s = connection.createStatement();
                s.executeUpdate(SQLiteCreateTokensTable);
                s.close();
                s = connection.createStatement();
                s.executeUpdate(SQLiteCreateBanksTable);
                s.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            initialize();
        }
    }
}
