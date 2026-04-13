package org.turbojax.lockedMythics.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.turbojax.lockedMythics.LockedMythics;
import org.turbojax.lockedMythics.SqliteDataManager;
import org.turbojax.lockedMythics.locks.Lock;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class GetLocks implements BasicCommand {
    private final SqliteDataManager dataManager;

    public GetLocks(final SqliteDataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String @NotNull [] args) {
        Audience sender = commandSourceStack.getSender();

        if (commandSourceStack.getExecutor() != null) {
            sender = commandSourceStack.getExecutor();
        }

        if (args.length == 0) {
            sender.sendMessage(Component.text("Usage: /getlocks <player>", NamedTextColor.YELLOW));
            return;
        }

        String playerName = args[0];
        OfflinePlayer player;
        if (playerName.equalsIgnoreCase("@p")) {
            player = TargetSelectors.nearestPlayer(commandSourceStack);

            // Logging is already done by TargetSelectors
            if (player == null) {
                return;
            }

            playerName = player.getName();
        } else {
            player = Bukkit.getOfflinePlayer(playerName);
        }

        // Getting the locks the player has
        List<Lock> locks = dataManager.getLocks(player);

        // Handling when the player has no active locks
        if (locks.isEmpty()) {
            sender.sendMessage(Component.text(playerName + " has no active locks.", NamedTextColor.GOLD));
            return;
        }

        sender.sendMessage(Component.text(playerName + "'s Active Locks:", NamedTextColor.GOLD));
        // If the number of applied locks == the number of locks total, then the player has every lock so it prints "*".
        if (locks.size() == LockedMythics.LOCKS.size()) {
            sender.sendMessage(Component.text("- *", NamedTextColor.GOLD));
            return;
        }

        // Displaying each of the player's lock
        for (Lock lock : locks) {
            sender.sendMessage(Component.text("- " + lock.getId(), NamedTextColor.GOLD));
        }
    }

    @Override
    public @NotNull Collection<String> suggest(@NotNull CommandSourceStack commandSourceStack, String[] args) {
        Stream<String> stream = Stream.empty();
        if (args.length <= 1) stream = Stream.concat(Stream.of("@p"), Bukkit.getOnlinePlayers().stream().map(Player::getName));

        // Filtering by what was entered
        if (args.length > 0) {
            stream = stream.filter(s -> s.startsWith(args[args.length - 1]));
        }

        return stream.toList();
    }

    @Override
    public @NotNull String permission() {
        return "lockedmythics.getlocks";
    }
}
