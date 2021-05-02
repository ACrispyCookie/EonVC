package me.acrispycookie.eonvc.ingame.commands;

import me.acrispycookie.eonvc.Main;
import me.acrispycookie.eonvc.util.LinkedPlayer;
import net.dv8tion.jda.api.entities.VoiceChannel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class VoiceChannelCommand implements CommandExecutor {

    Main main;

    public VoiceChannelCommand(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length == 1){
                LinkedPlayer pl = main.getPlayersManager().getByUuid(p.getUniqueId());
                if(pl != null){
                    if(main.getVcManager().isConnectedOnAChannel(pl.getDiscordId())){
                        me.acrispycookie.eonvc.util.utility.VoiceChannel channel = main.getVcManager().getByName(args[0]);
                        if(channel != null){
                            if(!main.getVcManager().containsUser(channel, pl.getDiscordId())){
                                main.getMessagesManager().msg(p, "messages.commands.voice-channel.moved-to-channel", new HashMap<String, String>() {{
                                    put("%number%", main.getVcManager().getByName(args[0]).getName());
                                }});
                                main.moveUserToChannel(pl.getDiscordId(), main.getVcManager().getByName(args[0]).getDiscordId());
                            }
                            else{
                                main.getMessagesManager().msg(p, "messages.commands.voice-channel.already-on-channel");
                            }
                        }
                        else{
                            main.getMessagesManager().msg(p, "messages.commands.voice-channel.channel-does-not-exist");
                        }
                    }
                    else{
                        main.getMessagesManager().msg(p, "messages.commands.voice-channel.not-waiting");
                    }
                }
                else{
                    main.getMessagesManager().msg(p, "messages.commands.voice-channel.not-linked");
                }
            }
            else if(args.length == 0){
                LinkedPlayer pl = main.getPlayersManager().getByUuid(p.getUniqueId());
                if(pl != null){
                    VoiceChannel waiting = main.getVoiceChannel(main.getConfigManager().getString("settings.waiting-channel"));
                    if(!waiting.getMembers().contains(main.getMember(pl.getDiscordId()))){
                        if(main.getVcManager().isConnectedOnAChannel(pl.getDiscordId())){
                            main.getMessagesManager().msg(p, "messages.commands.voice-channel.moved-to-waiting");
                            main.moveUserToChannel(pl.getDiscordId(), main.getConfigManager().getString("settings.waiting-channel"));
                        }
                        else{
                            main.getMessagesManager().msg(p, "messages.commands.voice-channel.not-connected");
                        }
                    }
                    else{
                        main.getMessagesManager().msg(p, "messages.commands.voice-channel.already-on-waiting");
                    }
                }
                else{
                    main.getMessagesManager().msg(p, "messages.commands.voice-channel.not-linked");
                }
            }
            else{
                main.getMessagesManager().msg(sender, "messages.commands.voice-channel.usage");
            }
        }
        else{
            main.getMessagesManager().msg(sender, "messages.only-players");
        }
        return false;
    }
}
