package com.halebradley.prisonskills.Commands;

import com.halebradley.prisonskills.Listeners.SkillsGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenSkillsGUI implements CommandExecutor {

    private SkillsGUI skillsGUIPlugin;

    public OpenSkillsGUI(SkillsGUI skillsGUIPlugin){
        this.skillsGUIPlugin = skillsGUIPlugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player p){
            skillsGUIPlugin.openInventory(p);
        }

        return true;
    }
}
