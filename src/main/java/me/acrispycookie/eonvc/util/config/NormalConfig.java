package me.acrispycookie.eonvc.util.config;

import me.acrispycookie.eonvc.Main;
import org.bukkit.configuration.file.YamlConfiguration;

public class NormalConfig extends ConfigManager {
    public NormalConfig(Main main, YamlConfiguration config, String name) {
        super(main, config, name);
    }
}
