package de.goldmensch.smartutils.localizer;

public final class Replacement {
    private final String key;
    private final String value;

    private Replacement(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static Replacement create(String key, String value) {
        return new Replacement(key, value);
    }

    public String replace(String message, char token) {
        return message.replace(token + key + token, value);
    }
}
