package combatlogx.expansion.newbie.helper;

import com.github.sirblobman.api.configuration.ConfigurationManager;
import com.github.sirblobman.combatlogx.api.ICombatLogX;
import com.github.sirblobman.combatlogx.api.expansion.Expansion;
import combatlogx.expansion.newbie.helper.command.CommandTogglePVP;
import combatlogx.expansion.newbie.helper.hooks.HookLootProtection;
import combatlogx.expansion.newbie.helper.listener.ListenerDamage;
import combatlogx.expansion.newbie.helper.listener.ListenerJoin;
import combatlogx.expansion.newbie.helper.manager.PVPManager;
import combatlogx.expansion.newbie.helper.manager.ProtectionManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.logging.Handler;

public final class NewbieHelperExpansion extends Expansion {
    private final PVPManager pvpManager;
    private final ProtectionManager protectionManager;
    private HookLootProtection hookLootProtection = null;

    public NewbieHelperExpansion(ICombatLogX plugin) {
        super(plugin);
        this.pvpManager = new PVPManager();
        this.protectionManager = new ProtectionManager(this);
    }

    @Override
    public void onLoad() {
        ConfigurationManager configurationManager = getConfigurationManager();
        configurationManager.saveDefault("config.yml");
    }

    @Override
    public void onEnable() {
        new ListenerJoin(this).register();
        new ListenerDamage(this).register();
        new CommandTogglePVP(this).register();
        YamlConfiguration config = getConfigurationManager().get("config.yml");
        if(!config.getBoolean("hooks.loot-protection", false)) return;
        this.hookLootProtection = new HookLootProtection(this);
        this.hookLootProtection.register();
    }

    @Override
    public void onDisable() {
        // Do Nothing
    }

    @Override
    public void reloadConfig() {
        ConfigurationManager configurationManager = getConfigurationManager();
        configurationManager.reload("config.yml");
        YamlConfiguration config = getConfigurationManager().get("config.yml");
        boolean hookLootProtection = config.getBoolean("hooks.loot-protection", false);
        if(!hookLootProtection && this.hookLootProtection != null) {
            this.hookLootProtection.unregister();
            this.hookLootProtection = null;
            return;
        }

        if(hookLootProtection && this.hookLootProtection == null) {
            this.hookLootProtection = new HookLootProtection(this);
            this.hookLootProtection.register();
        }

    }

    public PVPManager getPVPManager() {
        return this.pvpManager;
    }

    public ProtectionManager getProtectionManager() {
        return this.protectionManager;
    }
}