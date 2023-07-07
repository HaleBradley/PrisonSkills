package com.halebradley.prisonskills.Utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class Alerts {
    //Custom title for player level up
    public void playerLevelUp(Player p, Skill skill){
        int currentXP = skill.getPlayerXP(p);
        int currentLevel = skill.getPlayerLevel(p);
        p.sendTitle("Your " + skill.getName() + " Skill Leveled Up To " + currentLevel, skill.getLevelXP(currentLevel) + " XP To Level " + (skill.getPlayerLevel(p) + 1), 1, 20, 1);
    }
    //Custom action bar for when xp is gained
    public void playerXPGained(Player p, Skill skill){
        int currentXP = skill.getPlayerXP(p);
        int currentLevel = skill.getPlayerLevel(p);
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(currentXP + "/" + skill.getLevelXP(currentLevel) + " " + skill.getName() + " XP"));
    }

}
