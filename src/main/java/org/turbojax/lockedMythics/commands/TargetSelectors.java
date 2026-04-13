package org.turbojax.lockedMythics.commands;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class TargetSelectors {
    public static Player nearestPlayer(CommandSourceStack ctx) {
        Player nearest = null;
        double minDist = Double.MAX_VALUE;
        for (Player p : ctx.getLocation().getWorld().getPlayers()) {
            double dist = p.getLocation().distance(ctx.getLocation());
            if (dist < minDist) {
                minDist = dist;
                nearest = p;
            }
        }

        if (nearest == null) {
            ctx.getSender().sendMessage(Component.text("No target found", NamedTextColor.RED));
        }

        return nearest;
    }
}
