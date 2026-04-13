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
import org.turbojax.lockedMythics.locks.ItemModelLock;
import org.turbojax.lockedMythics.locks.Lock;
import org.turbojax.lockedMythics.locks.ModelDataLock;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin implements Listener {
    public static final Logger LOGGER = LoggerFactory.getLogger("LockedMythics");
    public static final NamespacedKey MYTHIC_LOCK = new NamespacedKey("locked-mythics", "lock");
    public static final List<Lock> LOCKS = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        loadDefaultLocks();

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    public void loadDefaultLocks() {
        // Eclipse Mythics
        LOCKS.add(new ModelDataLock("Bloodbath Mace", Material.MACE, 1003));
        LOCKS.add(new ModelDataLock("Trance Axe", Material.NETHERITE_AXE, 1002));
        LOCKS.add(new ModelDataLock("Fubuki Sword", Material.NETHERITE_SWORD, 1001));
        LOCKS.add(new ModelDataLock("King's Blade", Material.NETHERITE_SWORD, 1004));
        LOCKS.add(new ModelDataLock("Dragon Egg", Material.DRAGON_EGG, 1005));

        // Altar SMP
        LOCKS.add(new ModelDataLock("Nuke Remote", Material.AMETHYST_SHARD, 4));
        LOCKS.add(new ModelDataLock("Striker Bow", Material.BOW, 1));
        LOCKS.add(new ModelDataLock("Crazy Slots", Material.CLAY_BALL, 4));
        LOCKS.add(new ModelDataLock("Vulcan Crossbow", Material.CROSSBOW, 1));
        LOCKS.add(new ModelDataLock("Pale Gun", Material.CROSSBOW, 2));
        LOCKS.add(new ModelDataLock("Knightfall Lvl 1", Material.MACE, 15));
        LOCKS.add(new ModelDataLock("Knightfall Lvl 2", Material.MACE, 16));
        LOCKS.add(new ModelDataLock("Knightfall Lvl 3", Material.MACE, 17));
        LOCKS.add(new ModelDataLock("Paladin's Axe", Material.NETHERITE_AXE, 2));
        LOCKS.add(new ModelDataLock("Copper Boots", Material.NETHERITE_BOOTS, 1));
        LOCKS.add(new ModelDataLock("Copper Chestplate", Material.NETHERITE_CHESTPLATE, 1));
        LOCKS.add(new ModelDataLock("Copper Helmet", Material.NETHERITE_HELMET, 1));
        LOCKS.add(new ModelDataLock("Copper Leggings", Material.NETHERITE_LEGGINGS, 1));
        LOCKS.add(new ModelDataLock("Copper Pickaxe", Material.NETHERITE_PICKAXE, 1));
        LOCKS.add(new ModelDataLock("Bone Blade", Material.NETHERITE_SWORD, 1));
        LOCKS.add(new ModelDataLock("Wand of Illusion", Material.NETHERITE_SWORD, 2));
        LOCKS.add(new ModelDataLock("Bloodlust", Material.NETHERITE_SWORD, 3));
        LOCKS.add(new ModelDataLock("Frost Scythe", Material.NETHERITE_SWORD, 4));
        LOCKS.add(new ModelDataLock("Nightpiercer", Material.NETHERITE_SWORD, 5));
        LOCKS.add(new ModelDataLock("Hyperion", Material.NETHERITE_SWORD, 6));
        LOCKS.add(new ModelDataLock("Pure Blade", Material.NETHERITE_SWORD, 7));
        LOCKS.add(new ModelDataLock("Earth Gautlet", Material.NETHERITE_SWORD, 8));
        LOCKS.add(new ModelDataLock("Withered Blade", Material.NETHERITE_SWORD, 9));
        LOCKS.add(new ModelDataLock("Shadow Blade", Material.NETHERITE_SWORD, 10));
        LOCKS.add(new ModelDataLock("Cutlass", Material.NETHERITE_SWORD, 11));
        LOCKS.add(new ModelDataLock("Windweaver", Material.NETHERITE_SWORD, 12));
        LOCKS.add(new ModelDataLock("Echo", Material.NETHERITE_SWORD, 13));
        LOCKS.add(new ModelDataLock("Eclipse", Material.NETHERITE_SWORD, 14));

        // Trims SMP
        LOCKS.add(new ModelDataLock("Dragon Greatblade", Material.DIAMOND_SWORD, 1141));

        // Crazy Mythicals
        LOCKS.add(new ItemModelLock("Dragon Cannon", Material.CROSSBOW, new NamespacedKey("cm", "item/weapons/dragon")));
        LOCKS.add(new ItemModelLock("Hourglass Dagger", Material.NETHERITE_SWORD, new NamespacedKey("cm", "item/weapons/dagger")));
        LOCKS.add(new ItemModelLock("Great hammer", Material.NETHERITE_SWORD, new NamespacedKey("cm", "item/weapons/hammer")));
        LOCKS.add(new ItemModelLock("Ghost Shield", Material.SHIELD, new NamespacedKey("cm", "item/weapons/ghost_shield")));
        LOCKS.add(new ItemModelLock("The Judge", Material.NETHERITE_SWORD, new NamespacedKey("cm", "item/weapons/judge")));
    }

    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack item = event.getItem().getItemStack();

        // Replace LOCKS with `dataManager.getActiveLocks(player)`
        LOCKS.stream().filter(lock -> lock.matches(item)).findAny().ifPresent(lock -> {
            event.setCancelled(true);
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
