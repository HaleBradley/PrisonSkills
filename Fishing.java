package com.halebradley.prisonskills.Listeners;

import com.halebradley.prisonskills.Utils.Alerts;
import com.halebradley.prisonskills.Utils.Skill;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;

public class Fishing implements Listener {
    private Skill fishingSkillPlugin;
    private Alerts alertsPlugin;

    public Fishing(Skill fishingSkillPlugin, Alerts alertsPlugin) {
        this.fishingSkillPlugin = fishingSkillPlugin;
        this.alertsPlugin = alertsPlugin;
    }
    //Gives xp for different catches
    //TODO make configurable
    @EventHandler
    public void onFishEvent(PlayerFishEvent e){
        var entity = e.getCaught();
        if(entity instanceof Item){
            Player p = e.getPlayer();
            var item = (Item) entity;
            var itemstack = item.getItemStack();
            switch(itemstack.getType()){
                case COD -> increaseXP(10, p);
                case SALMON -> increaseXP(50, p);
            }
        }
    }

    public void increaseXP(int XP, Player p){
        if (fishingSkillPlugin.addXP(XP, p) != -1) {
            alertsPlugin.playerLevelUp(p, fishingSkillPlugin);
        } else {
            alertsPlugin.playerXPGained(p, fishingSkillPlugin);
        }
    }
}
