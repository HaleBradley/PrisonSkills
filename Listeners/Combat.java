package com.halebradley.prisonskills.Listeners;

import com.halebradley.prisonskills.Utils.Alerts;
import com.halebradley.prisonskills.Utils.Skill;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class Combat implements Listener {

    private Skill combatSkillPlugin;

    public Combat(Skill combatSkillPlugin) {
        this.combatSkillPlugin = combatSkillPlugin;
    }
    //Gives xp for different kills
    //TODO make configurable
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        Player p = e.getEntity().getKiller();
        switch(e.getEntity().getType()){
            case ZOMBIE -> increaseXP(10, p);
            case SKELETON -> increaseXP(50, p);
        }
    }

    public void increaseXP(int XP, Player p){
        if (combatSkillPlugin.addXP(XP, p) != -1) {
            Alerts.playerLevelUp(p, combatSkillPlugin);
        } else {
            Alerts.playerXPGained(p, combatSkillPlugin);
        }
    }
}
