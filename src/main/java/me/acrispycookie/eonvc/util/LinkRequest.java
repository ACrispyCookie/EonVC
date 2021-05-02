package me.acrispycookie.eonvc.util;

import me.acrispycookie.eonvc.Main;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class LinkRequest {

    Main main;
    UUID uuid;
    String discordId;
    BukkitTask expire;

    public LinkRequest(Main main, UUID uuid, String discordId){
        this.main = main;
        this.uuid = uuid;
        this.discordId = discordId;
        start();
    }

    private void start(){
        main.getRequestManager().addRequest(this);
        expire = new BukkitRunnable() {
            @Override
            public void run() {
                main.getRequestManager().removeRequest(LinkRequest.this);
            }
        }.runTaskLater(main, 60 * 20L);
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getDiscordId() {
        return discordId;
    }
}
