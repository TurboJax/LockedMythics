# ItemLocker
This plugin prevents players from picking up or looting certain items based off of configurable properties.

## Commands
- `/addlock <player> <locks|*>`: Adds all the specified locks to the player. ("\*" adds every lock)  
- `/getlocks <player>`: Gets all the locks attached to the player. ("\*" if they have every lock)  
- `/reloadlocks`: Reloads all the locks from the config.
- `/removelock <player> <locks|*>`: Removes all the specified locks from the player. ("\*" removes every lock)

## Permissions
- `lockedmythics.addlock`: Allows a player to use /addlock
- `lockedmythics.getlocks`: Allows a player to use /getlocks
- `lockedmythics.reloadlocks`: Allows a player to use /reloadlocks
- `lockedmythics.removelock`: Allows a player to use /removelocks
- `lockedmythics.admin`: Allows a player to use all the commands (Operators have this by default)

## Configuration
Each entry in the config.yml file is a different lock.  
The key is the lock id, which is used to identify what lock to add or remove from people.  
Each lock has a required "type" object, which tells the program what type of lock needs to be loaded.  
See below for the different lock types.

### Lock Types
#### ITEM_MODEL
This determines whether an item should be locked by checking if it is tagged with a specific item model.  
It requires the "item_model" key, which should be a string pointing to the item model defined in the resource pack.  
If it points the default minecraft namespace, the namespace can be omitted.  
You can also provide an optional Material requirement so the lock only applies to items with the model and material.  

Example:
```yml
item_model_example:
  type: ITEM_MODEL
  item_model: "namespace:path/to/model"
  material: MACE # OPTIONAL
```

#### CUSTOM_MODEL_DATA
This determines whether an item should be locked by checking if it has the provided model data.  
It requires the "custom_model_data" key, which should be a number.  
You can also provide an optional Material requirement so the lock only applies to items with the custom model data and material.  

Example:
```yml
item_model_example:
  type: CUSTOM_MODEL_DATA
  custom_model_data: 1
  material: MACE # OPTIONAL
```