package de.goldmensch.smartutils.event.movement;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SmartPlayerMoveEvent extends Event implements Cancellable {
    public static final HandlerList handlerlist = new HandlerList();
    private boolean cancelled;

    private final Location from, to;
    private final Player player;

    public SmartPlayerMoveEvent(Location from, Location to, Player player) {
        this.from = from;
        this.to = to;
        this.player = player;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean hasBlockChange() {
        return to.getBlock() != from.getBlock();
    }

    public boolean hasChunkChange() {
        return to.getChunk() != from.getChunk();
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerlist;
    }

    public static HandlerList getHandlerList() {
        return handlerlist;
    }
}
