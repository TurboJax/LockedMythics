package org.turbojax.lockedMythics;

import org.bukkit.OfflinePlayer;
import org.turbojax.lockedMythics.locks.Lock;

public class SqliteDataManager {
    public void load() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void addLock(OfflinePlayer player, Lock lock) {}

    public void removeLock(OfflinePlayer player, Lock lock) {}

    public boolean hasLock(OfflinePlayer player, Lock lock) {}

    public List<Lock> getLocks(OfflinePlayer player) {}
}
