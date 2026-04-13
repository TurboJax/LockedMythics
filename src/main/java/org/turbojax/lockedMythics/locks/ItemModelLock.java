package org.turbojax.lockedMythics.locks;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class ItemModelLock implements Lock {
    private final String id;
    private final Material material;
    private final NamespacedKey modelKey;

    public ItemModelLock(String id, Material material, NamespacedKey modelKey) {
        this.id = id;
        this.material = material;
        this.modelKey = modelKey;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public boolean matches(ItemStack item) {
        if (item == null) return false;
        if (item.getType() != material) return false;
        if (item.getItemMeta() == null) return false;

        NamespacedKey appliedModel = item.getItemMeta().getItemModel();
        if (appliedModel == null) return false;
        return modelKey.equals(appliedModel);
    }
}
