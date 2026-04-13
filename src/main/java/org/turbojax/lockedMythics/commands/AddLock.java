package org.turbojax.lockedMythics.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.turbojax.lockedMythics.LockedMythics;
import org.turbojax.lockedMythics.SqliteDataManager;
import org.turbojax.lockedMythics.locks.Lock;

import java.util.Collection;
import java.util.stream.Stream;

public class AddLock implements BasicCommand {
    private final SqliteDataManager dataManager;

    public AddLock(final SqliteDataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        CommandSender sender = commandSourceStack.getSender();

        if (args.length < 2) {
            sender.sendMessage(Component.text("Usage: /addlock <player> <locks|*>", NamedTextColor.YELLOW));
        }

        // Getting the player
        String playerName = args[0];
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);

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
        // TODO: Filter by partial argument
        if (args.length <= 1) return Stream.of(Bukkit.getOfflinePlayers()).map(OfflinePlayer::getName).toList();
        return LockedMythics.LOCKS.keySet();
    }

    @Override
    public @NotNull String permission() {
        return "lockedmythics.addlock";
    }
}
