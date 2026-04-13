package org.turbojax.lockedMythics;

import org.bukkit.OfflinePlayer;
import org.sqlite.SQLiteDataSource;
import org.turbojax.lockedMythics.locks.Lock;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqliteDataManager {
    private final SQLiteDataSource dataSource;

    public SqliteDataManager(LockedMythics plugin) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:" + dataFolder + "/database.db");
    }

    /** Loads the tables for the SQLite database. */
    public void load() {
        try(Connection connection = dataSource.getConnection()) {
            // Creating the database
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS locks(uuid BLOB, lock_id TEXT);").execute();
        } catch (SQLException e) {
            LockedMythics.LOGGER.error("Error while creating tables.", e);
        }
    }

    /**
     * Adds a lock to a player.
     *
     * @param player The player to add a lock to
     * @param lock The lock to add
     */
    public void addLock(OfflinePlayer player, Lock lock) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO locks (uuid, lock_id) VALUES (?, ?);");
            stmt.setBytes(1, player.getUniqueId().toString().getBytes());
            stmt.setString(2, lock.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LockedMythics.LOGGER.error("Error while adding lock {} to {}.", lock.getId(), player.getName(), e);
        }
    }

    /**
     * Removes a lock from a player.
     *
     * @param player The player to remove a lock from
     * @param lock The lock to remove
     */
    public void removeLock(OfflinePlayer player, Lock lock) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM locks WHERE uuid = ? AND lock_id LIKE ?;");
            stmt.setBytes(1, player.getUniqueId().toString().getBytes());
            stmt.setString(2, lock.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LockedMythics.LOGGER.error("Error while removing lock {} from {}.", lock.getId(), player.getName(), e);
        }
    }

    /**
     * Checks if a player has the provided lock.
     *
     * @param player The player whose locks to check
     * @param lock The lock to look for
     * @return true if the player has the lock applied to them.  False otherwise
     */
    public boolean hasLock(OfflinePlayer player, Lock lock) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM locks WHERE uuid = ? AND lock_id LIKE ?;");
            stmt.setBytes(1, player.getUniqueId().toString().getBytes());
            stmt.setString(2, lock.getId());
            ResultSet results = stmt.executeQuery();

            // Just looking for any lock here
            return results.next();
        } catch (SQLException e) {
            LockedMythics.LOGGER.error("Error while checking if {} has lock {}.", player.getName(), lock.getId(), e);
        }

        return false;
    }

    public List<Lock> getLocks(OfflinePlayer player) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT lock_id FROM locks WHERE uuid = ?;");
            stmt.setBytes(1, player.getUniqueId().toString().getBytes());
            ResultSet results = stmt.executeQuery();

            List<Lock> locks = new ArrayList<>();
            while (results.next()) {
                String lockId = results.getString(1);
                Lock lock = LockedMythics.LOCKS.get(lockId);
                if (lock != null) locks.add(lock);
            }

            return locks;
        } catch (SQLException e) {
            LockedMythics.LOGGER.error("Error while getting {}'s locks.", player.getName(), e);
        }

        return List.of();
    }
}
