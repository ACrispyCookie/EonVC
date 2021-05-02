package me.acrispycookie.eonvc.ingame.commands;

import me.acrispycookie.eonvc.Main;
import me.acrispycookie.eonvc.util.LinkRequest;
import me.acrispycookie.eonvc.util.LinkedPlayer;
import me.acrispycookie.eonvc.util.utility.Numbers;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class AcceptLinkCommand implements CommandExecutor {

    Main main;

    public AcceptLinkCommand(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length == 1){
                if(Numbers.isLong(args[0])){
                    LinkRequest request = main.getRequestManager().get(p.getUniqueId(), args[0]);
                    if(request != null){
                        LinkedPlayer lp = main.getPlayersManager().getByUuid(p.getUniqueId());
                        if(lp == null){
                            main.getMessagesManager().msg(p, "messages.commands.accept-link.accepted", new HashMap<String, String>() {{
                                put("%user%", main.getUser(request.getDiscordId()).getAsTag());
                            }});
                            main.getRequestManager().accept(request);
                        }
                        else{
                            main.getMessagesManager().msg(p, "messages.commands.accept-link.already-linked", new HashMap<String, String>() {{
                                put("%user%", main.getUser(lp.getDiscordId()).getAsTag());
                            }});
                        }
                    }
                    else{
                        main.getMessagesManager().msg(p, "messages.commands.accept-link.not-requested");
                    }
                }
                else{
                    main.getMessagesManager().msg(p, "messages.invalid-number");
                }
            }
            else{
                main.getMessagesManager().msg(p, "messages.commands.accept-link.usage");
            }
        }
        else{
            main.getMessagesManager().msg(sender, "messages.only-players");
        }
        return false;
    }
}
