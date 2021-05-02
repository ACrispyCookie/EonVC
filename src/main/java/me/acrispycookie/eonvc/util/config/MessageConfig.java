package me.acrispycookie.eonvc.util.config;

import me.acrispycookie.eonvc.Main;
import me.acrispycookie.eonvc.util.utility.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class MessageConfig extends ConfigManager{
    public MessageConfig(Main main, YamlConfiguration config, String name) {
        super(main, config, name);
    }

    public String getStringReplaced(String path, HashMap<String, String> placeholders){
        return getString(path, placeholders);
    }

    public void msg(CommandSender sender, String path){
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getString(path, new HashMap<>())));
    }

    public void msg(Player p, String path){
        p.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', getString(path, new HashMap<>())));
    }

    public void msg(Player p, String path, HashMap<String, String> placeholders){
        p.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', getString(path, placeholders)));
    }

    private String replace(String path, HashMap<String, String> placeholders){
        String value = getString(path);
        for(String s : placeholders.keySet()){
            value = value.replace(s, placeholders.get(s));
        }
        return value;
    }

    private String getString(String path, HashMap<String, String> placeholders){
        if(config.contains(path + ".text")){
            StringBuilder s = new StringBuilder();
            for(String ss : config.getStringList(path + ".text")){
                String replaced = ss;
                for(String placeholder : placeholders.keySet()){
                    replaced = replaced.replace(placeholder, placeholders.get(placeholder));
                }
                if(config.getBoolean(path + ".centered") && ss.length() < 60){
                    s.append(Messages.getCenteredMessage(replaced)).append("\n");
                }
                else{
                    s.append(ChatColor.translateAlternateColorCodes('&', replaced)).append("\n");
                }
            }
            s = new StringBuilder(s.substring(0, s.length() - 1));
            return s.toString();
        }
        else if(config.isList(path)){
            StringBuilder s = new StringBuilder();
            for(String ss : config.getStringList(path)){
                String replaced = ss;
                for(String placeholder : placeholders.keySet()){
                    replaced = replaced.replace(placeholder, placeholders.get(placeholder));
                }
                s.append(replaced).append("\n");
            }
            s = new StringBuilder(s.substring(0, Math.max(s.length() - 1, s.length())));
            return s.toString();
        }
        else{
            return ChatColor.translateAlternateColorCodes('&', replace(path, placeholders));
        }
    }
}
