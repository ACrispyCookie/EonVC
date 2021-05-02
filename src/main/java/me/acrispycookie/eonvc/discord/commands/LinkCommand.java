package me.acrispycookie.eonvc.discord.commands;

import me.acrispycookie.eonvc.Main;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

import java.util.HashMap;


public class LinkCommand extends ListenerAdapter {

    Main main;

    public LinkCommand(Main main){
        this.main = main;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e){
        String[] args = e.getMessage().getContentRaw().split(" ");
        if(args[0].startsWith("!") && !e.getAuthor().equals(e.getJDA().getSelfUser())) {
            if(args.length == 2) {
                if(args[0].equalsIgnoreCase("!link")){
                    String player = args[1];
                    if(Bukkit.getPlayerExact(player) != null){
                        if(main.getPlayersManager().getByUuid(Bukkit.getPlayerExact(player).getUniqueId()) == null){
                            main.getRequestManager().newRequest(e.getAuthor().getId(), Bukkit.getPlayerExact(player).getUniqueId());
                            e.getChannel().sendMessage(main.getMessagesManager().getStringReplaced("messages.discord-commands.link.sent-request", new HashMap<String, String>() {{
                                put("%player%", Bukkit.getPlayerExact(player).getName());
                            }})).queue();
                        }
                        else{
                            e.getChannel().sendMessage(main.getMessagesManager().getString("messages.discord-commands.link.already-linked")).queue();
                        }
                    }
                    else{
                        e.getChannel().sendMessage(main.getMessagesManager().getString("messages.discord-commands.link.not-online")).queue();
                    }
                }
            }
            else{
                e.getChannel().sendMessage(main.getMessagesManager().getString("messages.discord-commands.link.usage")).queue();
            }
        }
    }

}
