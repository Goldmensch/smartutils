package de.goldmensch.smartutils.localizer;

import de.goldmensch.smartutils.localizer.adapter.LocalizerAdapter;
import de.goldmensch.smartutils.localizer.reader.LocalizationReader;
import de.goldmensch.smartutils.utils.MapUtil;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class SmartLocalizer<T>{

    private final LocalizerAdapter<T> adapter;
    private final Map<String, String> localizationMap = new ConcurrentHashMap<>();
    private final char replaceToken;
    private final Locale locale;

    private SmartLocalizer(char replaceToken, Locale locale, LocalizerAdapter<T> adapter) {
        this.adapter = adapter;
        this.replaceToken = replaceToken;
        this.locale = locale;
    }

    public static <T> SmartLocalizer<T> newLocalizer(Locale locale, LocalizerAdapter<T> adapter) {
        return new SmartLocalizer<>('%', locale, adapter);
    }

    public static <T> SmartLocalizer<T> newLocalizer(Locale locale, LocalizerAdapter<T> adapter, char replaceToken) {
        return new SmartLocalizer<>(replaceToken, locale, adapter);
    }

    public T localize(String key, Replacement... replacements) {
        String message = getLocalizationMap().get(key);
        return adapter.convert(replace(message, replacements));
    }

    public SmartLocalizer<T> addAll(Map<String, String> map) {
        localizationMap.putAll(MapUtil.lowerMap(map));
        return this;
    }

    public SmartLocalizer<T> add(String key, String value) {
        localizationMap.put(key.toLowerCase(), value.toLowerCase());
        return this;
    }

    public Map<String, String> getLocalizationMap() {
        return localizationMap;
    }

    public Locale getLocale() {
        return locale;
    }

    public char getReplaceToken() {
        return replaceToken;
    }

    public void fromReader(LocalizationReader reader) {
        addAll(reader.getLocalizations());
    }

    private String replace(String message, Replacement... replacements) {
        for(Replacement c : replacements) {
            message = c.replace(message, replaceToken);
        }
        return message;
    }

}
