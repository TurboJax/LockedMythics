package org.turbojax.lockedMythics;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
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

public final class LockedMythics extends JavaPlugin implements Listener {
    public static final Logger LOGGER = LoggerFactory.getLogger("LockedMythics");
    public static final Map<String,Lock> LOCKS = new HashMap<>();

    private final SqliteDataManager dataManager;

    public LockedMythics() {
        this.dataManager = new SqliteDataManager();
    }

    @Override
    public void onEnable() {
        // Loading the sqlite data
        dataManager.load();

        // Adding the default locks to the map
        loadDefaultLocks();

        // Registering plugin commands
        registerCommand("addlock", new AddLock(dataManager));
        registerCommand("getlocks", new GetLocks(dataManager));
        registerCommand("reloadlocks", new ReloadLocks(dataManager));
        registerCommand("removelock", new RemoveLock(dataManager));

        // Registering plugin event listeners
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    public void loadDefaultLocks() {
        // Eclipse Mythics
        addLock(new ModelDataLock("Bloodbath Mace", Material.MACE, 1003));
        addLock(new ModelDataLock("Trance Axe", Material.NETHERITE_AXE, 1002));
        addLock(new ModelDataLock("Fubuki Sword", Material.NETHERITE_SWORD, 1001));
        addLock(new ModelDataLock("King's Blade", Material.NETHERITE_SWORD, 1004));
        addLock(new ModelDataLock("Dragon Egg", Material.DRAGON_EGG, 1005));

        // Altar SMP
        addLock(new ModelDataLock("Nuke Remote", Material.AMETHYST_SHARD, 4));
        addLock(new ModelDataLock("Striker Bow", Material.BOW, 1));
        addLock(new ModelDataLock("Crazy Slots", Material.CLAY_BALL, 4));
        addLock(new ModelDataLock("Vulcan Crossbow", Material.CROSSBOW, 1));
        addLock(new ModelDataLock("Pale Gun", Material.CROSSBOW, 2));
        addLock(new ModelDataLock("Knightfall Lvl 1", Material.MACE, 15));
        addLock(new ModelDataLock("Knightfall Lvl 2", Material.MACE, 16));
        addLock(new ModelDataLock("Knightfall Lvl 3", Material.MACE, 17));
        addLock(new ModelDataLock("Paladin's Axe", Material.NETHERITE_AXE, 2));
        addLock(new ModelDataLock("Copper Boots", Material.NETHERITE_BOOTS, 1));
        addLock(new ModelDataLock("Copper Chestplate", Material.NETHERITE_CHESTPLATE, 1));
        addLock(new ModelDataLock("Copper Helmet", Material.NETHERITE_HELMET, 1));
        addLock(new ModelDataLock("Copper Leggings", Material.NETHERITE_LEGGINGS, 1));
        addLock(new ModelDataLock("Copper Pickaxe", Material.NETHERITE_PICKAXE, 1));
        addLock(new ModelDataLock("Bone Blade", Material.NETHERITE_SWORD, 1));
        addLock(new ModelDataLock("Wand of Illusion", Material.NETHERITE_SWORD, 2));
        addLock(new ModelDataLock("Bloodlust", Material.NETHERITE_SWORD, 3));
        addLock(new ModelDataLock("Frost Scythe", Material.NETHERITE_SWORD, 4));
        addLock(new ModelDataLock("Nightpiercer", Material.NETHERITE_SWORD, 5));
        addLock(new ModelDataLock("Hyperion", Material.NETHERITE_SWORD, 6));
        addLock(new ModelDataLock("Pure Blade", Material.NETHERITE_SWORD, 7));
        addLock(new ModelDataLock("Earth Gautlet", Material.NETHERITE_SWORD, 8));
        addLock(new ModelDataLock("Withered Blade", Material.NETHERITE_SWORD, 9));
        addLock(new ModelDataLock("Shadow Blade", Material.NETHERITE_SWORD, 10));
        addLock(new ModelDataLock("Cutlass", Material.NETHERITE_SWORD, 11));
        addLock(new ModelDataLock("Windweaver", Material.NETHERITE_SWORD, 12));
        addLock(new ModelDataLock("Echo", Material.NETHERITE_SWORD, 13));
        addLock(new ModelDataLock("Eclipse", Material.NETHERITE_SWORD, 14));

        // Trims SMP
        addLock(new ModelDataLock("Dragon Greatblade", Material.DIAMOND_SWORD, 1141));

        // Crazy Mythicals
        addLock(new ItemModelLock("Dragon Cannon", Material.CROSSBOW, new NamespacedKey("cm", "item/weapons/dragon")));
        addLock(new ItemModelLock("Hourglass Dagger", Material.NETHERITE_SWORD, new NamespacedKey("cm", "item/weapons/dagger")));
        addLock(new ItemModelLock("Great hammer", Material.NETHERITE_SWORD, new NamespacedKey("cm", "item/weapons/hammer")));
        addLock(new ItemModelLock("Ghost Shield", Material.SHIELD, new NamespacedKey("cm", "item/weapons/ghost_shield")));
        addLock(new ItemModelLock("The Judge", Material.NETHERITE_SWORD, new NamespacedKey("cm", "item/weapons/judge")));

        // ManePear Scythe
        addLock(new ModelDataLock("ManePear Scythe", Material.MACE, 9999));
    }

    public void addLock(Lock lock) {
        LOCKS.put(lock.getId(), lock);
    }

    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack item = event.getItem().getItemStack();

        dataManager.getLocks(player).stream().filter(lock -> lock.matches(item)).findAny().ifPresent(lock -> {
            event.setCancelled(true);
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}