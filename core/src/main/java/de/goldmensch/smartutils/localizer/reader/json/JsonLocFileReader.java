package de.goldmensch.smartutils.localizer.reader.json;

import com.google.gson.Gson;
import de.goldmensch.smartutils.localizer.reader.LocalizationReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class JsonLocFileReader implements LocalizationReader {
    private Map<?, ?> rawInput;
    private final Map<String, String> localizations = new HashMap<>();

    private final Gson gson = new Gson();
    private final Path file;

    private JsonLocFileReader(Path file) {
        this.file = file;
    }

    public static JsonLocFileReader readFile(Path path) {
        JsonLocFileReader locFileReader = new JsonLocFileReader(path);

        try {
            locFileReader.readIn();
        } catch (IOException e) {
            e.printStackTrace();
        }

        locFileReader.convertToLocalizationsMap();
        return locFileReader;
    }

    private void readIn() throws IOException {
        try(BufferedReader reader = Files.newBufferedReader(file)) {
            rawInput = gson.fromJson(reader, Map.class);
        }
    }

    private void convertToLocalizationsMap() {
        for(Map.Entry<?, ?> c : rawInput.entrySet()) {
            if(checkEntry(c)) {
                Map.Entry<String, String> entry = convertToStringEntry(c);
                localizations.put(entry.getKey(), entry.getValue());
            }else {
                throw new InvalidJsonLocalizationFormat(c);
            }
        }
    }

    private boolean checkEntry(Map.Entry<?, ?> entry) {
        return (entry.getValue() instanceof String)
                && (entry.getKey() instanceof String);
    }

    private Map.Entry<String, String> convertToStringEntry(Map.Entry<?, ?> entry) {
        return new AbstractMap.SimpleEntry<>(entry.getKey().toString(), entry.getValue().toString());
    }

    public Map<String, String> getLocalizations() {
        return localizations;
    }
}
