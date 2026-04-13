package org.turbojax.lockedMythics.locks;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class ItemModelLock implements Lock {
    private final String id;
    private final NamespacedKey modelKey;
    private final Material material;

    public ItemModelLock(String id, NamespacedKey modelKey) {
        this(id, modelKey, null);
    }

    public ItemModelLock(String id, NamespacedKey modelKey, Material material) {
        this.id = id;
        this.modelKey = modelKey;
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

        NamespacedKey appliedModel = item.getItemMeta().getItemModel();
        if (appliedModel == null) return false;
        return modelKey.equals(appliedModel);
    }
}
