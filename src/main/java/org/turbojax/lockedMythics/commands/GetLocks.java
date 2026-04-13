package org.turbojax.lockedMythics.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.turbojax.lockedMythics.Main;
import org.turbojax.lockedMythics.SqliteDataManager;
import org.turbojax.lockedMythics.locks.Lock;

import java.util.Collection;
import java.util.List;

public class GetLocks implements BasicCommand {
    private final SqliteDataManager dataManager;

    public GetLocks(final SqliteDataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String @NotNull [] args) {
        CommandSender sender = commandSourceStack.getSender();

        String playerName = args[0];
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);

        // Getting the locks the player has
        List<Lock> locks = dataManager.getLocks(player);

        // Handling when the player has no active locks
        if (locks.isEmpty()) {
            sender.sendMessage(Component.text(playerName + " has no active locks."));
            return;
        }

        sender.sendMessage(Component.text(playerName + "'s Active Locks:"));
        // If the number of applied locks == the number of locks total, then the player has every lock so it prints "*".
        if (locks.size() == Main.LOCKS.size()) {
            sender.sendMessage(Component.text("- *"));
            return;
        }

        // Displaying each of the player's lock
        for (Lock lock : locks) {
            sender.sendMessage(Component.text("- " + lock.getId()));
        }
    }

    @Override
    public @NotNull Collection<String> suggest(@NotNull CommandSourceStack commandSourceStack, String[] args) {
        // TODO: Filter by partial argument
        if (args.length <= 1) return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();

        return List.of();
    }

    @Override
    public @NotNull String permission() {
        return "lockedmythics.getlocks";
    }
}
