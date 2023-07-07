package com.halebradley.prisonskills;

import com.halebradley.prisonskills.Commands.MiningCommand;
import com.halebradley.prisonskills.Commands.OpenSkillsGUI;
import com.halebradley.prisonskills.Listeners.*;
import com.halebradley.prisonskills.Utils.Alerts;
import com.halebradley.prisonskills.Utils.Skill;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class PrisonSkills extends JavaPlugin {

    @Override
    public void onEnable() {
        //Utils
        Skill miningSkillPlugin = new Skill(new int[]{50, 100, 200, 400, 800, 1600, 3200, 6400, 12800}, "Mining");
        Skill combatSkillPlugin = new Skill(new int[]{50, 100, 200, 400, 800, 1600, 3200, 6400, 12800}, "Combat");
        Skill fishingSkillPlugin = new Skill(new int[]{50, 100, 200, 400, 800, 1600, 3200, 6400, 12800}, "Fishing");
        Skill guardSkillPlugin = new Skill(new int[]{50, 100, 200, 400, 800, 1600, 3200, 6400, 12800}, "Guard");
        Alerts alertsPlugin = new Alerts();
        SkillsGUI skillsGUIPlugin = new SkillsGUI(miningSkillPlugin, combatSkillPlugin, fishingSkillPlugin, guardSkillPlugin);
        //Commands
        getCommand("mining").setExecutor(new MiningCommand(miningSkillPlugin));
        getCommand("skills").setExecutor(new OpenSkillsGUI(skillsGUIPlugin));
        //Listeners
        getServer().getPluginManager().registerEvents(new Mining(miningSkillPlugin, alertsPlugin), this);
        getServer().getPluginManager().registerEvents(new Combat(combatSkillPlugin, alertsPlugin), this);
        getServer().getPluginManager().registerEvents(new Fishing(fishingSkillPlugin, alertsPlugin), this);
        getServer().getPluginManager().registerEvents(skillsGUIPlugin, this);
        getServer().getPluginManager().registerEvents(new Database(miningSkillPlugin, combatSkillPlugin, fishingSkillPlugin, guardSkillPlugin), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }
}
