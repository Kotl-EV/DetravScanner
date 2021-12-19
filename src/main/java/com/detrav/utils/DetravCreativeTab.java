package com.detrav.utils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.List;

public class DetravCreativeTab extends CreativeTabs {

    public DetravCreativeTab() {
        super("Detrav Scanner");
    }

    @Override
    public Item getTabIconItem() {
        return Items.stick;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllReleventItems(List p_78018_1_) {
        for (Object o : Item.itemRegistry) {
            Item item = (Item) o;

            if (item == null) {
                continue;
            }

            for (CreativeTabs tab : item.getCreativeTabs()) {
                if (tab == this) {
                    item.getSubItems(item, this, p_78018_1_);
                }
            }
        }

        if (this.func_111225_m() != null) {
            this.addEnchantmentBooksToList(p_78018_1_, this.func_111225_m());
        }
    }
}