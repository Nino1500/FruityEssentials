package net.fruchtlabor.fruityessentials;

import net.fruchtlabor.fruityessentials.commands.Bank;
import net.fruchtlabor.fruityessentials.commands.Money;
import net.fruchtlabor.fruityessentials.commands.TeleportCommand;
import net.fruchtlabor.fruityessentials.commands.WeatherCommand;
import net.fruchtlabor.fruityessentials.database.DBContext;
import net.fruchtlabor.fruityessentials.database.DBMoney;
import net.fruchtlabor.fruityessentials.listeners.*;
import net.fruchtlabor.fruityessentials.utils.Debug;
import net.fruchtlabor.fruityessentials.utils.Messages;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public final class FruityEssentials extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");

    // instance reference
    private static FruityEssentials instance = null;

    // Configuration
    public static boolean debug = true;
    public static Messages messages;

    public static FruityEssentials getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {

        initConfig();
        debug = this.getConfig().getBoolean("debug");

        this.getCommand("money").setExecutor(new Money(this));
        this.getCommand("bank").setExecutor(new Bank(this));
        this.getCommand("weather").setExecutor(new WeatherCommand());
        this.getCommand("tp").setExecutor(new TeleportCommand());

        this.getServer().getPluginManager().registerEvents(new Join(this), this);
        this.getServer().getPluginManager().registerEvents(new DamageListener(), this);
        this.getServer().getPluginManager().registerEvents(new DeathListener(), this);
        this.getServer().getPluginManager().registerEvents(new ExplosionListener(), this);
        this.getServer().getPluginManager().registerEvents(new FieldDestroyListener(), this);
        this.getServer().getPluginManager().registerEvents(new FireSpreadListener(), this);
        this.getServer().getPluginManager().registerEvents(new FoodLevelListener(), this);
        this.getServer().getPluginManager().registerEvents(new LeaveDecayListener(), this);
        this.getServer().getPluginManager().registerEvents(new MobSpawnListener(), this);
        this.getServer().getPluginManager().registerEvents(new PluginProtectionListener(), this);
        this.getServer().getPluginManager().registerEvents(new QuitListener(), this);
        this.getServer().getPluginManager().registerEvents(new VoidEnterListener(), this);
        this.getServer().getPluginManager().registerEvents(new WeatherChangeListener(), this);
        this.getServer().getPluginManager().registerEvents(new XPDropListener(), this);

        DBContext dbContext = new DBContext("49.12.124.252", 3306, "minecraft", "Test99?!", "money_db");
        createBankTable(dbContext);
        createMoneyTable(dbContext);
        createLogTable(dbContext);
    }

    /**
     * Initialize config.yml
     */
    public void initConfig() {
        File f = new File(getDataFolder()+ File.separator+ "config.yml");
        if(!f.exists())
        {
            this.saveDefaultConfig();
            Debug.print("Saved Default Config");
        }
        this.reloadConfig();
    }

    synchronized private void createMoneyTable(DBContext dbContext) {
        String sql = "CREATE TABLE IF NOT EXISTS money_table (player VARCHAR(128), money DOUBLE(10,2));";
        try (Statement stmnt = dbContext.getStatement()) {
            stmnt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    synchronized private void createBankTable(DBContext dbContext) {
        String sql = "CREATE TABLE IF NOT EXISTS bank_table (owner VARCHAR(128), players varchar(540), money DOUBLE(10,2));"; //MAX 10 people per bank
        try (Statement stmnt = dbContext.getStatement()) {
            stmnt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    synchronized private void createLogTable(DBContext dbContext){
        String sql = "CREATE TABLE IF NOT EXISTS log_table (sender VARCHAR(128), receiver VARCHAR(128), time_stamp TIMESTAMP, money DOUBLE, trans_type VARCHAR(16))";
        try (Statement stmt = dbContext.getStatement()){
            stmt.execute(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }

}
