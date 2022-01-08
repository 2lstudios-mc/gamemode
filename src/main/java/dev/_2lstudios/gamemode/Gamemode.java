package dev._2lstudios.gamemode;

import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import dev._2lstudios.gamemode.commands.GamemodeCommand;
import dev._2lstudios.gamemode.config.MessagesConfig;

public class Gamemode extends JavaPlugin {
    @Override
    public void onEnable () {
        this.saveDefaultConfig();

        Gamemode.instance = this;

        Configuration config = getConfig();
        MessagesConfig messagesConfig = new MessagesConfig(config);

        this.getCommand("gamemode").setExecutor(new GamemodeCommand(messagesConfig));
        
    }

    private static Gamemode instance;

    public static Gamemode getInstance () {
        return Gamemode.instance;
    }
}