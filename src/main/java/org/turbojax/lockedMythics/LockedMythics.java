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
        this.dataManager = new SqliteDataManager(this);
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
        addLock(new ModelDataLock("eclipse.bloodbath_mace", Material.MACE, 1003));
        addLock(new ModelDataLock("eclipse.trance_axe", Material.NETHERITE_AXE, 1002));
        addLock(new ModelDataLock("eclipse.fubuki_sword", Material.NETHERITE_SWORD, 1001));
        addLock(new ModelDataLock("eclipse.kings_blade", Material.NETHERITE_SWORD, 1004));
        addLock(new ModelDataLock("eclipse.dragon_egg", Material.DRAGON_EGG, 1005));

        // Altar SMP
        addLock(new ModelDataLock("altar.nuke_remote", Material.AMETHYST_SHARD, 4));
        addLock(new ModelDataLock("altar.striker_bow", Material.BOW, 1));
        addLock(new ModelDataLock("altar.crazy_slots", Material.CLAY_BALL, 4));
        addLock(new ModelDataLock("altar.vulcan_crossbow", Material.CROSSBOW, 1));
        addLock(new ModelDataLock("altar.pale_gun", Material.CROSSBOW, 2));
        addLock(new ModelDataLock("altar.knightfall_1", Material.MACE, 15));
        addLock(new ModelDataLock("altar.knightfall_2", Material.MACE, 16));
        addLock(new ModelDataLock("altar.knightfall_3", Material.MACE, 17));
        addLock(new ModelDataLock("altar.paladins_axe", Material.NETHERITE_AXE, 2));
        addLock(new ModelDataLock("altar.copper_boots", Material.NETHERITE_BOOTS, 1));
        addLock(new ModelDataLock("altar.copper_chestplate", Material.NETHERITE_CHESTPLATE, 1));
        addLock(new ModelDataLock("altar.copper_helmet", Material.NETHERITE_HELMET, 1));
        addLock(new ModelDataLock("altar.copper_leggings", Material.NETHERITE_LEGGINGS, 1));
        addLock(new ModelDataLock("altar.copper_pickaxe", Material.NETHERITE_PICKAXE, 1));
        addLock(new ModelDataLock("altar.bone_blade", Material.NETHERITE_SWORD, 1));
        addLock(new ModelDataLock("altar.wand_of_illusion", Material.NETHERITE_SWORD, 2));
        addLock(new ModelDataLock("altar.bloodlust", Material.NETHERITE_SWORD, 3));
        addLock(new ModelDataLock("altar.frost_scythe", Material.NETHERITE_SWORD, 4));
        addLock(new ModelDataLock("altar.nightpiercer", Material.NETHERITE_SWORD, 5));
        addLock(new ModelDataLock("altar.hyperion", Material.NETHERITE_SWORD, 6));
        addLock(new ModelDataLock("altar.pure_blade", Material.NETHERITE_SWORD, 7));
        addLock(new ModelDataLock("altar.earth_gauntlet", Material.NETHERITE_SWORD, 8));
        addLock(new ModelDataLock("altar.withered_blade", Material.NETHERITE_SWORD, 9));
        addLock(new ModelDataLock("altar.shadow_blade", Material.NETHERITE_SWORD, 10));
        addLock(new ModelDataLock("altar.cutlass", Material.NETHERITE_SWORD, 11));
        addLock(new ModelDataLock("altar.windweaver", Material.NETHERITE_SWORD, 12));
        addLock(new ModelDataLock("altar.echo", Material.NETHERITE_SWORD, 13));
        addLock(new ModelDataLock("altar.eclipse", Material.NETHERITE_SWORD, 14));

        // Trims SMP
        addLock(new ModelDataLock("trims.dragon_greatblade", Material.DIAMOND_SWORD, 1141));

        // Crazy Mythicals
        addLock(new ItemModelLock("crazymyth.dragon_cannon", Material.CROSSBOW, new NamespacedKey("cm", "item/weapons/dragon")));
        addLock(new ItemModelLock("crazymyth.hourglass_dagger", Material.NETHERITE_SWORD, new NamespacedKey("cm", "item/weapons/dagger")));
        addLock(new ItemModelLock("crazymyth.great_hammer", Material.NETHERITE_SWORD, new NamespacedKey("cm", "item/weapons/hammer")));
        addLock(new ItemModelLock("crazymyth.ghost_shield", Material.SHIELD, new NamespacedKey("cm", "item/weapons/ghost_shield")));
        addLock(new ItemModelLock("crazymyth.the_judge", Material.NETHERITE_SWORD, new NamespacedKey("cm", "item/weapons/judge")));

        // ManePear Scythe
        addLock(new ModelDataLock("mpscythe.manepear_scythe", Material.MACE, 9999));
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