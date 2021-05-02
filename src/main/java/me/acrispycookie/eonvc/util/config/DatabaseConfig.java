package me.acrispycookie.eonvc.util.config;

import me.acrispycookie.eonvc.Main;
import me.acrispycookie.eonvc.util.LinkedPlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseConfig extends ConfigManager {

    List<LinkedPlayer> players = new ArrayList<>();

    public DatabaseConfig(Main main, YamlConfiguration config, String name) {
        super(main, config, name);
        loadPlayers();
    }

    public void savePlayer(UUID uuid, String discordId){
        config.set("players." + uuid + ".id", discordId);
        players.add(new LinkedPlayer(uuid, discordId));
        save();
    }

    public LinkedPlayer getByUuid(UUID uuid){
        for(LinkedPlayer p : players){
            if(p.getUuid().equals(uuid)){
                return p;
            }
        }
        return null;
    }

    public void unlink(UUID uuid){
        config.set("players." + uuid, null);
        players.remove(getByUuid(uuid));
        save();
    }

    private void loadPlayers(){
        for(String s : config.getConfigurationSection("players").getKeys(false)){
            players.add(new LinkedPlayer(UUID.fromString(s), config.getConfigurationSection("players").getString(s + ".id")));
        }
    }
}
