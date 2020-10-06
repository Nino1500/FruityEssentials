package net.fruchtlabor.fruityessentials.utils;

import org.bukkit.entity.Player;

/**
 * Teleport Request Class
 */
public class TeleportRequest {
    private TeleportSuspension suspension;
    private Player requester;
    private Player target;

    /**
     * Constructor
     * @param requester Requester Player
     * @param target Target Player
     */
    public TeleportRequest(Player requester, Player target) {
        setRequester(requester);
        setTarget(target);
        suspension = new TeleportSuspension(requester, target);
        suspension.start();
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
     * Get Teleport Suspension
     * @return Teleport Suspension
     */
    public TeleportSuspension getTeleportSuspension() {
        return suspension;
    }

    /**
     * Get Requester
     * @return Requester Player
     */
    public Player getRequester() {
        return requester;
    }

    /**
     * Get Target
     * @return Target Player
     */
    public Player getTarget() {
        return target;
    }
}
