package combatlogx.expansion.loot.protection;

import com.github.sirblobman.api.configuration.ConfigurationManager;
import com.github.sirblobman.combatlogx.api.ICombatLogX;
import com.github.sirblobman.combatlogx.api.expansion.Expansion;
import combatlogx.expansion.loot.protection.listener.ListenerLootProtection;
import org.bukkit.configuration.file.YamlConfiguration;

public final class LootProtectionExpansion extends Expansion {
    public LootProtectionExpansion(ICombatLogX plugin) {
        super(plugin);
    }

    public long lootProtectionTime;
    public int messageCooldown;
    public boolean onlyProtectAfterLog;
    public boolean returnVoidItems;
    public boolean protect;

    @Override
    public void onLoad() {
        ConfigurationManager configurationManager = getConfigurationManager();
        configurationManager.saveDefault("config.yml");
        YamlConfiguration config =  configurationManager.get("config.yml");
        this.lootProtectionTime = config.getLong("loot-protection-time", 30);
        this.messageCooldown = config.getInt("message-cooldown", 1);
        this.onlyProtectAfterLog = config.getBoolean("only-protect-after-log", false);
        this.returnVoidItems = config.getBoolean("return-void-items", true);
        this.protect = config.getBoolean("protect", true);
    }

    @Override
    public void onEnable() {
        new ListenerLootProtection(this).register();
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void reloadConfig() {
        ConfigurationManager configurationManager = getConfigurationManager();
        configurationManager.reload("config.yml");
        YamlConfiguration config =  configurationManager.get("config.yml");
        this.lootProtectionTime = config.getLong("loot-protection-time", 30);
        this.messageCooldown = config.getInt("message-cooldown", 1);
        this.onlyProtectAfterLog = config.getBoolean("only-protect-after-log", false);
        this.returnVoidItems = config.getBoolean("return-void-items", true);
        this.protect = config.getBoolean("protect", true);
    }
}