package org.turbojax.lockedMythics.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.turbojax.lockedMythics.LockedMythics;
import org.turbojax.lockedMythics.SqliteDataManager;
import org.turbojax.lockedMythics.locks.Lock;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class AddLock implements BasicCommand {
    private final SqliteDataManager dataManager;

    public AddLock(final SqliteDataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String @NotNull [] args) {
        Audience sender = commandSourceStack.getSender();

        if (commandSourceStack.getExecutor() != null) {
            sender = commandSourceStack.getExecutor();
        }

        if (args.length < 2) {
            sender.sendMessage(Component.text("Usage: /addlock <player> <locks|*>", NamedTextColor.YELLOW));
        }

        // Getting the player
        String playerName = args[0];
        OfflinePlayer player;
        if (playerName.startsWith("@")) {
            List<Player> players = Bukkit.selectEntities(commandSourceStack.getSender(), playerName)
                    .stream()
                    .filter(entity -> entity instanceof Player)
                    .map(p -> (Player) p)
                    .toList();

            if (players.isEmpty()) {
                LockedMythics.LOGGER.error("No players found");
                return;
            }

            if (players.size() > 1) {
                LockedMythics.LOGGER.error("Too many results");
                return;
            }

            player = players.getFirst();
            playerName = player.getName();
        } else {
            player = Bukkit.getOfflinePlayer(playerName);
        }

        // Running through each lock passed to the command
        for (int i = 1; i < args.length; i++) {
            String lockId = args[i];

            // Handling the '*' argument
            if (lockId.equals("*")) {
                for (Lock lock : LockedMythics.LOCKS.values()) {
                    dataManager.addLock(player, lock);
                }
                sender.sendMessage(Component.text("Applied all locks to " + playerName, NamedTextColor.GOLD));
                return;
            }

            Lock lock = LockedMythics.LOCKS.get(lockId);
            if (lock == null) {
                sender.sendMessage(Component.text("Could not find lock \"" + lockId + "\"!", NamedTextColor.RED));
                continue;
            }

            // Adding the lock to the player
            dataManager.addLock(player, lock);
            sender.sendMessage(Component.text("Added the \"" + lockId + "\" lock to " + playerName, NamedTextColor.GOLD));
        }
    }

    @Override
    public @NotNull Collection<String> suggest(@NotNull CommandSourceStack commandSourceStack, String[] args) {
        Stream<String> stream;
        if (args.length <= 1) {
            stream = Stream.concat(Stream.of("@p"), Bukkit.getOnlinePlayers().stream().map(Player::getName));
        } else {
            stream = Stream.concat(Stream.of("*"), LockedMythics.LOCKS.keySet().stream());
        }

        // Filtering by what was entered
        if (args.length > 0) {
            stream = stream.filter(s -> s.startsWith(args[args.length - 1]));
        }

        return stream.toList();
    }

    @Override
    public @NotNull String permission() {
        return "lockedmythics.addlock";
    }
}
