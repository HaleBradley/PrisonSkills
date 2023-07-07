package com.halebradley.prisonskills.Listeners;

import com.halebradley.prisonskills.Utils.Skill;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class SkillsGUI implements Listener {
    private final Inventory inv;
    private Skill miningSkillPlugin;
    private Skill combatSkillPlugin;
    private Skill fishingSkillPlugin;
    private Skill guardSkillPlugin;

    public SkillsGUI(Skill miningSkillPlugin, Skill combatSkillPlugin, Skill fishingSkillPlugin, Skill guardSkillPlugin){
        this.miningSkillPlugin = miningSkillPlugin;
        this.combatSkillPlugin = combatSkillPlugin;
        this.fishingSkillPlugin = fishingSkillPlugin;
        this.guardSkillPlugin = guardSkillPlugin;
        inv = Bukkit.createInventory(null, 54, "Skills");
    }
    //Initializes each skills item and ability item
    private void initializeItems(Player p){
        addBorder(Material.BLACK_STAINED_GLASS_PANE, " ");
        inv.setItem(10, createGUIItem(Material.DIAMOND_PICKAXE, "Mining Skill", "Level: " + miningSkillPlugin.getPlayerLevel(p), miningSkillPlugin.getPlayerXP(p) + "/" + miningSkillPlugin.getLevelXP(miningSkillPlugin.getPlayerLevel(p)) + " to level " + (miningSkillPlugin.getPlayerLevel(p) + 1) ));
        inv.setItem(16, createGUIItem(Material.CHEST, "Mining Abilities"));
        inv.setItem(19, createGUIItem(Material.DIAMOND_SWORD, "Combat Skill", "Level: " + combatSkillPlugin.getPlayerLevel(p), combatSkillPlugin.getPlayerXP(p) + "/" + combatSkillPlugin.getLevelXP(combatSkillPlugin.getPlayerLevel(p)) + " to level " + (combatSkillPlugin.getPlayerLevel(p) + 1) ));
        inv.setItem(25, createGUIItem(Material.CHEST, "Combat Abilities"));
        inv.setItem(28, createGUIItem(Material.FISHING_ROD, "Fishing Skill", "Level: " + fishingSkillPlugin.getPlayerLevel(p), fishingSkillPlugin.getPlayerXP(p) + "/" + fishingSkillPlugin.getLevelXP(fishingSkillPlugin.getPlayerLevel(p)) + " to level " + (fishingSkillPlugin.getPlayerLevel(p) + 1) ));
        inv.setItem(34, createGUIItem(Material.CHEST, "Fishing Abilities"));
        inv.setItem(37, createGUIItem(Material.SHIELD, "Guard Skill", "Level: " + guardSkillPlugin.getPlayerLevel(p), guardSkillPlugin.getPlayerXP(p) + "/" + guardSkillPlugin.getLevelXP(guardSkillPlugin.getPlayerLevel(p)) + " to level " + (guardSkillPlugin.getPlayerLevel(p) + 1) ));
        inv.setItem(43, createGUIItem(Material.CHEST, "Guard Abilities"));
    }

    private ItemStack createGUIItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        return item;
    }

    private void addBorder(Material material, String name){
        ItemStack borderItem = createGUIItem(material, name);
        for(int i = 0; i < 9; i++){
            inv.setItem(i, borderItem);
        }
        inv.setItem(9, borderItem);
        inv.setItem(17, borderItem);
        inv.setItem(18, borderItem);
        inv.setItem(26, borderItem);
        inv.setItem(27, borderItem);
        inv.setItem(35, borderItem);
        inv.setItem(36, borderItem);
        inv.setItem(44, borderItem);
        for(int i = 45; i < 54; i++){
            inv.setItem(i, borderItem);
        }
    }
    //Gets the progress of the skill
    private void addSkillProgress(Player p, Skill skill, int bound1, int bound2){
        int currentXP = skill.getPlayerXP(p);
        int currentLevel = skill.getPlayerLevel(p);
        double percentageToLevelUp = ((double) currentXP / skill.getLevelXP(currentLevel)) * 100.0;

        addSkillProgressBar(percentageToLevelUp, bound1, bound2);
    }
    //Creates a skill bar for progress in the skill
    private void addSkillProgressBar(double percentageToLevelUp, int bound1, int bound2){
        ItemStack completedProgressItem = createGUIItem(Material.GREEN_STAINED_GLASS_PANE, percentageToLevelUp + "%");
        ItemStack inProgressItem = createGUIItem(Material.RED_STAINED_GLASS_PANE, percentageToLevelUp + "%");
        bound2 = bound2 + 1;

        if(percentageToLevelUp > 0 && percentageToLevelUp < 25){                //0 to 25 percent bar
            inv.setItem(bound1, completedProgressItem);
            for(int i = (bound1 + 1); i < bound2; i++){
                inv.setItem(i, inProgressItem);
            }
        } else if(percentageToLevelUp >= 25 && percentageToLevelUp < 50) {      //25 to 50 percent bar
            for(int i = bound1; i < (bound1 + 2); i++) {
                inv.setItem(i, completedProgressItem);
            }
            for(int i = (bound1 + 2); i < bound2; i++) {
                inv.setItem(i, inProgressItem);
            }
        } else if(percentageToLevelUp >= 50 && percentageToLevelUp < 75) {      //50 to 75 percent bar
            for(int i = bound1; i < (bound1 + 3); i++) {
                inv.setItem(i, completedProgressItem);
            }
            for(int i = (bound1 + 3); i < bound2; i++) {
                inv.setItem(i, inProgressItem);
            }
        } else if(percentageToLevelUp >= 75 && percentageToLevelUp < 100) {     //75 to 100 percent bar
            for(int i = bound1; i < (bound1 + 4); i++) {
                inv.setItem(i, completedProgressItem);
            }
            inv.setItem(bound2, inProgressItem);
        } else {
            for(int i = bound1; i < bound2; i++) {
                inv.setItem(i, inProgressItem);
            }
        }
    }

    public void openInventory(Player p){
        initializeItems(p);
        addSkillProgress(p, miningSkillPlugin, 11, 15);
        addSkillProgress(p, combatSkillPlugin, 20, 24);
        addSkillProgress(p, fishingSkillPlugin, 29, 33);
        addSkillProgress(p, guardSkillPlugin, 38, 42);
        p.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(!e.getInventory().equals(inv)) return;
        e.setCancelled(true);

        if(e.getRawSlot() == 16){
            //TODO Open mining abilites GUI
        } else if(e.getRawSlot() == 25){
            //TODO Open combat abilities GUI
        } else if(e.getRawSlot() == 34){
            //TODO Open fishing abilities GUI
        } else if(e.getRawSlot() == 43){
            //TODO Open guard abilities GUI
        }
    }
}
