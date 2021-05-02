package me.acrispycookie.eonvc.util;

import me.acrispycookie.eonvc.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class RequestManager {

    Main main;
    ArrayList<LinkRequest> requests = new ArrayList<>();

    public RequestManager(Main main) {
        this.main = main;
    }

    public void newRequest(String discordId, UUID uuid){
        new LinkRequest(main, uuid, discordId);
        sendRequest(uuid, discordId);
    }

    public void accept(LinkRequest request){
        savePlayer(request.getUuid(), request.getDiscordId());
    }

    public void addRequest(LinkRequest request){
        requests.add(request);
    }

    public void removeRequest(LinkRequest request){
        requests.remove(request);
    }

    public LinkRequest get(UUID uuid, String discordId){
        for(LinkRequest request : requests){
            if(request.getDiscordId().equalsIgnoreCase(discordId) && request.getUuid().equals(uuid)){
                return request;
            }
        }
        return null;
    }

    private void savePlayer(UUID uuid, String discordId){
        main.getPlayersManager().savePlayer(uuid, discordId);
    }

    private void sendRequest(UUID uuid, String discordId){
        String msg = main.getMessagesManager().getStringReplaced("messages.commands.accept-link.request", new HashMap<String, String>() {{
            put("%user%", main.getUser(discordId).getAsTag());
        }});
        String[] msgLines = msg.split("\n");
        TextComponent accept = new TextComponent(main.getMessagesManager().getStringReplaced("messages.commands.accept-link.accept-button", new HashMap<>()));
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptlink " + discordId));
        TextComponent textC = new TextComponent("");
        for (int i = 0; i < msgLines.length; i++) {
            if(msgLines[i].contains("%accept%")) {
                textC.addExtra(msgLines[i].substring(0, msgLines[i].indexOf("%accept%")));
                textC.addExtra(accept);
                textC.addExtra(msgLines[i].substring(msgLines[i].indexOf("%accept%") + 8));
            } else {
                textC.addExtra(msgLines[i]);
            }
            if(i + 1 < msgLines.length){
                textC.addExtra("\n");
            }
        }
        Bukkit.getPlayer(uuid).spigot().sendMessage(textC);
    }
}
