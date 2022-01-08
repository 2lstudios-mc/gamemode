package dev._2lstudios.gamemode.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev._2lstudios.gamemode.config.MessagesConfig;
import dev._2lstudios.gamemode.config.Placeholder;

public class GamemodeCommand implements CommandExecutor {
    private MessagesConfig messagesConfig;

    public GamemodeCommand(MessagesConfig messagesConfig) {
        this.messagesConfig = messagesConfig;
    }

    public GameMode processGameMode(String arg) {
        if (arg.startsWith("SP")) {
            return GameMode.SPECTATOR;
        } else if (arg.startsWith("C")) {
            return GameMode.CREATIVE;
        } else if (arg.startsWith("A")) {
            return GameMode.ADVENTURE;
        } else if (arg.startsWith("S")) {
            return GameMode.SURVIVAL;
        } else {
            try {
                int number = Integer.parseInt(arg);

                if (number == 0) {
                    return GameMode.SURVIVAL;
                } else if (number == 1) {
                    return GameMode.CREATIVE;
                } else if (number == 2) {
                    return GameMode.ADVENTURE;
                } else if (number == 3) {
                    return GameMode.SPECTATOR;
                }
            } catch (NumberFormatException ex) {
                /* Ignored */ }
        }

        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(messagesConfig.getMessage("console"));
            return true;
        }

        Player player = (Player) sender;

        if (args.length > 0) {
            String arg = args[0].toUpperCase();
            GameMode gameMode = processGameMode(arg);

            if (gameMode != null) {
                String gameModeName = gameMode.name();
                Placeholder[] placeholders = { new Placeholder("%gamemode%", gameModeName) };

                if (sender.hasPermission("gamemode." + gameModeName.toLowerCase())) {
                    player.setGameMode(gameMode);
                    sender.sendMessage(messagesConfig.getMessage("changed", placeholders));
                } else {
                    sender.sendMessage(messagesConfig.getMessage("no-permission", placeholders));
                }
            } else {
                Placeholder[] placeholders = { new Placeholder("%gamemode%", arg) };

                sender.sendMessage(messagesConfig.getMessage("unknown", placeholders));
            }
        } else {
            sender.sendMessage(messagesConfig.getMessage("usage"));
        }

        return true;
    }
}
