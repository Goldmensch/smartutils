package de.goldmensch.smartutils.localizer;

import de.goldmensch.smartutils.localizer.adapter.LocalizerAdapter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class MiniMessageAdapter implements LocalizerAdapter<Component> {

    @Override
    public Component convert(String message) {
        return MiniMessage.get().parse(message);
    }
}
