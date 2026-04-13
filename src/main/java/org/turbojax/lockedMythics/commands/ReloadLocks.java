package org.turbojax.lockedMythics.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.jetbrains.annotations.NotNull;
import org.turbojax.lockedMythics.SqliteDataManager;

public class ReloadLocks implements BasicCommand {
    private final SqliteDataManager dataManager;

    public ReloadLocks(SqliteDataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String @NotNull [] args) {
        dataManager.load();
        commandSourceStack.getSender().sendMessage("Reloaded all locks!");
    }

    @Override
    public @NotNull String permission() {
        return "lockedmythics.reloadlocks";
    }
}
