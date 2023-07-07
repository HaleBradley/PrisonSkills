package com.halebradley.prisonskills.Utils;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Skill {
    //Saves database data for online players
    private HashMap<Player, Integer> playerLevel;
    private HashMap<Player, Integer> playerXP;

    private int[] levelsXP = {};    //XP for each level
    private String name = "";       //Skill name

    //Creats a skill object
    public Skill(int[] levelsXP, String name){
        playerLevel = new HashMap<>();
        playerXP = new HashMap<>();
        this.levelsXP = levelsXP;
        this.name = name;
    }
    //Imports data from database to HashMaps
    public void importData(Player p, int level, int XP){
        playerLevel.put(p, level);
        playerXP.put(p, XP);
    }
    //Exports data from HashMaps to database
    public void exportData(MongoCollection<Document> collection){
        //TODO export all HashMap data to database
    }

    public String getName(){
        return name;
    }

    public int getPlayerLevel(Player p){
        return playerLevel.get(p);
    }

    public int getPlayerXP(Player p){
        return playerXP.get(p);
    }

    public int getLevelXP(int level){
        return levelsXP[level - 1];
    }
    //Add xp to player's xp and check for level up
    public int addXP(int XP, Player p){
        int currentXP = XP + playerXP.get(p);
        int currentLevel = playerLevel.get(p);

        if(currentXP >= levelsXP[playerLevel.get(p) - 1]) {
            update(playerLevelUp(p), 0, p);
            return getPlayerLevel(p);
        } else{
            update(currentLevel, currentXP, p);
            return -1;
        }
    }
    //Levels up player
    private int playerLevelUp(Player p){
        int newLevel = playerLevel.get(p) + 1;
        playerLevel.replace(p, newLevel);
        //TODO Make player xp overflow on level up
        playerXP.replace(p, 0);
        return newLevel;
    }
    //Update data into HashMaps
    public void update(int level, int XP, Player p){
        playerXP.put(p, XP);
        playerLevel.put(p, level);
    }

}
