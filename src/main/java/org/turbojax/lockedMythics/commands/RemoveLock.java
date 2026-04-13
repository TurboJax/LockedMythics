package org.turbojax.lockedMythics.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.turbojax.lockedMythics.LockedMythics;
import org.turbojax.lockedMythics.SqliteDataManager;
import org.turbojax.lockedMythics.locks.Lock;

import java.util.Collection;
import java.util.stream.Stream;

public class RemoveLock implements BasicCommand {
    private final SqliteDataManager dataManager;

    public RemoveLock(SqliteDataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        Audience sender = commandSourceStack.getSender();

        if (commandSourceStack.getExecutor() != null) {
            sender = commandSourceStack.getExecutor();
        }

        if (args.length < 2) {
            sender.sendMessage(Component.text("Usage: /removelock <player> <locks|*>", NamedTextColor.YELLOW));
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
                    dataManager.removeLock(player, lock);
                }
                sender.sendMessage(Component.text("Removed all locks from " + playerName, NamedTextColor.GOLD));
                return;
            }

            Lock lock = LockedMythics.LOCKS.get(lockId);
            if (lock == null) {
                sender.sendMessage(Component.text("No lock \"" + lockId + "\" exists!", NamedTextColor.RED));
                continue;
            }

            // Removing the lock from the player
            dataManager.removeLock(player, lock);
            sender.sendMessage(Component.text("Removed the \"" + lockId + "\" lock from " + playerName, NamedTextColor.GOLD));
        }
    }

    @Override
    public @NotNull Collection<String> suggest(@NotNull CommandSourceStack commandSourceStack, String[] args) {
        // TODO: Filter by partial argument
        if (args.length <= 1) return Stream.of(Bukkit.getOfflinePlayers()).map(OfflinePlayer::getName).toList();

        OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
        return dataManager.getLocks(player).stream().map(Lock::getId).toList();
    }

    @Override
    public @NotNull String permission() {
        return "lockedmythics.removelocks";
    }
}
