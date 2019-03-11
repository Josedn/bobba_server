package io.bobba.poc.misc.configs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConfigFile {

    private static final String KEY_SEPARATOR = "=";
    private static final String COMMENT_PREFIX = "#";

    private String name;
    private Map<String, String> properties;

    public String getProp(String key) {
        return this.properties.getOrDefault(key, null);
    }

    public void setProp(String key, String value) {
        this.properties.put(key, value);
    }

    public boolean fileExists() {
        File file = new File(name);
        return file.exists();
    }

    public ConfigFile(String name) {
        this.name = name;
        this.properties = new LinkedHashMap<>();
    }

    private void handleLines(List<String> lines) {
        this.properties.clear();
        for (String line : lines) {
            if (!line.startsWith(COMMENT_PREFIX) && !line.isEmpty() && line.contains(KEY_SEPARATOR)) {
                int separation = line.indexOf(KEY_SEPARATOR);
                String key = line.substring(0, separation);
                String value = line.substring(separation + 1);
                setProp(key, value);
            }
        }
    }

    public boolean readFile() {
        File file = new File(name);
        if (file.exists()) {
            try {
                List<String> lines = Files.readAllLines(file.toPath());
                handleLines(lines);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public boolean writeFile() {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileOutputStream(name));
            for (String key : this.properties.keySet()) {
                String value = this.properties.get(key);
                out.write(key + KEY_SEPARATOR + value);
                out.write(System.lineSeparator());
            }
            out.close();
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }
}
