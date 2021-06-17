package de.goldmensch.smartutils.localizer.adapter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class ComponentLocalizerAdapter implements LocalizerAdapter<Component> {

    @Override
    public Component convert(String message) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(message);
    }
}
