package org.turbojax.lockedMythics.locks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface Lock {
    String getId();
    Material getMaterial();
    boolean matches(ItemStack item);
}
