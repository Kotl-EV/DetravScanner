package com.detrav.items;

import java.util.List;

import gregtech.api.items.GT_MetaBase_Item;
import gregtech.common.items.behaviors.Behaviour_None;
import net.minecraft.item.ItemStack;


/**
 * Created by wital_000 on 19.03.2016.
 */
public class DetravMetaGeneratedTool01 extends Behaviour_None {
    public static DetravMetaGeneratedTool01 INSTANCE;

    public DetravMetaGeneratedTool01() {
        INSTANCE = this;
    }


    @Override
    public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
        return aList;
    }
}
