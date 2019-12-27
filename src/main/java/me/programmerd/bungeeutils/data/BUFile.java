package me.programmerd.bungeeutils.data;

import com.google.common.io.ByteStreams;
import me.programmerd.bungeeutils.BungeeUtils;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;

public class BUFile {

    private String fileName;

    private Configuration configuration;
    private File file;

    public BUFile(String fileName) {

        this.fileName = fileName;

        file = new File(BungeeUtils.getInstance().getDataFolder(), fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                try (InputStream is = BungeeUtils.getInstance().getResourceAsStream(fileName);
                     OutputStream os = new FileOutputStream(file)) {
                    ByteStreams.copy(is, os);
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to create configuration file", e);
            }
        }

        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, new File(BungeeUtils.getInstance().getDataFolder(), fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() throws IOException {
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(getConfiguration(), new File(BungeeUtils.getInstance().getDataFolder(), fileName));
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public File getFile() {
        return file;
    }

}
