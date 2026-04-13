package org.turbojax.lockedMythics;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.turbojax.lockedMythics.locks.ItemModelLock;
import org.turbojax.lockedMythics.locks.Lock;
import org.turbojax.lockedMythics.locks.ModelDataLock;

import java.util.List;
import java.util.Objects;

public class MainConfig {
    private final LockedMythics plugin;
    private Configuration config;

    public MainConfig(LockedMythics plugin) {
        this.plugin = plugin;
    }

    public void load() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        config = plugin.getConfig();
    }

    public void save() {
        plugin.saveConfig();
    }

    public Lock parseLock(String id) {
        ConfigurationSection section = config.getConfigurationSection("locks." + id);
        if (section == null) {
            LockedMythics.LOGGER.error("Could not parse section locks.{} because it doesn't exist.", id);
            return null;
        }
        String type = section.getString("type");

        String matName;
        Material material;
        return switch (type) {
            case "ITEM_MODEL":
                // Getting the material
                matName = section.getString("material");
                if (matName == null) {
                    LockedMythics.LOGGER.error("Lock {} is missing the material field", id);
                    yield null;
                }

                material = Material.getMaterial(matName);
                if (material == null) {
                    LockedMythics.LOGGER.error("Lock {} has an invalid material \"{}\"", id, matName);
                    yield null;
                }

                // Getting the model key
                String modelKey = section.getString("model_key");
                if (modelKey == null) {
                    LockedMythics.LOGGER.error("Lock {} is missing the model_key field", id);
                    yield null;
                }

                yield new ItemModelLock(id, material, NamespacedKey.fromString(modelKey));
            case "MODEL_DATA":
                // Getting the material
                matName = section.getString("material");
                if (matName == null) {
                    LockedMythics.LOGGER.error("Lock {} is missing the material field", id);
                    yield null;
                }

                material = Material.getMaterial(matName);
                if (material == null) {
                    LockedMythics.LOGGER.error("Lock {} has an invalid material \"{}\"", id, matName);
                    yield null;
                }

                // Getting the custom model data
                int customModelData = section.getInt("custom_model_data", -1);
                if (customModelData == -1) {
                    LockedMythics.LOGGER.error("Lock {} is missing the custom_model_data field", id);
                    yield null;
                }

                yield new ModelDataLock(id, material, customModelData);
            case null:
            default:
                LockedMythics.LOGGER.error("Lock {} has an invalid lock type", id);
                yield null;
        };
    }

    public List<Lock> getLocks() {
        ConfigurationSection section = config.getConfigurationSection("locks");
        if (section == null) {
            LockedMythics.LOGGER.error("Missing the locks section.  Returning empty list.");
            return List.of();
        }
        return section.getKeys(false).stream().map(this::parseLock).filter(Objects::nonNull).toList();
    }
}
