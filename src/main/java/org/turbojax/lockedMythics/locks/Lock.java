package org.turbojax.lockedMythics.locks;

import org.bukkit.inventory.ItemStack;

public interface Lock {
    String getId();
    boolean matches(ItemStack item);
}
