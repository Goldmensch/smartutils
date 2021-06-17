package de.goldmensch.smartutils.localizer;

import org.bukkit.entity.Player;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerLocalizerCollection<T> {

    private final Map<Locale, SmartLocalizer<T>> localizerMap = new ConcurrentHashMap<>();
    private final Map<Player, Locale> players = new ConcurrentHashMap<>();
    private final Locale standard;

    private PlayerLocalizerCollection(SmartLocalizer<T> standard) {
        addLocalizer(standard);
        this.standard = standard.getLocale();
    }

    @SafeVarargs
    public static <T> PlayerLocalizerCollection<T> newCollection(SmartLocalizer<T> standard, SmartLocalizer<T>... localizers) {
        var collection = new PlayerLocalizerCollection<>(standard);
        collection.addLocalizer(localizers);
        return collection;
    }

    @SafeVarargs
    public final PlayerLocalizerCollection<T> addLocalizer(SmartLocalizer<T>... localizers) {
        for(var c : localizers) {
            localizerMap.put(c.getLocale(), c);
        }
        return this;
    }

    public PlayerLocalizerCollection<T> setPlayerLocale(Locale locale, Player... players) {
        for(var c : players) {
            this.players.put(c, locale);
        }
        return this;
    }

    public T localize(Player player, String key, Replacement... replacements) {
        if(players.containsKey(player)) {
            return getFromPlayer(player).localize(key, replacements);
        }
        return getStandardLocalizer().localize(key, replacements);
    }

    public SmartLocalizer<T> getFromPlayer(Player player) {
        return localizerMap.get(players.get(player));
    }

    public SmartLocalizer<T> getStandardLocalizer() {
        return localizerMap.get(standard);
    }
}
