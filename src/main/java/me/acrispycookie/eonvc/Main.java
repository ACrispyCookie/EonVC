package me.acrispycookie.eonvc;

import me.acrispycookie.eonvc.discord.commands.AutoDeafenListener;
import me.acrispycookie.eonvc.discord.commands.LinkCommand;
import me.acrispycookie.eonvc.ingame.commands.AcceptLinkCommand;
import me.acrispycookie.eonvc.ingame.commands.UnlinkCommand;
import me.acrispycookie.eonvc.ingame.commands.VoiceChannelCommand;
import me.acrispycookie.eonvc.util.RequestManager;
import me.acrispycookie.eonvc.util.VoiceChannelManager;
import me.acrispycookie.eonvc.util.config.DatabaseConfig;
import me.acrispycookie.eonvc.util.config.MessageConfig;
import me.acrispycookie.eonvc.util.config.NormalConfig;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {

    NormalConfig configManager;
    MessageConfig messagesManager;
    DatabaseConfig playersManager;
    RequestManager requestManager;
    VoiceChannelManager vcManager;
    JDA bot;

    @Override
    public void onEnable(){
        configManager = new NormalConfig(this, loadConfig("config.yml"), "config.yml");
        messagesManager = new MessageConfig(this, loadConfig("messages.yml"), "messages.yml");
        playersManager = new DatabaseConfig(this, loadConfig("players.yml"), "players.yml");
        if(!configManager.getString("settings.bot-token").equalsIgnoreCase("<------------- BOT TOKEN GOES HERE ------------->")){
            loadRequestManager();
            startDiscordBot();
            loadVoiceChannelManager();
            initializeCommands();
            Bukkit.getLogger().info("EonVC has been enabled!");
        }
        else{
            Bukkit.getLogger().info("Please edit the config file and restart the server to enable the plugin.");
        }
    }

    @Override
    public void onDisable(){
        bot.shutdown();
        Bukkit.getLogger().info("EonVC has been disabled!");
    }

    private YamlConfiguration loadConfig(String path){
        File file = new File(getDataFolder(), path);
        if(!file.exists()){
            saveResource(path, false);
        }
        try {
            YamlConfiguration configuration = new YamlConfiguration();
            configuration.load(file);
            return configuration;
        } catch (IOException | InvalidConfigurationException e) {
            Bukkit.getLogger().info("Couldn't load the " + path + " file.");
            e.printStackTrace();
        }
        return null;
    }

    private void loadRequestManager(){
        requestManager = new RequestManager(this);
    }

    private void startDiscordBot(){
        String token = configManager.getString("settings.bot-token");
        String activityType = configManager.getString("settings.activity.type");
        String activityText = configManager.getString("settings.activity.text");
        try {
            JDABuilder builder = JDABuilder.createDefault(token);
            builder.setActivity(Activity.of(activityType.equals("PLAYING") ? Activity.ActivityType.DEFAULT : Activity.ActivityType.valueOf(activityType), activityText));
            bot = builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    private void loadVoiceChannelManager(){
        vcManager = new VoiceChannelManager(this);
    }

    private void initializeCommands(){
        bot.addEventListener(new LinkCommand(this));
        bot.addEventListener(new AutoDeafenListener(this));
        this.getCommand("voicechannel").setExecutor(new VoiceChannelCommand(this));
        this.getCommand("acceptlink").setExecutor(new AcceptLinkCommand(this));
        this.getCommand("unlink").setExecutor(new UnlinkCommand(this));
    }

    public NormalConfig getConfigManager(){
        return configManager;
    }

    public MessageConfig getMessagesManager(){
        return messagesManager;
    }

    public DatabaseConfig getPlayersManager(){
        return playersManager;
    }

    public RequestManager getRequestManager(){
        return requestManager;
    }

    public VoiceChannelManager getVcManager() {
        return vcManager;
    }

    public User getUser(String discordId){
        return bot.retrieveUserById(discordId).complete();
    }

    public Member getMember(String discordId){
        return bot.getGuildById(configManager.getString("settings.guild-id")).getMember(getUser(discordId));
    }

    public VoiceChannel getVoiceChannel(String discordId){
        return bot.getGuildById(configManager.getString("settings.guild-id")).getVoiceChannelById(discordId);
    }

    public void moveUserToChannel(String userId, String channelId){
        Member m = bot.getGuildById(configManager.getString("settings.guild-id")).retrieveMember(getUser(userId)).complete();
        VoiceChannel channel = bot.getGuildById(configManager.getString("settings.guild-id")).getVoiceChannelById(channelId);
        if(m.getVoiceState().inVoiceChannel()){
            bot.getGuildById(configManager.getString("settings.guild-id")).moveVoiceMember(m, channel).queue();
        }
    }
}
