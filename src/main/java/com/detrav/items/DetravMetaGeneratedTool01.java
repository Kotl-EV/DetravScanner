package com.detrav.items;

import java.util.List;

import com.detrav.DetravScannerMod;
import com.detrav.items.tools.DetravToolElectricProPick;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.interfaces.IToolStats;
import gregtech.api.items.GT_MetaGenerated_Tool;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;

import static com.detrav.DetravScannerMod.DEBUGBUILD;

/**
 * Created by wital_000 on 19.03.2016.
 */
public class DetravMetaGeneratedTool01 extends GT_MetaGenerated_Tool {
    public static DetravMetaGeneratedTool01 INSTANCE;

    public DetravMetaGeneratedTool01() {
        super("detrav.metatool.01");
        INSTANCE = this;
        addTool(100, "Electric Prospector's Scanner (LuV)", "", new DetravToolElectricProPick(6));
        addTool(102, "Electric Prospector's Scanner (ZPM)", "", new DetravToolElectricProPick(7));
        addTool(104, "Electric Prospector's Scanner (UV)", "", new DetravToolElectricProPick(8));
        addTool(106, "Electric Prospector's Scanner (UHV)", "", new DetravToolElectricProPick(9));
        setCreativeTab(DetravScannerMod.TAB_DETRAV);
    }
    @SuppressWarnings("unchecked")
    public void addAdditionalToolTips(List aList, ItemStack aStack, EntityPlayer aPlayer) {
        //getElectricStats()
        //super.addAdditionalToolTips();
        long tMaxDamage = getToolMaxDamage(aStack);
        Materials tMaterial = getPrimaryMaterial(aStack);
        IToolStats tStats = getToolStats(aStack);
        int tOffset = aList.size(); 
		//getElectricStats(aStack) != null ? 2 : 1;
        if (tStats != null) {
            String name = aStack.getUnlocalizedName();
            String num = name.substring("gt.detrav.metatool.01.".length());
            int meta = Integer.parseInt(num);
            int range = getHarvestLevel(aStack, "")/2+(meta/4);
            if ((range % 2) == 0 ) {
                range += 1;
            }
            if (meta >=100 && meta<200) {
                    aList.add(tOffset + 0, EnumChatFormatting.WHITE + "Durability: " + EnumChatFormatting.GREEN + (tMaxDamage - getToolDamage(aStack)) + " / " + tMaxDamage + EnumChatFormatting.GRAY);
                    aList.add(tOffset + 1, EnumChatFormatting.WHITE + tMaterial.mDefaultLocalName + EnumChatFormatting.GRAY);
                    aList.add(tOffset + 2, EnumChatFormatting.WHITE + "Chunks: " + EnumChatFormatting.YELLOW + (getHarvestLevel(aStack, "") * 2 + 1) + "x" + (getHarvestLevel(aStack, "") * 2 + 1) + EnumChatFormatting.GRAY);
                    aList.add(tOffset + 3, EnumChatFormatting.ITALIC+ "Right click on rock for prospecting current chunk!");
                    aList.add(tOffset + 4, EnumChatFormatting.ITALIC+ "Right click on bedrock for prospecting oil!");
                    aList.add(tOffset + 5, EnumChatFormatting.ITALIC+ "Right click for scanning!");
            }
        }
    }

    public Long getElectricStatsLoss(ItemStack aStack) {
        NBTTagCompound aNBT = aStack.getTagCompound();
        if (aNBT != null) {
            aNBT = aNBT.getCompoundTag("GT.ToolStats");
            if (aNBT != null && aNBT.getBoolean("Electric"))
                return aNBT.getLong("Loss");
        }
        return 0L;
    }

    public final ItemStack getToolWithStatsPlus(int aToolID, int aAmount, Materials aPrimaryMaterial, Materials aSecondaryMaterial, long[] aElectricArray, long aLoss) {
        return getToolWithStatsPlus(aToolID, aAmount, aPrimaryMaterial, aSecondaryMaterial, aElectricArray, aLoss, 10000L);
    }

    public final ItemStack getToolWithStatsPlus(int aToolID, int aAmount, Materials aPrimaryMaterial, Materials aSecondaryMaterial, long[] aElectricArray, long aLoss, long durability) {
        ItemStack result = getToolWithStats(aToolID, aAmount, aPrimaryMaterial, aSecondaryMaterial, aElectricArray);
        NBTTagCompound aNBT = result.getTagCompound();
        if (aNBT != null) {
            aNBT = aNBT.getCompoundTag("GT.ToolStats");
            if (aNBT != null && aNBT.getBoolean("Electric")) {
                aNBT.setLong("Loss", aLoss);
            }
            aNBT.setLong("MaxDamage", durability);
        }
        return result;
    }

    public Long getToolGTDetravData(ItemStack aStack) {
        NBTTagCompound aNBT = aStack.getTagCompound();
        if (aNBT != null) {
            aNBT = aNBT.getCompoundTag("GT.ToolStats");
            if (aNBT != null)
                return aNBT.getLong("DetravData");
        }
        return 0L;
    }

    public boolean setToolGTDetravData(ItemStack aStack, long data) {
        NBTTagCompound aNBT = aStack.getTagCompound();
        if (aNBT != null) {
            aNBT = aNBT.getCompoundTag("GT.ToolStats");
            if (aNBT != null) {
                aNBT.setLong("DetravData", data);
                return true;
            }
        }
        return false;
    }

