package com.xSavior_of_God.HappyNewYear.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * <h1>onFireworkEvent</h1>
 *
 * @author xSavior_of_God
 * @version 1.0
 * @since 2021-01-01
 */
public class onFireworkEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private Player PLAYER;
    private boolean isCancelled = false;

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public onFireworkEvent(Player PLAYER) {
        this.PLAYER = PLAYER;
    }

    /**
     * Returns the Player
     *
     * @return Player
     */
    public Player getPlayer() {
        return PLAYER;
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

}
