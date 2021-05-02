package me.acrispycookie.eonvc.discord.commands;

import me.acrispycookie.eonvc.Main;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AutoDeafenListener extends ListenerAdapter {

    Main main;

    public AutoDeafenListener(Main main){
        this.main = main;
    }

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent e){
        if(e.getChannelJoined().getId().equalsIgnoreCase(main.getConfigManager().getString("settings.waiting-channel"))){
            e.getMember().mute(true).queue();
            e.getMember().deafen(true).queue();
        }
        else{
            e.getMember().mute(false).queue();
            e.getMember().deafen(false).queue();
        }
    }

    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent e){
        if(e.getChannelJoined().getId().equalsIgnoreCase(main.getConfigManager().getString("settings.waiting-channel"))){
            e.getMember().mute(true).queue();
            e.getMember().deafen(true).queue();
        }
        else{
            e.getMember().mute(false).queue();
            e.getMember().deafen(false).queue();
        }
    }
}
