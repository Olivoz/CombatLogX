package combatlogx.expansion.newbie.helper.hooks;

import com.github.sirblobman.combatlogx.api.expansion.Expansion;
import com.github.sirblobman.combatlogx.api.expansion.ExpansionManager;
import combatlogx.expansion.loot.protection.LootProtectionExpansion;
import combatlogx.expansion.loot.protection.event.QueryPickupEvent;
import combatlogx.expansion.newbie.helper.NewbieHelperExpansion;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.Optional;

public class HookLootProtection implements Listener {

    private final NewbieHelperExpansion newbieHelper;

    public HookLootProtection(NewbieHelperExpansion expansion) {
        this.newbieHelper = expansion;
    }

    @EventHandler
    public void onQueryPickup(QueryPickupEvent event) {
        if(newbieHelper.getProtectionManager().isProtected(event.getPlayer())) event.setCancelled(true);
    }

    public void register() {
        ExpansionManager manager = newbieHelper.getPlugin().getExpansionManager();
        Optional<Expansion> optionalLootProtection = manager.getExpansion("LootProtection");
        if(optionalLootProtection.isPresent()) {
            LootProtectionExpansion lootProtection = (LootProtectionExpansion) optionalLootProtection.get();
            if(lootProtection.protect) return;
            Bukkit.getPluginManager().registerEvents(this, newbieHelper.getPlugin().getPlugin());
        }
    }

    public void unregister() {
        HandlerList.unregisterAll(this);
    }

}
