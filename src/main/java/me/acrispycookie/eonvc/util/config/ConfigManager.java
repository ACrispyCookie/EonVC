package me.acrispycookie.eonvc.util.config;

import me.acrispycookie.eonvc.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class ConfigManager {

    Main main;
    YamlConfiguration config;
    String name;

    public ConfigManager(Main main, YamlConfiguration config, String name){
        this.main = main;
        this.config = config;
        this.name = name;
    }

    public String getString(String path){
        return config.getString(path);
    }

    public YamlConfiguration getConfig(){
        return config;
    }

    public void save(){
        File file = new File(main.getDataFolder(), name);
        try {
            config.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().info("Couldn't save the file with the name " + name);
            e.printStackTrace();
        }
    }
}
