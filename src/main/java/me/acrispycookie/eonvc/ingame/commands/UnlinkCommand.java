package me.acrispycookie.eonvc.ingame.commands;

import me.acrispycookie.eonvc.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnlinkCommand implements CommandExecutor {

    Main main;

    public UnlinkCommand(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(main.getPlayersManager().getByUuid(p.getUniqueId()) != null){
                main.getPlayersManager().unlink(p.getUniqueId());
                main.getMessagesManager().msg(sender, "messages.commands.unlink.unlinked");
            }
            else{
                main.getMessagesManager().msg(sender, "messages.commands.unlink.not-linked");
            }
        }
        else{
            main.getMessagesManager().msg(sender, "messages.only-players");
        }
        return false;
    }
}
