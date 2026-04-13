package org.turbojax.lockedMythics.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;
import org.turbojax.lockedMythics.MainConfig;

public class ReloadLocks implements BasicCommand {
    private final MainConfig config;

    public ReloadLocks(MainConfig config) {
        this.config = config;
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String @NotNull [] args) {
        config.load();
        commandSourceStack.getSender().sendMessage(Component.text("Reloaded the config!", NamedTextColor.GOLD));
    }

    @Override
    public @NotNull String permission() {
        return "lockedmythics.reloadlocks";
    }
}
