package de.goldmensch.smartutils.event.movement;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.*;

public class SmartPlayerMoveExecutor implements Listener {

    private final PreCheck preCheck;
    private final CheckType checkType;
    private final boolean checkAll;
    private final Set<Player> players = new HashSet<>();

    public SmartPlayerMoveExecutor(PreCheck preCheck, CheckType checkType, boolean checkAll) {
        this.preCheck = preCheck;
        this.checkType = checkType;
        this.checkAll = checkAll;
    }

    @EventHandler
    public void handlePlayerMove(PlayerMoveEvent event) {
        if(checkAll || players.contains(event.getPlayer())) {
            checkAndCall(event);
        }
    }

    private void checkAndCall(PlayerMoveEvent moveEvent) {
        Location oldLoc = moveEvent.getFrom();
        Location newLoc = moveEvent.getTo();

        boolean call = switch (checkType) {
            case BLOCK -> checkCoords(oldLoc.getBlockX(), oldLoc.getBlockY(), oldLoc.getBlockZ(), newLoc.getBlockX(), newLoc.getBlockY(), newLoc.getBlockZ());
            case CHUNK -> oldLoc.getChunk() != newLoc.getChunk();
            case PRECISE -> checkCoords(oldLoc.getX(), oldLoc.getY(), oldLoc.getZ(), newLoc.getX(), newLoc.getY(), newLoc.getZ());
        };

        if(call) {
            SmartPlayerMoveEvent event = new SmartPlayerMoveEvent(oldLoc, newLoc, moveEvent.getPlayer());
            Bukkit.getPluginManager().callEvent(event);
            moveEvent.setCancelled(event.isCancelled());
        }
    }

    private boolean checkCoords(double oldX, double oldY, double oldZ, double newX, double newY, double newZ) {
        return (preCheck.isX() && checkSingle(oldX, newX))
                || (preCheck.isY() && checkSingle(oldY, newY))
                || (preCheck.isZ() && checkSingle(oldZ, newZ));
    }

    private boolean checkSingle(double oldCoo, double newCoo) {
        return oldCoo != newCoo;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    public Set<Player> getPlayers() {
        return Collections.unmodifiableSet(players);
    }

    public boolean isTracked(Player player) {
        return players.contains(player);
    }

    public enum CheckType {
        CHUNK,
        BLOCK,
        PRECISE;
    }

    public static class PreCheck {
        public static final PreCheck ALL = new PreCheck(true, true, true);
        private final boolean x, y, z;

        public PreCheck(boolean x, boolean y, boolean z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public static PreCheckBuilder newBuilder() {
            return new PreCheckBuilder();
        }

        public boolean isX() {
            return x;
        }

        public boolean isY() {
            return y;
        }

        public boolean isZ() {
            return z;
        }
    }

    public static final class PreCheckBuilder {
        private boolean x, y, z;

        private PreCheckBuilder() {

        }

        public PreCheckBuilder checkX() {
            x = true;
            return this;
        }

        public PreCheckBuilder checkY() {
            y = true;
            return this;
        }

        public PreCheckBuilder checkZ() {
            z = true;
            return this;
        }

        public SmartPlayerMoveExecutor.PreCheck build() {
            return new SmartPlayerMoveExecutor.PreCheck(x, y, z);
        }
    }
}
