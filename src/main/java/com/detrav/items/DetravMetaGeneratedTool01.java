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
//        super("detrav.metatool.01");
        INSTANCE = this;
//        addTool(100, "Electric Prospector's Scanner (LuV)", "", new DetravToolElectricProspector(6));
//        addTool(102, "Electric Prospector's Scanner (ZPM)", "", new DetravToolElectricProspector(7));
//        addTool(104, "Electric Prospector's Scanner (UV)", "", new DetravToolElectricProspector(8));
//        addTool(106, "Electric Prospector's Scanner (MaxV)", "", new DetravToolElectricProspector(9));
//        setCreativeTab(DetravScannerMod.TAB_DETRAV);
    }


    @Override
    public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
//        int tOffset = aList.size();
//
//        String name = aStack.getUnlocalizedName();
//        String num = name.substring("gt.detrav.metatool.01.".length());
//        int meta = Integer.parseInt(num);
//        if ((range % 2) == 0 ) {
//            range += 1;
//        }
//        if (meta<100) {
//            aList.add(tOffset + 0, EnumChatFormatting.WHITE + StatCollector.translateToLocal("tooltip.detrav.scanner.durability") + EnumChatFormatting.GREEN + Long.toString(tMaxDamage - getToolDamage(aStack)) + " / " + Long.toString(tMaxDamage) + EnumChatFormatting.GRAY);
//            aList.add(tOffset + 1, EnumChatFormatting.WHITE + tMaterial.getLocalizedNameForItem("%material") + EnumChatFormatting.GRAY);
//            aList.add(tOffset + 2, EnumChatFormatting.WHITE + StatCollector.translateToLocal("tooltip.detrav.scanner.range") + Integer.toString(range) + "x"+ Integer.toString(range) + EnumChatFormatting.GRAY);
//            aList.add(tOffset + 3, EnumChatFormatting.ITALIC+ StatCollector.translateToLocal("tooltip.detrav.scanner.usage.0") + EnumChatFormatting.GRAY);
//            aList.add(tOffset + 4, EnumChatFormatting.ITALIC+ StatCollector.translateToLocal("tooltip.detrav.scanner.usage.1") + EnumChatFormatting.GRAY);
//            aList.add(tOffset + 5, EnumChatFormatting.ITALIC+ StatCollector.translateToLocal("tooltip.detrav.scanner.success.chance")+EnumChatFormatting.RESET+Integer.toString(((((1+meta)*8) <= 100)? ((1+meta)*8) : 100))+EnumChatFormatting.GRAY+"%");
//            aList.add(tOffset + 6, EnumChatFormatting.ITALIC+ StatCollector.translateToLocal("tooltip.detrav.scanner.distance.0"));
//            aList.add(tOffset + 7, EnumChatFormatting.ITALIC+ StatCollector.translateToLocal("tooltip.detrav.scanner.distance.1"));
//
//        } else if (meta >=100 && meta<200) {
//            aList.add(tOffset + 0, EnumChatFormatting.WHITE + StatCollector.translateToLocal("tooltip.detrav.scanner.durability") + EnumChatFormatting.GREEN + (tMaxDamage - getToolDamage(aStack)) + " / " + tMaxDamage + EnumChatFormatting.GRAY);
//            aList.add(tOffset + 1, EnumChatFormatting.WHITE + tMaterial.getLocalizedNameForItem("%material") + EnumChatFormatting.GRAY);
//            aList.add(tOffset + 2, EnumChatFormatting.WHITE + StatCollector.translateToLocal("tooltip.detrav.scanner.range") + EnumChatFormatting.YELLOW + (getHarvestLevel(aStack, "") * 2 + 1) + "x" + (getHarvestLevel(aStack, "") * 2 + 1) + EnumChatFormatting.GRAY);
//            aList.add(tOffset + 3, EnumChatFormatting.ITALIC+ StatCollector.translateToLocal("tooltip.detrav.scanner.usage.0"));
//            aList.add(tOffset + 4, EnumChatFormatting.ITALIC+ StatCollector.translateToLocal("tooltip.detrav.scanner.usage.1"));
//            aList.add(tOffset + 5, EnumChatFormatting.ITALIC+ StatCollector.translateToLocal("tooltip.detrav.scanner.usage.2"));
//            aList.add(tOffset + 6, EnumChatFormatting.ITALIC+ StatCollector.translateToLocal("tooltip.detrav.scanner.usage.3"));
//            aList.add(tOffset + 7, EnumChatFormatting.ITALIC+ StatCollector.translateToLocal("tooltup.detrav.scanner.quantum"));
//        }
        return aList;
    }

//    //SubItems TODO
//    @SuppressWarnings("unchecked")
//    public void getDetravSubItems(Item item, CreativeTabs detravCreativeTab, List list) {
//        ItemStack dStack;
////        //Electric Scanners
////        dStack = getToolWithStats(100, 1, Materials.Iridium, Materials.TungstenSteel, new long[]{102400000L, GT_Values.V[6], 6L, -1L});
////        setCharge(dStack,102400000L);
////        list.add(dStack);
////        dStack = getToolWithStats(102, 1, Materials.Neutronium, Materials.TungstenSteel, new long[]{409600000L, GT_Values.V[7], 7L, -1L});
////        setCharge(dStack,409600000L);
////        list.add(dStack);
//        //Electric Scanners
//        dStack = getToolWithStats(100, 1, Materials.Iridium, Materials.TungstenSteel, new long[]{102400000L, GT_Values.V[6], 6L, -1L});
//        setCharge(dStack,102400000L);
//        list.add(dStack);
//        dStack = getToolWithStats(102, 1, Materials.HSSS, Materials.TungstenSteel, new long[]{409600000L, GT_Values.V[7], 7L, -1L});
//        setCharge(dStack,409600000L);
//        list.add(dStack);
//        dStack = getToolWithStats(104, 1, Materials.Neutronium, Materials.TungstenSteel, new long[]{1638400000L, GT_Values.V[8], 8L, -1L});
//        setCharge(dStack, 1638400000L);
//        list.add(dStack);
//        dStack = getToolWithStats(106, 1, Materials.Neutronium, Materials.TungstenSteel, new long[]{6553600000L, GT_Values.V[8], 9L, -1L});
//        setCharge(dStack, 6553600000L);
//        list.add(dStack);
//    }
}
