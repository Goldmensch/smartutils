package de.goldmensch.smartutils.localizer.reader.resourcebundle;

import de.goldmensch.smartutils.localizer.reader.LocalizationReader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ResourceBundleReader implements LocalizationReader {

    private final ResourceBundle bundle;
    private final Map<String, String> localizations = new HashMap<>();

    private ResourceBundleReader(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public static ResourceBundleReader fromBundle(ResourceBundle bundle) {
        ResourceBundleReader reader = new ResourceBundleReader(bundle);
        reader.convertToLocalizationMap();
        return reader;
    }

    public static ResourceBundleReader fromPropertiesFile(Path path) {
        ResourceBundle bundle = null;

        try(InputStream in = Files.newInputStream(path)) {
            bundle = new PropertyResourceBundle(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fromBundle(bundle);
    }

    private void convertToLocalizationMap() {
        for(String c : bundle.keySet()) {
            localizations.put(c, bundle.getString(c));
        }
    }

    @Override
    public Map<String, String> getLocalizations() {
        return localizations;
    }
}
