package me.acrispycookie.eonvc.util.utility;

public class VoiceChannel {

    String name;
    String discordId;

    public VoiceChannel(String name, String discordId){
        this.name = name;
        this.discordId = discordId;
    }

    public String getName() {
        return name;
    }

    public String getDiscordId() {
        return discordId;
    }
}
