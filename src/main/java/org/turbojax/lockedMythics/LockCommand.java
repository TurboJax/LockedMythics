package org.turbojax.lockedMythics;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.stream.Stream;

public class LockCommand implements BasicCommand {
    private final boolean isLock;
    private final String label;
    private final String permission;

    public LockCommand(boolean isLock) {

        this.isLock = isLock;
        this.label = isLock ? "lock" : "unlock";
        this.permission = isLock ? "lockedmythics.lock" : "lockedmythics.unlock";
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        CommandSender sender = commandSourceStack.getSender();

        if (args.length == 0) {
            sender.sendMessage(Component.text("Usage: /" + label + " <player>", NamedTextColor.YELLOW));
        }

        for (String playerName : args) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
            if (!player.isOnline()) {
                sender.sendMessage(Component.text("Could not find player " + playerName + ".",  NamedTextColor.RED));
                return;
            }

            if (isLock) {
                if (player.getPersistentDataContainer().has(Main.MYTHIC_LOCK)) {
                    sender.sendMessage(playerName + " already has a lock.");
                    return;
                }

                player.getPersistentDataContainer();
            } else {

            }

        }
    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        return Stream.of(Bukkit.getOfflinePlayers()).map(OfflinePlayer::getName).toList();
    }

    @Override
    public @Nullable String permission() {
        return permission;
    }
}
