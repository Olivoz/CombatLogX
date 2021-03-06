package combatlogx.expansion.cheat.prevention;

import com.github.sirblobman.api.configuration.ConfigurationManager;
import com.github.sirblobman.api.utility.VersionUtility;
import com.github.sirblobman.combatlogx.api.ICombatLogX;
import com.github.sirblobman.combatlogx.api.expansion.Expansion;

import combatlogx.expansion.cheat.prevention.listener.CheatPreventionListener;
import combatlogx.expansion.cheat.prevention.listener.ListenerBlocks;
import combatlogx.expansion.cheat.prevention.listener.ListenerChat;
import combatlogx.expansion.cheat.prevention.listener.ListenerCommands;
import combatlogx.expansion.cheat.prevention.listener.ListenerDrop;
import combatlogx.expansion.cheat.prevention.listener.ListenerElytra;
import combatlogx.expansion.cheat.prevention.listener.ListenerEntities;
import combatlogx.expansion.cheat.prevention.listener.ListenerFlight;
import combatlogx.expansion.cheat.prevention.listener.ListenerGameMode;
import combatlogx.expansion.cheat.prevention.listener.ListenerInventories;
import combatlogx.expansion.cheat.prevention.listener.ListenerLegacyItemPickup;
import combatlogx.expansion.cheat.prevention.listener.ListenerModernItemPickup;
import combatlogx.expansion.cheat.prevention.listener.ListenerPotions;
import combatlogx.expansion.cheat.prevention.listener.ListenerRiptide;
import combatlogx.expansion.cheat.prevention.listener.ListenerTeleport;
import combatlogx.expansion.cheat.prevention.listener.ListenerTotem;

public final class CheatPreventionExpansion extends Expansion {
    public CheatPreventionExpansion(ICombatLogX plugin) {
        super(plugin);
    }

    @Override
    public void onLoad() {
        ConfigurationManager configurationManager = getConfigurationManager();
        configurationManager.saveDefault("blocks.yml");
        configurationManager.saveDefault("chat.yml");
        configurationManager.saveDefault("commands.yml");
        configurationManager.saveDefault("config.yml");
        configurationManager.saveDefault("entities.yml");
        configurationManager.saveDefault("flight.yml");
        configurationManager.saveDefault("game-mode.yml");
        configurationManager.saveDefault("inventories.yml");
        configurationManager.saveDefault("items.yml");
        configurationManager.saveDefault("potions.yml");
        configurationManager.saveDefault("teleportation.yml");
    }

    @Override
    public void onEnable() {
        new ListenerBlocks(this).register();
        new ListenerChat(this).register();
        new ListenerCommands(this).register();
        new ListenerDrop(this).register();
        new ListenerEntities(this).register();
        new ListenerFlight(this).register();
        new ListenerGameMode(this).register();
        new ListenerInventories(this).register();
        new ListenerPotions(this).register();
        new ListenerTeleport(this).register();

        // 1.9: Elytra
        int minorVersion = VersionUtility.getMinorVersion();
        if(minorVersion >= 9) new ListenerElytra(this).register();

        // 1.11: Totem of Undying
        if(minorVersion >= 11) new ListenerTotem(this).register();

        // 1.12: PlayerPickupItemEvent --> EntityPickupItemEvent
        CheatPreventionListener listenerItemPickup = (minorVersion < 12 ? new ListenerLegacyItemPickup(this) : new ListenerModernItemPickup(this));
        listenerItemPickup.register();

        // 1.13: Riptide Enchantment
        if(minorVersion >= 13) new ListenerRiptide(this).register();
    }

    @Override
    public void onDisable() {
        // Do Nothing
    }

    @Override
    public void reloadConfig() {
        ConfigurationManager configurationManager = getConfigurationManager();
        configurationManager.reload("blocks.yml");
        configurationManager.reload("chat.yml");
        configurationManager.reload("commands.yml");
        configurationManager.reload("config.yml");
        configurationManager.reload("entities.yml");
        configurationManager.reload("items.yml");
        configurationManager.reload("inventories.yml");
        configurationManager.reload("flight.yml");
        configurationManager.reload("game-mode.yml");
        configurationManager.reload("potions.yml");
        configurationManager.reload("teleportation.yml");
    }
}