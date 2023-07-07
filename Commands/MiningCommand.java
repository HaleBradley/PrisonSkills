package com.halebradley.prisonskills.Commands;

import com.halebradley.prisonskills.Utils.Skill;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MiningCommand implements CommandExecutor {

    private Skill miningSkillPlugin;

    public MiningCommand(Skill miningSkillPlugin){
        this.miningSkillPlugin = miningSkillPlugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player p){
            p.sendMessage(ChatColor.BOLD + "" + ChatColor.RED + "Mining Level: " + miningSkillPlugin.getPlayerLevel(p));
        }

        return true;
    }
}
