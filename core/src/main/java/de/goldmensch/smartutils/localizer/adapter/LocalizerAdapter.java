package de.goldmensch.smartutils.localizer.adapter;

public interface LocalizerAdapter<T> {
    T convert(String message);
}
