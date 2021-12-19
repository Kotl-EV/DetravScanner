package com.detrav.items.processing;

import com.detrav.items.DetravMetaGeneratedTool01;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.item.ItemStack;

import static com.detrav.DetravScannerMod.DEBUGBUILD;

/**
 * Created by wital_000 on 18.03.2016.
 */
public class ProcessingDetravToolProspector implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingDetravToolProspector() {
        OrePrefixes.toolHeadPickaxe.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (!aPrefix.doGenerateItem(aMaterial)) return;
        if (DEBUGBUILD) return;
    }
}
