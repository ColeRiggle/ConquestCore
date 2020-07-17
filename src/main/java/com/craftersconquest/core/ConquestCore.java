package com.craftersconquest.core;

import com.craftersconquest.blocklist.Blocklist;
import com.craftersconquest.core.input.InputManager;
import com.craftersconquest.database.Configuration;
import com.craftersconquest.database.ConquestDataSource;
import com.craftersconquest.database.ConquestSQLSource;
import com.craftersconquest.guilds.GuildManager;
import com.craftersconquest.horses.HorseManager;
import com.craftersconquest.items.ItemLoader;
import com.craftersconquest.listeners.ListenerManager;
import com.craftersconquest.menu.MenuManager;
import com.craftersconquest.object.Component;
import com.craftersconquest.player.cache.ConquestPlayerCacheManager;
import com.craftersconquest.skills.SkillsManager;
import com.craftersconquest.time.TimeManager;
import com.craftersconquest.visual.ScoreboardManager;
import com.craftersconquest.visual.VisualsManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ConquestCore extends JavaPlugin {

    private final ConquestDataSource dataSource = new ConquestSQLSource(this);
    private final List<Component> components = new ArrayList<>();

    private final Configuration configuration = new Configuration(this);
    private final ConquestPlayerCacheManager cacheManager = new ConquestPlayerCacheManager(this);
    private final ListenerManager listenerManager = new ListenerManager(this);
    private final SkillsManager skillsManager = new SkillsManager(this);
    private final Blocklist blocklist = new Blocklist(this);
    private final ScoreboardManager scoreboardManager = new ScoreboardManager(this);
    private final VisualsManager visualsManager = new VisualsManager(this);
    private final ItemLoader itemLoader = new ItemLoader(this);
    private final MenuManager menuManager = new MenuManager(this);
    private final TimeManager timeManager = new TimeManager(this);
    private final GuildManager guildManager = new GuildManager(this);
    private final HorseManager horseManager = new HorseManager(this);
    private final InputManager inputManager = new InputManager(this);

    private Economy economy;

    @Override
    public void onEnable() {
        dataSource.open();
        setupEconomy();
        registerComponents();
        enableComponents();

        Bukkit.getLogger().info(timeManager.getDate().toString());

        test();
    }

    private void test() {

    }

    private void setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        economy = rsp.getProvider();
    }

    private void registerComponents() {
        components.add(configuration);
        components.add(timeManager);
        components.add(cacheManager);
        components.add(listenerManager);
        components.add(scoreboardManager);
        components.add(itemLoader);
        components.add(guildManager);
        components.add(horseManager);
        components.add(inputManager);
    }

    private void enableComponents() {
        for (Component component : components) {
            component.onEnable();
        }
    }

    @Override
    public void onDisable() {
        disableComponents();
        dataSource.close();
    }

    private void disableComponents() {
        for (Component component : components) {
            component.onDisable();
        }
    }

    public ConquestDataSource getDataSource() {
        return dataSource;
    }

    public Configuration getConfiguration() { return configuration; }

    public ConquestPlayerCacheManager getCacheManager() {
        return cacheManager;
    }

    public SkillsManager getSkillsManager() { return skillsManager; }

    public Blocklist getBlocklist() { return blocklist; }

    public ScoreboardManager getScoreboardManager() { return scoreboardManager; }

    public VisualsManager getVisualsManager() { return visualsManager; }

    public TimeManager getTimeManager() { return timeManager; }

    public ItemLoader getItemLoader() { return itemLoader; }

    public MenuManager getMenuManager() { return menuManager; }

    public GuildManager getGuildManager() { return guildManager; }

    public HorseManager getHorseManager() { return horseManager; }

    public InputManager getInputManager() { return inputManager; }

    public Economy getEconomy() { return economy; }

}
