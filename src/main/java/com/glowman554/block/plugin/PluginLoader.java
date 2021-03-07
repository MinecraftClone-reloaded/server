package com.glowman554.block.plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class PluginLoader {

    File plugin_dir = new File("plugins");

    List<Plugin> plugins = new ArrayList<>();

    public PluginLoader() throws IOException {
        if(!this.plugin_dir.exists()) {
            this.plugin_dir.mkdir();
        }

        Files.walk(this.plugin_dir.toPath()).forEach(path -> {
            if(path.toFile().isFile()) {
                try {
                    this.plugins.add(new Plugin(path.toFile()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void enableAll() {
        this.plugins.forEach(Plugin::enable);
    }

    public void disableAll() {
        this.plugins.forEach(Plugin::disable);
    }
}
