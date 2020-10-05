package net.fruchtlabor.fruityessentials;

import net.fruchtlabor.fruityessentials.commands.Bank;
import net.fruchtlabor.fruityessentials.commands.Money;
import net.fruchtlabor.fruityessentials.database.DBController;
import net.fruchtlabor.fruityessentials.database.Database;
import net.fruchtlabor.fruityessentials.listeners.Join;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class FruityEssentials extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");

    private static FruityEssentials instance = null;

    public static FruityEssentials getInstance() {
        return instance;
    }


    private Database db;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {

        this.db = new DBController.SQLite(this);
        this.db.load();

        this.getCommand("money").setExecutor(new Money(this));
        this.getCommand("bank").setExecutor(new Bank(this));

        this.getServer().getPluginManager().registerEvents(new Join(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }

}
