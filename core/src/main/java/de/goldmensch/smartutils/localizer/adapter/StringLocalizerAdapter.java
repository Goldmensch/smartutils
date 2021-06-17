package de.goldmensch.smartutils.localizer.adapter;

public class StringLocalizerAdapter implements LocalizerAdapter<String>{

    @Override
    public String convert(String message) {
        return message;
    }
}
