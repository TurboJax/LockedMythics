package org.turbojax.lockedMythics.locks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CustomModelDataLock implements Lock {
    // Required params
    private final String id;
    private final int customModelData;

    // Optional params
    private final Material material;

    public CustomModelDataLock(String id, int customModelData) {
        this(id, customModelData, null);
    }

    public CustomModelDataLock(String id, int customModelData, Material material) {
        this.id = id;
        this.customModelData = customModelData;
        this.material = material;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean matches(ItemStack item) {
        if (item == null) return false;
        if (material != null && item.getType() != material) return false;
        if (item.getItemMeta() == null) return false;
        if (item.getItemMeta().getCustomModelDataComponent().getFloats().isEmpty()) return false;
        return item.getItemMeta().getCustomModelDataComponent().getFloats().getFirst() == customModelData;
    }
}
