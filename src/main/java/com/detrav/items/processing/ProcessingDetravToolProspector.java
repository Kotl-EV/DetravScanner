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

        GT_ModHandler.addCraftingRecipe(DetravMetaGeneratedTool01.INSTANCE.getToolWithStats(100, 1, aMaterial, Materials.BT6, new long[]{25000000L, GT_Values.V[4], 4L, -1L}), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"EHR", "CSC", "PBP", 'S', ItemList.Cover_Screen, 'R', ItemList.Sensor_EV, 'H', OrePrefixes.toolHeadDrill.get(aMaterial), 'E', ItemList.Emitter_EV, 'C', OrePrefixes.circuit.get(Materials.Data), 'P', OrePrefixes.plate.get(Materials.BT6), 'B', ItemList.BatteryHull_EV_Full.get(1L)});
        GT_ModHandler.addCraftingRecipe(DetravMetaGeneratedTool01.INSTANCE.getToolWithStats(102, 1, aMaterial, Materials.HastelloyC276, new long[]{250000000L, GT_Values.V[5], 5L, -1L}), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"EHR", "CSC", "PBP", 'S', ItemList.Cover_Screen, 'R', ItemList.Sensor_IV, 'H', OrePrefixes.toolHeadDrill.get(aMaterial), 'E', ItemList.Emitter_IV, 'C', OrePrefixes.circuit.get(Materials.Elite), 'P', OrePrefixes.plate.get(Materials.HastelloyC276), 'B', ItemList.BatteryHull_IV_Full.get(1L)});
        GT_ModHandler.addCraftingRecipe(DetravMetaGeneratedTool01.INSTANCE.getToolWithStats(104, 1, aMaterial, Materials.HastelloyN, new long[]{1000000000L, GT_Values.V[6], 6L, -1L}), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"EHR", "CSC", "PBP", 'S', ItemList.Cover_Screen, 'R', ItemList.Sensor_LuV, 'H', OrePrefixes.toolHeadDrill.get(aMaterial), 'E', ItemList.Emitter_LuV, 'C', OrePrefixes.circuit.get(Materials.Master), 'P', OrePrefixes.plate.get(Materials.HastelloyN), 'B', ItemList.BatteryHull_LuV_Full.get(1L)});
        GT_ModHandler.addCraftingRecipe(DetravMetaGeneratedTool01.INSTANCE.getToolWithStats(106, 1, aMaterial, Materials.Lafium, new long[]{10000000000L, GT_Values.V[7], 7L, -1L}), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"EHR", "CSC", "PBP", 'S', ItemList.Cover_Screen, 'R', ItemList.Sensor_ZPM, 'H', OrePrefixes.toolHeadDrill.get(aMaterial), 'E', ItemList.Emitter_ZPM, 'C', OrePrefixes.circuit.get(Materials.Ultimate), 'P', OrePrefixes.plate.get(Materials.Lafium), 'B', ItemList.BatteryHull_ZPM_Full.get(1L)});
    }
}
