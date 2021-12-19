package com.detrav.items.behaviours;

import com.detrav.DetravScannerMod;
import com.detrav.items.DetravMetaGeneratedTool01;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.LanguageRegistry;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.objects.ItemData;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.common.GT_Pollution;
import gregtech.common.GT_UndergroundOil;
import gregtech.common.blocks.GT_Block_Ores_Abstract;
import gregtech.common.blocks.GT_TileEntity_Ores;
import gregtech.common.items.behaviors.Behaviour_None;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.SplittableRandom;

/**
 * Created by wital_000 on 19.03.2016.
 */
public class BehaviourDetravToolProPick extends Behaviour_None {
    HashMap<String, Integer> ores;
    int badluck;

    protected final int mCosts;

    public BehaviourDetravToolProPick(int aCosts) {
        mCosts = aCosts;
    }
    
    public boolean onItemUse(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
    	
    	SplittableRandom aRandom =new SplittableRandom();
        int chance = ((1+aStack.getItemDamage())*8) > 100 ? 100 :(1+aStack.getItemDamage())*8;
        
    	if (aWorld.isRemote)
    		 return false;
    	
        if(aWorld.getBlock(aX,aY,aZ) == Blocks.bedrock)
        {
            if (!aWorld.isRemote && aRandom.nextInt(100) < chance) {
                FluidStack fStack = GT_UndergroundOil.undergroundOil(aWorld.getChunkFromBlockCoords(aX, aZ), -1);
            }
            return true;
        }
        if (aWorld.getBlock(aX, aY, aZ).getMaterial() == Material.rock || aWorld.getBlock(aX, aY, aZ).getMaterial() == Material.ground || aWorld.getBlock(aX, aY, aZ) == GregTech_API.sBlockOres1) {
            if (!aWorld.isRemote) {
            }
            return true;
        }
        return false;
    }

    protected void prospectSingleChunk(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ )
    {
        ores = new HashMap<String, Integer>();
        aPlayer.addChatMessage(new ChatComponentTranslation("detrav.scanner.prospecting", aX, aZ));
        processOreProspecting(aItem, aStack, aPlayer, aWorld.getChunkFromBlockCoords(aX, aZ), aWorld.getTileEntity(aX, aY, aZ),GT_OreDictUnificator.getAssociation(new ItemStack(aWorld.getBlock(aX, aY, aZ), 1, aWorld.getBlockMetadata(aX, aY, aZ))), new SplittableRandom(), 1000);
        
        for (String key : ores.keySet()) {
            int value = ores.get(key);
            addChatMassageByValue(aPlayer,value,key);
        }
    }

