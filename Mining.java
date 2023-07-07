package com.halebradley.prisonskills.Listeners;

import com.halebradley.prisonskills.Utils.Alerts;
import com.halebradley.prisonskills.Utils.Skill;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Mining implements Listener {

    private Skill miningSkillPlugin;
    private Alerts alertsPlugin;

    public Mining(Skill miningSkillPlugin, Alerts alertsPlugin) {
        this.miningSkillPlugin = miningSkillPlugin;
        this.alertsPlugin = alertsPlugin;
    }
    //Gives xp for different blocks
    //TODO make configurable
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        switch (e.getBlock().getBlockData().getMaterial()) {
            case STONE -> increaseXP(10, p);
            case DIAMOND_ORE -> increaseXP(50, p);
        }
    }

    public void increaseXP(int XP, Player p){
        if (miningSkillPlugin.addXP(XP, p) != -1) {
            alertsPlugin.playerLevelUp(p, miningSkillPlugin);
        } else {
            alertsPlugin.playerXPGained(p, miningSkillPlugin);
        }
    }
}