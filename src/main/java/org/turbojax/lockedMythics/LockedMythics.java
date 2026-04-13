package org.turbojax.lockedMythics;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.turbojax.lockedMythics.commands.*;
import org.turbojax.lockedMythics.locks.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class LockedMythics extends JavaPlugin implements Listener {
    public static final Logger LOGGER = LoggerFactory.getLogger("LockedMythics");
    public static final Map<String,Lock> LOCKS = new HashMap<>();

    private final MainConfig mainConfig;
    private final SqliteDataManager dataManager;

    public LockedMythics() {
        this.mainConfig = new MainConfig(this);
        this.dataManager = new SqliteDataManager(this);
    }

    @Override
    public void onEnable() {
        // Loading the yaml config
        mainConfig.load();

        // Putting the yaml data into the hashmap
        mainConfig.getLocks().forEach(this::addLock);

        // Loading the sqlite data
        dataManager.load();

        // Registering plugin commands
        registerCommand("addlock", new AddLock(dataManager));
        registerCommand("getlocks", new GetLocks(dataManager));
        registerCommand("reloadlocks", new ReloadLocks(mainConfig));
        registerCommand("removelock", new RemoveLock(dataManager));

        // Registering plugin event listeners
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    public void addLock(Lock lock) {
        LOCKS.put(lock.getId(), lock);
    }

    public final Map<UUID,Long> recentPickups = new HashMap<>();

    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack item = event.getItem().getItemStack();

        dataManager.getLocks(player).stream().filter(lock -> lock.matches(item)).findAny().ifPresent(lock -> {
            // Preventing console spam by only letting the message play if it has been 2 seconds
            Long pickupTime = recentPickups.getOrDefault(player.getUniqueId(), 0L);
            if (pickupTime == 0 || pickupTime < System.currentTimeMillis()) {
                recentPickups.put(player.getUniqueId(), System.currentTimeMillis() + 2000);
                player.sendMessage(Component.text("You can't pick up ", NamedTextColor.RED).append(item.effectiveName()));
            }

            event.setCancelled(true);
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}