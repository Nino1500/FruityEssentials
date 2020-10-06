package net.fruchtlabor.fruityessentials.utils;


import net.fruchtlabor.fruityessentials.FruityEssentials;
import net.fruchtlabor.fruityessentials.commands.TeleportCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Teleport Suspension Class
 */
public class TeleportSuspension {
    private static final int SUSPENSTION_TIME = 60;
    private boolean running;
    private int taskId;
    private Player requester;
    private Player target;

    /**
     * Constructor
     * @param requester Requester Player
     * @param target Target Player
     */
    public TeleportSuspension(Player requester, Player target) {
        setRequester(requester);
        setTarget(target);
    }

    /**
     * Constructor
     * @param target Target Player
     */
    public TeleportSuspension(Player target) {
        setTarget(target);
    }

    /**
     * Start Suspension Thread
     */
    synchronized public void start() {
        if (running) return;
        running = true;
        Bukkit.getServer().getScheduler().runTaskAsynchronously(FruityEssentials.getInstance(), new Runnable() {
            @Override
            public void run() {
                for (int i = SUSPENSTION_TIME; i >= 0; i--) {
                    if (!running)return;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i <= 0) {
                        stop();
                        TeleportCommand.tpas.remove(target);
                        return;
                    }
                    ChatAPI.sendMessage(requester, FruityEssentials.messages.getMessage("tpaSuspensionCounter").replaceAll("%n", String.valueOf(i)));
                    Debug.print(FruityEssentials.messages.getMessage("tpaSuspensionCounter").replaceAll("%n", String.valueOf(i)));


                }
            }
        });
    }

    /**
     * Stop Suspension Thread
     */
    public void stop() {
        running = false;
    }

    /**
     * Set Requester
     * @param requester Requester Player
     */
    public void setRequester(Player requester) {
        this.requester = requester;
    }

    /**
     * Set Target
     * @param target Target Player
     */
    public void setTarget(Player target) {
        this.target = target;
    }

    /**
     * Set Running
     * @param running true when running, false when stopping
     */
    public void setRunning(boolean running) {
        this.running = running;
    }
}