    public void setLevelToItemStack(ItemStack aStack, int level, float percent)
    {
        if(aStack == null) return;
        NBTTagCompound aNBT = aStack.getTagCompound();
        if(aNBT == null) {
            aNBT = new NBTTagCompound();
            NBTTagCompound detravLevel = new NBTTagCompound();
            aNBT.setTag("DetravLevel", detravLevel);
            aStack.setTagCompound(aNBT);
        }
        {
            NBTTagCompound detravLevel = aNBT.getCompoundTag("DetravLevel");
            if (detravLevel == null || hasnolevel(detravLevel)) {
                detravLevel = new NBTTagCompound();
                aNBT.setTag("DetravLevel", detravLevel);
            }
            detravLevel.setFloat("level"+Integer.toString(level),percent);
        }
    }


    private boolean hasnolevel(NBTTagCompound detravLevel)
    {
        for(int i=0;i<9;i++)
        {
            if(detravLevel.hasKey("level"+Integer.toString(i)))
              return false;
        }
        return true;
    }

    public float getLevel(ItemStack aStack, int level)
    {
        if(aStack == null) return 0;
        NBTTagCompound aNBT = aStack.getTagCompound();
        if(aNBT ==null) return 0;
        NBTTagCompound detravLevel = aNBT.getCompoundTag("DetravLevel");
        if(detravLevel == null) return 0;
        return detravLevel.getFloat("level"+Integer.toString(level));
    }

    public boolean setItemStackToDetravData(ItemStack aStack, ItemStack what)
    {
        if(aStack == null) return false;
        NBTTagCompound aNBT = aStack.getTagCompound();
        if(aNBT == null) {
            aNBT = new NBTTagCompound();
            NBTTagCompound detravData = new NBTTagCompound();
            aNBT.setTag("DetravData", detravData);
            aStack.setTagCompound(aNBT);
        }
        {
            NBTTagCompound detravData = aNBT.getCompoundTag("DetravData");
            if (detravData == null || detravData.getShort("id") == 0) {
                detravData = new NBTTagCompound();
                aNBT.setTag("DetravData", detravData);
            }
            if (what == null)
                aNBT.removeTag("DetravData");
            else
                what.writeToNBT(detravData);
            return true;
        }
    }

    public ItemStack getItemStackFromDetravData(ItemStack aStack)
    {
        if(aStack == null) return null;
        NBTTagCompound aNBT = aStack.getTagCompound();
        if(aNBT ==null) return null;
        NBTTagCompound detravData = aNBT.getCompoundTag("DetravData");
        if(detravData == null) return null;
        return ItemStack.loadItemStackFromNBT(detravData);
    }


    public boolean setFluidStackToDetravData(ItemStack aStack, FluidStack what)
    {
        if(aStack == null) return false;
        NBTTagCompound aNBT = aStack.getTagCompound();
        if(aNBT == null) {
            aNBT = new NBTTagCompound();
            NBTTagCompound detravData = new NBTTagCompound();
            aNBT.setTag("DetravData", detravData);
            aStack.setTagCompound(aNBT);
        }
        {
            NBTTagCompound detravData = aNBT.getCompoundTag("DetravData");
            if (detravData == null || detravData.getShort("id") == 0) {
                detravData = new NBTTagCompound();
                aNBT.setTag("DetravData", detravData);
            }
            if (what == null)
                aNBT.removeTag("DetravData");
            else
                what.writeToNBT(detravData);
            return true;
        }
    }

    public FluidStack getFluidStackFromDetravData(ItemStack aStack)
    {
        if(aStack == null) return null;
        NBTTagCompound aNBT = aStack.getTagCompound();
        if(aNBT ==null) return null;
        NBTTagCompound detravData = aNBT.getCompoundTag("DetravData");
        if(detravData == null) return null;
        return FluidStack.loadFluidStackFromNBT(detravData);
    }

    //SubItems TODO
    @SuppressWarnings("unchecked")
    public void getDetravSubItems(Item item, CreativeTabs detravCreativeTab, List list) {
        ItemStack dStack;
        //Electric Scanners 
        dStack = getToolWithStats(100, 1, Materials.Iridium, Materials.TungstenSteel, new long[]{102400000L, GT_Values.V[6], 6L, -1L});
        setCharge(dStack,102400000L);
        list.add(dStack);
        dStack = getToolWithStats(102, 1, Materials.Neutronium, Materials.TungstenSteel, new long[]{409600000L, GT_Values.V[7], 7L, -1L});
        setCharge(dStack,409600000L);
        list.add(dStack);
      //Electric Scanners 
        dStack = getToolWithStats(100, 1, Materials.Iridium, Materials.TungstenSteel, new long[]{102400000L, GT_Values.V[6], 6L, -1L});
        setCharge(dStack,102400000L);
        list.add(dStack);
        dStack = getToolWithStats(102, 1, Materials.Neutronium, Materials.TungstenSteel, new long[]{409600000L, GT_Values.V[7], 7L, -1L});
        setCharge(dStack,409600000L);
        list.add(dStack);
        dStack = getToolWithStats(104, 1, Materials.Phoenixite, Materials.TungstenSteel, new long[]{1638400000L, GT_Values.V[8], 8L, -1L});
        setCharge(dStack, 1638400000L);
        list.add(dStack);
        dStack = getToolWithStats(106, 1, Materials.Infinity, Materials.TungstenSteel, new long[]{6553600000L, GT_Values.V[9], 9L, -1L});
        setCharge(dStack, 6553600000L);
        list.add(dStack);
    }
}
