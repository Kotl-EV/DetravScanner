package com.detrav.proxies;

import com.detrav.events.DetravLoginEventHandler;
import com.detrav.gui.DetravScannerGUI;
import com.detrav.items.behaviours.BehaviourDetravToolElectricProspector;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.common.items.GT_MetaGenerated_Item_01;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by wital_000 on 19.03.2016.
 */
public class CommonProxy implements IGuiHandler {

    ItemStack scannerLuV, scannerZPM, scannerUV;
    public void onLoad() {
        Item item = GameRegistry.findItem("gregtech", "gt.metaitem.01");
        GT_MetaGenerated_Item_01 metaGenItem = (GT_MetaGenerated_Item_01) item;

        scannerLuV = metaGenItem.addItem(375, "LuV Prospector's Scanner", "Use to prospect ores", new BehaviourDetravToolElectricProspector(50_000, 5));
        metaGenItem.setElectricStats(32375, 10_000_000, GT_Values.V[6], 6, -1, false);

        scannerZPM = metaGenItem.addItem(376, "ZPM Prospector's Scanner", "Use to prospect ores", new BehaviourDetravToolElectricProspector(100_000, 7));
        metaGenItem.setElectricStats(32376, 10_000_000, GT_Values.V[7], 7, -1, false);

        scannerUV = metaGenItem.addItem(377, "UV Prospector's Scanner", "Use to prospect ores", new BehaviourDetravToolElectricProspector(200_000, 9));
        metaGenItem.setElectricStats(32377, 10_000_000, GT_Values.V[8], 8, -1, false);
    }

    public void onPostLoad() {
        Item item = GameRegistry.findItem("gregtech", "gt.metaitem.01");
        GT_MetaGenerated_Item_01 metaGenItem = (GT_MetaGenerated_Item_01) item;
        DetravLoginEventHandler.register();
        GT_ModHandler.addCraftingRecipe(scannerLuV.copy(), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"EHR", "CSC", "PBP", 'S', ItemList.Cover_Screen, 'R', ItemList.Sensor_LuV, 'H', ItemList.Field_Generator_HV, 'E', ItemList.Emitter_LuV, 'C', OrePrefixes.circuit.get(Materials.Master), 'P', OrePrefixes.plate.get(Materials.Iridium), 'B', ItemList.Energy_LapotronicOrb.get(1L)});
        GT_ModHandler.addCraftingRecipe(scannerZPM.copy(), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"EHR", "CSC", "PBP", 'S', ItemList.Cover_Screen, 'R', ItemList.Sensor_ZPM, 'H', ItemList.Field_Generator_EV, 'E', ItemList.Emitter_ZPM, 'C', OrePrefixes.circuit.get(Materials.Ultimate), 'P', OrePrefixes.plate.get(Materials.Osmium), 'B', ItemList.Energy_LapotronicOrb2.get(1L)});
        GT_ModHandler.addCraftingRecipe(scannerUV.copy(), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"EHR", "CSC", "PBP", 'S', ItemList.Cover_Screen, 'R', ItemList.Sensor_UV, 'H', ItemList.Field_Generator_IV, 'E', ItemList.Emitter_UV, 'C', OrePrefixes.circuit.get(Materials.Superconductor), 'P', OrePrefixes.plate.get(Materials.Neutronium), 'B', ItemList.Energy_Cluster.get(1L)});

    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case DetravScannerGUI.GUI_ID:
                return null;
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case DetravScannerGUI.GUI_ID:
                return new DetravScannerGUI();
            default:
                return null;
        }
    }

    public void openProspectorGUI() {
        //just Client code
    }

    public void onPreInit() {
    }

    public void sendPlayerExeption(String s) {
    }
}
