package de.goldmensch.smartutils.testplugin;

import de.goldmensch.smartutils.event.movement.SmartPlayerMoveEvent;
import de.goldmensch.smartutils.event.movement.SmartPlayerMoveExecutor;
import de.goldmensch.smartutils.plugin.SmartPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TestPlugin extends SmartPlugin implements Listener {

    @Override
    public void onEnable() {
        SmartPlayerMoveExecutor executor = new SmartPlayerMoveExecutor(SmartPlayerMoveExecutor.PreCheck.newBuilder()
                .checkX().checkY().build(),
                SmartPlayerMoveExecutor.CheckType.BLOCK, true);

        registerListener(executor, this);
    }

    @EventHandler
    public void handleMoveEvent(SmartPlayerMoveEvent event) {
        event.getPlayer().sendMessage(event.getFrom() + "-" + event.getTo());
    }
}
