package org.turbojax.lockedMythics.locks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class MaterialLock implements Lock {
    private final String id;
    private final Material material;

    public MaterialLock(String id, Material material) {
        this.id = id;
        this.material = material;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean matches(ItemStack item) {
        if (item == null) return false;
        return item.getType() == material;
    }
}
