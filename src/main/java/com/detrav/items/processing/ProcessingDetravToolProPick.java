package com.detrav.items.processing;

import com.detrav.items.DetravMetaGeneratedTool01;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import static com.detrav.DetravScannerMod.DEBUGBUILD;

/**
 * Created by wital_000 on 18.03.2016.
 */
public class ProcessingDetravToolProPick implements gregtech.api.interfaces.IOreRecipeRegistrator {			
    public ProcessingDetravToolProPick() {
        OrePrefixes.toolHeadPickaxe.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if(!aPrefix.doGenerateItem(aMaterial)) 
        	return;
        if (DEBUGBUILD)
            return;

        GT_ModHandler.addCraftingRecipe(DetravMetaGeneratedTool01.INSTANCE.getToolWithStats(100, 1, aMaterial, Materials.Iridium, new long[]{102400000L, GT_Values.V[6], 6L, -1L}), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"EHR", "CSC", "PBP", Character.valueOf('S'), ItemList.Cover_Screen, Character.valueOf('R'), ItemList.Sensor_LuV, Character.valueOf('H'), OrePrefixes.toolHeadDrill.get(aMaterial),Character.valueOf('E'), ItemList.Emitter_LuV,Character.valueOf('C'), OrePrefixes.circuit.get(Materials.Master), Character.valueOf('P'), OrePrefixes.plate.get(Materials.Iridium), Character.valueOf('B'), ItemList.BatteryHull_LuV_Full.get(1L)});
        GT_ModHandler.addCraftingRecipe(DetravMetaGeneratedTool01.INSTANCE.getToolWithStats(102, 1, aMaterial, Materials.Osmium, new long[]{409600000L, GT_Values.V[7], 7L, -1L}), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"EHR", "CSC", "PBP", Character.valueOf('S'), ItemList.Cover_Screen, Character.valueOf('R'), ItemList.Sensor_ZPM, Character.valueOf('H'), OrePrefixes.toolHeadDrill.get(aMaterial), Character.valueOf('E'), ItemList.Emitter_ZPM,Character.valueOf('C'), OrePrefixes.circuit.get(Materials.Ultimate), Character.valueOf('P'), OrePrefixes.plate.get(Materials.Osmium), Character.valueOf('B'), ItemList.BatteryHull_ZPM_Full.get(1L)});
        GT_ModHandler.addCraftingRecipe(DetravMetaGeneratedTool01.INSTANCE.getToolWithStats(104, 1, aMaterial, Materials.Tritanium, new long[]{1638400000L, GT_Values.V[8], 8L, -1L}), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"EHR", "CSC", "PBP", Character.valueOf('S'), ItemList.Cover_Screen, Character.valueOf('R'), ItemList.Sensor_UV, Character.valueOf('H'), OrePrefixes.toolHeadDrill.get(aMaterial), Character.valueOf('E'), ItemList.Emitter_UV,Character.valueOf('C'), OrePrefixes.circuit.get(Materials.Superconductor), Character.valueOf('P'), OrePrefixes.plate.get(Materials.Neutronium), Character.valueOf('B'), ItemList.BatteryHull_UV_Full.get(1L)});
        GT_ModHandler.addCraftingRecipe(DetravMetaGeneratedTool01.INSTANCE.getToolWithStats(106, 1, aMaterial, Materials.Neutronium, new long[]{6553600000L, GT_Values.V[9], 9L, -1L}), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"EHR", "CSC", "PBP", Character.valueOf('S'), ItemList.Cover_Screen, Character.valueOf('R'), ItemList.Sensor_UHV, Character.valueOf('H'), OrePrefixes.toolHeadDrill.get(aMaterial), Character.valueOf('E'), ItemList.Emitter_UHV,Character.valueOf('C'), OrePrefixes.circuit.get(Materials.Infinite), Character.valueOf('P'), OrePrefixes.plate.get(Materials.Phoenixite), Character.valueOf('B'), ItemList.BatteryHull_UHV_Full.get(1L)});

		
    }
}
