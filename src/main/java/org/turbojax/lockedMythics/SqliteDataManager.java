package org.turbojax.lockedMythics;

public class SqliteDataManager {
    public void load() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
