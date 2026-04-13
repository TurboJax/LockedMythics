package org.turbojax.lockedMythics;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
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
        LOCKS.add(new ModelDataLock(Material.MACE, 1003)); // Bloodbath Mace
        LOCKS.add(new ModelDataLock(Material.NETHERITE_AXE, 1002)); // Trance Axe
        LOCKS.add(new ModelDataLock(Material.NETHERITE_SWORD, 1001)); // Fubuki Sword
        LOCKS.add(new ModelDataLock(Material.NETHERITE_SWORD, 1004)); // King's Blade
        LOCKS.add(new ModelDataLock(Material.DRAGON_EGG, 1005)); // Dragon Egg

        // Altar SMP
        LOCKS.add(new ModelDataLock(Material.AMETHYST_SHARD, 4)); // Nuke Remote
        LOCKS.add(new ModelDataLock(Material.BOW, 1)); // Striker Bow
        LOCKS.add(new ModelDataLock(Material.CLAY_BALL, 4)); // Crazy Slots
        LOCKS.add(new ModelDataLock(Material.CROSSBOW, 1)); // Vulcan Crossbow
        LOCKS.add(new ModelDataLock(Material.CROSSBOW, 2)); // Pale Gun
        LOCKS.add(new ModelDataLock(Material.MACE, 15)); // Knightfall Lvl 1
        LOCKS.add(new ModelDataLock(Material.MACE, 16)); // Knightfall Lvl 2
        LOCKS.add(new ModelDataLock(Material.MACE, 17)); // Knightfall Lvl 3
        LOCKS.add(new ModelDataLock(Material.NETHERITE_AXE, 2)); // Paladin's Axe
        LOCKS.add(new ModelDataLock(Material.NETHERITE_BOOTS, 1)); // Copper Boots
        LOCKS.add(new ModelDataLock(Material.NETHERITE_CHESTPLATE, 1)); // Copper Chestplate
        LOCKS.add(new ModelDataLock(Material.NETHERITE_HELMET, 1)); // Copper Helmet
        LOCKS.add(new ModelDataLock(Material.NETHERITE_LEGGINGS, 1)); // Copper Leggings
        LOCKS.add(new ModelDataLock(Material.NETHERITE_PICKAXE, 1)); // Copper Pickaxe
        LOCKS.add(new ModelDataLock(Material.NETHERITE_SWORD, 1)); // Bone Blade
        LOCKS.add(new ModelDataLock(Material.NETHERITE_SWORD, 2)); // Wand of Illusion
        LOCKS.add(new ModelDataLock(Material.NETHERITE_SWORD, 3)); // Bloodlust
        LOCKS.add(new ModelDataLock(Material.NETHERITE_SWORD, 4)); // Frost Scythe
        LOCKS.add(new ModelDataLock(Material.NETHERITE_SWORD, 5)); // Nightpiercer
        LOCKS.add(new ModelDataLock(Material.NETHERITE_SWORD, 6)); // Hypherion
        LOCKS.add(new ModelDataLock(Material.NETHERITE_SWORD, 7)); // Pure Blade
        LOCKS.add(new ModelDataLock(Material.NETHERITE_SWORD, 8)); // Earth Gautlet
        LOCKS.add(new ModelDataLock(Material.NETHERITE_SWORD, 9)); // Withered Blade
        LOCKS.add(new ModelDataLock(Material.NETHERITE_SWORD, 10)); // Shadow Blade
        LOCKS.add(new ModelDataLock(Material.NETHERITE_SWORD, 11)); // Cutlass
        LOCKS.add(new ModelDataLock(Material.NETHERITE_SWORD, 12)); // Windweaver
        LOCKS.add(new ModelDataLock(Material.NETHERITE_SWORD, 13)); // Echo
        LOCKS.add(new ModelDataLock(Material.NETHERITE_SWORD, 14)); // Eclipse

        // Trims SMP
        LOCKS.add(new ModelDataLock(Material.DIAMOND_SWORD, 1141));

        // Crazy Mythicals
        LOCKS.add(new ItemModelLock(Material.CROSSBOW, new NamespacedKey("cm", "item/weapons/dragon"))); // Dragon Cannon
        LOCKS.add(new ItemModelLock(Material.NETHERITE_SWORD, new NamespacedKey("cm", "item/weapons/dagger"))); // Dagger
        LOCKS.add(new ItemModelLock(Material.NETHERITE_SWORD, new NamespacedKey("cm", "item/weapons/hammer"))); // Greathammer
        LOCKS.add(new ItemModelLock(Material.SHIELD, new NamespacedKey("cm", "item/weapons/ghost_shield"))); // Ghost shield
        LOCKS.add(new ItemModelLock(Material.NETHERITE_SWORD, new NamespacedKey("cm", "item/weapons/judge"))); // Judge
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
