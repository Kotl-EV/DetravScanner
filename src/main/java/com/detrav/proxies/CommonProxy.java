package com.detrav.proxies;

import com.detrav.events.DetravLoginEventHandler;
import com.detrav.gui.DetravScannerGUI;
import com.detrav.items.behaviours.BehaviourDetravToolElectricProspector;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.enums.*;
import gregtech.api.util.GT_ModHandler;
import gregtech.common.items.GT_MetaGenerated_Item_01;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

/**
 * Created by wital_000 on 19.03.2016.
 */
public class CommonProxy implements IGuiHandler {

    ItemStack scannerMV, scannerHV, scannerEV;
    public void onLoad() {
        Item item = GameRegistry.findItem("gregtech", "gt.metaitem.01");
        GT_MetaGenerated_Item_01 metaGenItem = (GT_MetaGenerated_Item_01) item;

        scannerMV = metaGenItem.addItem(375, "MV Prospector's Scanner", "Use to prospect ores", new BehaviourDetravToolElectricProspector(5_000, 5));
        metaGenItem.setElectricStats(32375, 100_000, GT_Values.V[6], 2, -1, false);

        scannerHV = metaGenItem.addItem(376, "HV Prospector's Scanner", "Use to prospect ores", new BehaviourDetravToolElectricProspector(10_000, 7));
        metaGenItem.setElectricStats(32376, 1_000_000, GT_Values.V[7], 3, -1, false);

        scannerEV = metaGenItem.addItem(377, "EV Prospector's Scanner", "Use to prospect ores", new BehaviourDetravToolElectricProspector(20_000, 9));
        metaGenItem.setElectricStats(32377, 10_000_000, GT_Values.V[8], 4, -1, false);
    }

    public void onPostLoad() {
        Item item = GameRegistry.findItem("gregtech", "gt.metaitem.01");
        GT_MetaGenerated_Item_01 metaGenItem = (GT_MetaGenerated_Item_01) item;
        DetravLoginEventHandler.register();
        GT_ModHandler.addCraftingRecipe(scannerMV.copy(), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"EHR", "CSC", "PBP", 'S', ItemList.Cover_Screen, 'R', ItemList.Sensor_MV, 'H', OrePrefixes.toolHeadDrill.get(Materials.Steel), 'E', ItemList.Emitter_MV, 'C', OrePrefixes.circuit.get(Materials.Good), 'P', OrePrefixes.plate.get(Materials.Aluminium), 'B', ItemList.Battery_RE_MV_Lithium.get(1L)});
        GT_ModHandler.addCraftingRecipe(scannerHV.copy(), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"EHR", "CSC", "PBP", 'S', ItemList.Cover_Screen, 'R', ItemList.Sensor_HV, 'H', OrePrefixes.toolHeadDrill.get(Materials.StainlessSteel), 'E', ItemList.Emitter_HV, 'C', OrePrefixes.circuit.get(Materials.Advanced), 'P', OrePrefixes.plate.get(Materials.StainlessSteel), 'B', ItemList.Battery_RE_HV_Lithium.get(1L)});
        GT_ModHandler.addCraftingRecipe(scannerEV.copy(), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"EHR", "CSC", "PBP", 'S', ItemList.Cover_Screen, 'R', ItemList.Sensor_EV, 'H', OrePrefixes.toolHeadDrill.get(Materials.Titanium), 'E', ItemList.Emitter_EV, 'C', OrePrefixes.circuit.get(Materials.Data), 'P', OrePrefixes.plate.get(Materials.Titanium), 'B', ItemList.IC2_LapotronCrystal.get(1L)});

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
