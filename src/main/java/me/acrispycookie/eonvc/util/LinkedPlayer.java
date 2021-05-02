package me.acrispycookie.eonvc.util;

import java.util.UUID;

public class LinkedPlayer {

    UUID uuid;
    String discordId;

    public LinkedPlayer(UUID uuid, String discordId){
        this.uuid = uuid;
        this.discordId = discordId;
    }

    public UUID getUuid(){
        return uuid;
    }

    public String getDiscordId(){
        return discordId;
    }
}
