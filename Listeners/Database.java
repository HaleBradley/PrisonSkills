package com.halebradley.prisonskills.Listeners;

import com.halebradley.prisonskills.PrisonSkills;
import com.halebradley.prisonskills.Utils.Skill;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

public class Database implements Listener {

    private Skill miningSkillPlugin;
    private Skill combatSkillPlugin;
    private Skill fishingSkillPlugin;
    private Skill guardSkillPlugin;

    public Database(Skill miningSkillPlugin, Skill combatSkillPlugin, Skill fishingSkillPlugin, Skill guardSkillPlugin){
        this.miningSkillPlugin = miningSkillPlugin;
        this.combatSkillPlugin = combatSkillPlugin;
        this.fishingSkillPlugin = fishingSkillPlugin;
        this.guardSkillPlugin = guardSkillPlugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        updatePlayerData(p);
    }

    @EventHandler
    public void playerLeaveEvent(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        miningSkillPlugin.exportPlayerData(p);
        combatSkillPlugin.exportPlayerData(p);
        fishingSkillPlugin.exportPlayerData(p);
        guardSkillPlugin.exportPlayerData(p);
    }

    @EventHandler
    public void onPluginEnable(PluginEnableEvent e){
        Player[] players = Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]);
        for(Player p : players){
            updatePlayerData(p);
        }
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent e){
        miningSkillPlugin.exportAllData();
        combatSkillPlugin.exportAllData();
        fishingSkillPlugin.exportAllData();
        guardSkillPlugin.exportAllData();
    }

    public void updatePlayerData(Player p){
        Document filter = new Document("uuid", p.getUniqueId().toString());

        if(PrisonSkills.getCollection().countDocuments(filter) == 1){
            Document data = PrisonSkills.getCollection().find(filter).first();
            Document mining = (Document) data.get("mining");
            Document combat = (Document) data.get("combat");
            Document fishing = (Document) data.get("fishing");
            Document guard = (Document) data.get("guard");
            miningSkillPlugin.importData(p, mining.getInteger("level"), mining.getInteger("xp"));
            combatSkillPlugin.importData(p, combat.getInteger("level"), combat.getInteger("xp"));
            fishingSkillPlugin.importData(p, fishing.getInteger("level"), fishing.getInteger("xp"));
            guardSkillPlugin.importData(p, guard.getInteger("level"), guard.getInteger("xp"));
        } else{
            Document newPlayer = new Document("uuid", p.getUniqueId().toString())
                    .append("name", p.getName())
                    .append("mining", new Document("level", 1).append("xp", 0))
                    .append("combat", new Document("level", 1).append("xp", 0))
                    .append("fishing", new Document("level", 1).append("xp", 0))
                    .append("guard", new Document("level", 1).append("xp", 0));

            PrisonSkills.getCollection().insertOne(newPlayer);
            miningSkillPlugin.importData(p, 1, 0);
            combatSkillPlugin.importData(p, 1, 0);
            fishingSkillPlugin.importData(p, 1, 0);
            guardSkillPlugin.importData(p, 1, 0);
        }
    }

}
