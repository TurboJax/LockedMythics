package org.turbojax.lockedMythics.locks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ModelDataLock implements Lock {
    private final Material material;
    private final int customModelData;

    public ModelDataLock(Material material, int customModelData) {
        this.material = material;
        this.customModelData = customModelData;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    @Override
    public boolean matches(ItemStack item) {
        if (item == null) return false;
        if (item.getType() != material) return false;
        if (item.getItemMeta() == null) return false;
        return item.getItemMeta().getCustomModelDataComponent().getFloats().getFirst() == customModelData;
    }
}
