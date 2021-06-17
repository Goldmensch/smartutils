package de.goldmensch.smartutils.localizer.reader.json;

import java.util.Map;

public class InvalidJsonLocalizationFormat extends RuntimeException{

    public InvalidJsonLocalizationFormat(Map.Entry<?, ?> entry) {
        super("the entry: (key: "
                + entry.getKey().toString()  + ")(value: "
                + entry.getValue().toString()
                + ") is not valid for localization.");
    }
}