    protected void processOreProspecting(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, Chunk aChunk, TileEntity aTileEntity, ItemData tAssotiation, SplittableRandom aRandom, int chance)//TileEntity aTileEntity)
    {
		//TODO LuxinfineTeam code START
        String lang = aPlayer instanceof EntityPlayerMP ? ((EntityPlayerMP)aPlayer).translator : "en_US";
		//TODO LuxinfineTeam code END
        if (aTileEntity != null) {
            if (aTileEntity instanceof GT_TileEntity_Ores) {
                GT_TileEntity_Ores gt_entity = (GT_TileEntity_Ores) aTileEntity;
                short meta = gt_entity.getMetaData();
                //TODO LuxinfineTeam code RELACE
                //String name = Materials.getLocalizedNameForItem(GT_LanguageManager.getTranslation("gt.blockores." + meta + ".name"), meta%1000);
                String name = this.getTranslatedNameFromBlock(lang, "gt.blockores." + meta + ".name", meta % 1000);
                addOreToHashMap(name, aPlayer);
                return;
            }
        } else if (tAssotiation!=null){
            try {
                GT_TileEntity_Ores gt_entity = (GT_TileEntity_Ores) aTileEntity;
                String name = tAssotiation.toString();
                addChatMassageByValue(aPlayer, -1, name);
                return;
            }
            catch (Exception e)
            {
                addChatMassageByValue(aPlayer, -1, "ERROR, lol ^_^");
            }
        }else if (aRandom.nextInt(100) < chance) {
            int data = getMode(aStack);
            final String small_ore_keyword = StatCollector.translateToLocal("detrav.scanner.small_ore.keyword");
            final String ore_keyword = StatCollector.translateToLocal("detrav.scanner.ore.keyword");
            for (int x = 0; x < 16; x++)
                for (int z = 0; z < 16; z++) {
                    int ySize = aChunk.getHeightValue(x, z);
                    for (int y = 1; y < ySize; y++) {

                        Block tBlock = aChunk.getBlock(x,y,z);
                        short tMetaID = (short)aChunk.getBlockMetadata(x,y,z);
                        if (tBlock instanceof GT_Block_Ores_Abstract) {
                            TileEntity tTileEntity = aChunk.getTileEntityUnsafe(x,y,z);
                            if ((tTileEntity instanceof GT_TileEntity_Ores)
                                    && ((GT_TileEntity_Ores) tTileEntity).mNatural) {
                                tMetaID = (short)((GT_TileEntity_Ores) tTileEntity).getMetaData();
                                try {
                                    String name = Materials.getLocalizedNameForItem(GT_LanguageManager.getTranslation(tBlock.getUnlocalizedName() + "." + tMetaID + ".name"), tMetaID%1000);
                                    if (data != 1 && name.startsWith(small_ore_keyword)) continue;
                                    if (name.startsWith(small_ore_keyword)) if (data != 1) continue;
									//TODO LuxinfineTeam code REPLACE
                                    //addOreToHashMap(name, aPlayer);
									String translateName = this.getTranslatedNameFromBlock(lang, tBlock.getUnlocalizedName() + "." + tMetaID + ".name", tMetaID%1000);
                                    addOreToHashMap(translateName, aPlayer);
									//TODO LuxinfineTeam code
                                }
                                catch(Exception e) {
                                    String name = tBlock.getUnlocalizedName() + ".";
                                    if (data != 1 && name.contains(".small."))  continue;
                                    if (name.startsWith(small_ore_keyword)) if(data!=1) continue;
                                    addOreToHashMap(name, aPlayer);
                                }
                            }
                        } else if (data == 1) {
                            tAssotiation = GT_OreDictUnificator.getAssociation(new ItemStack(tBlock, 1, tMetaID));
                            if ((tAssotiation != null) && (tAssotiation.mPrefix.toString().startsWith(ore_keyword))) {
                                try {
                                    try {
                                        tMetaID = (short)tAssotiation.mMaterial.mMaterial.mMetaItemSubID;

                                        //TODO LuxinfineTeam code REPLACE
                                        //String name = Materials.getLocalizedNameForItem(GT_LanguageManager.getTranslation(tBlock.getUnlocalizedName() + "." + tMetaID + ".name"), tMetaID%1000);
                                        String name = this.getTranslatedNameFromBlock(lang, tBlock.getUnlocalizedName() + "." + tMetaID + ".name", tMetaID % 1000);
                                        addOreToHashMap(name, aPlayer);
                                    } catch (Exception e1) {
                                        String name = tAssotiation.toString();
                                        addOreToHashMap(name, aPlayer);
                                    }
                                }
                                catch (Exception ignored) { }
                            }
                        }

                    }
                }
            return;
        }
        else  {
            if (DetravScannerMod.DEBUGBUILD)
                aPlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+" Failed on this chunk"));
        	badluck++;
        }
    }

    void addOreToHashMap(String orename, EntityPlayer aPlayer) {
        String oreDistance = orename; // orename + the textual distance of the ore
        if (!ores.containsKey(oreDistance)) {
            if (DetravScannerMod.DEBUGBUILD)
                aPlayer.addChatMessage(new ChatComponentText(oreDistance));
            ores.put(oreDistance, 1);
        } else {
            int val = ores.get(oreDistance);
            ores.put(oreDistance, val + 1);
        }
    }

    void addChatMassageByValue(EntityPlayer aPlayer, int value, String name) {
        if (value < 1)
            aPlayer.addChatMessage(new ChatComponentTranslation("detrav.scanner.prospecting.nothing"));
        else if (value < 10)
            aPlayer.addChatMessage(new ChatComponentTranslation("detrav.scanner.prospecting.traces", new ChatComponentTranslation(name), value));
        else if (value < 30)
            aPlayer.addChatMessage(new ChatComponentTranslation("detrav.scanner.prospecting.smallsample", new ChatComponentTranslation(name), value));
        else if (value < 60)
            aPlayer.addChatMessage(new ChatComponentTranslation("detrav.scanner.prospecting.mediumsample", new ChatComponentTranslation(name), value));
        else if (value < 100)
            aPlayer.addChatMessage(new ChatComponentTranslation("detrav.scanner.prospecting.largesample", new ChatComponentTranslation(name), value));
        else
            aPlayer.addChatMessage(new ChatComponentTranslation("detrav.scanner.prospecting.verylargesample", new ChatComponentTranslation(name), value));
    }

    public static int getPolution(World aWorld, int aX, int aZ)
    {
        return GT_Pollution.getPollution(aWorld.getChunkFromBlockCoords(aX, aZ));
    }

    protected int getMode(ItemStack is) {
        if (is == null || is.getTagCompound() == null) {
            return 0;
        }
        return is.getTagCompound().getInteger("mode");
    }

    protected void setMode(ItemStack is, int mode) {
        if (is == null) {
            return;
        }
        NBTTagCompound tag = is.getTagCompound() == null ? new NBTTagCompound() : is.getTagCompound();
        tag.setInteger("mode", mode);
        is.setTagCompound(tag);
    }

    //TODO LuxinfineTeam code START
    private String getTranslatedNameFromBlock(String lang, String alternative, int meta) {
        if (meta >= 0 && meta < 1000) {
            Materials material = GregTech_API.sGeneratedMaterials[meta];
            if (material != null) {
				String materialName = material.mLocalizedName;
				material.mLocalizedName = LanguageRegistry.instance().getStringLocalization("Material." + material.mName.toLowerCase(), lang);
                String result = material.getLocalizedNameForItem(LanguageRegistry.instance().getStringLocalization(alternative, lang));
				material.mLocalizedName = materialName;
				return result;
			}
        }
        return LanguageRegistry.instance().getStringLocalization(alternative, lang);
    }
    //TODO LuxinfineTeam code END
}
