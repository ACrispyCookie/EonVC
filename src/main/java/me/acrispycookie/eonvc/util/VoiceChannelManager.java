package me.acrispycookie.eonvc.util;

import me.acrispycookie.eonvc.Main;
import me.acrispycookie.eonvc.util.utility.VoiceChannel;
import net.dv8tion.jda.api.entities.Member;

import java.util.ArrayList;

public class VoiceChannelManager {

    Main main;
    ArrayList<VoiceChannel> channels = new ArrayList<>();

    public VoiceChannelManager(Main main){
        this.main = main;
        loadChannels();
    }

    private void loadChannels(){
        for(String s : main.getConfigManager().getConfig().getConfigurationSection("settings.voice-channels").getKeys(false)){
            channels.add(new VoiceChannel(s, main.getConfigManager().getString("settings.voice-channels." + s + ".id")));
        }
    }

    public boolean isConnectedOnAChannel(String discordId){
        Member m = main.getMember(discordId);
        if(main.getVoiceChannel(main.getConfigManager().getString("settings.waiting-channel")).getMembers().contains(m)){
            return true;
        }
        for(VoiceChannel v : channels){
            if(main.getVoiceChannel(v.getDiscordId()).getMembers().contains(m)){
                return true;
            }
        }
        return false;
    }

    public boolean containsUser(VoiceChannel c, String discordId){
        return main.getVoiceChannel(c.getDiscordId()).getMembers().contains(main.getMember(discordId));
    }

    public VoiceChannel getByName(String name){
        for(VoiceChannel channel : channels){
            if(channel.getName().equalsIgnoreCase(name)){
                return channel;
            }
        }
        return null;
    }
}
