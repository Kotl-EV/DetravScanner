package com.detrav.items.behaviours;

import com.detrav.items.DetravMetaGeneratedTool01;
import com.detrav.net.DetravNetwork;
import com.detrav.net.ProspectingPacket;
import gregtech.api.enums.GT_Values;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.objects.ItemData;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_UndergroundOil;
import gregtech.common.blocks.GT_Block_Ores_Abstract;
import gregtech.common.blocks.GT_TileEntity_Ores;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wital_000 on 19.03.2016.
 */
public class BehaviourDetravToolElectricProspector extends BehaviourDetravToolProPick {

    int usage, radius;

    public BehaviourDetravToolElectricProspector(int usage, int radius) {
        super(0);
        this.usage = usage;
        this.radius = radius;
    }

    public ItemStack onItemRightClick(GT_MetaBase_Item aItem, ItemStack aStack, World aWorld, EntityPlayer aPlayer) {
//        radius = 5;
        if (aWorld.isRemote) {
            return aStack;
        }
        if (!(aPlayer.isSneaking() || aPlayer.capabilities.isCreativeMode || GT_Utility.isWearingFullQuantumArmor(aPlayer))) {
            aPlayer.addChatMessage(new ChatComponentText("Quantum Synchronization Failed"));
            return aStack;
        }
        if (!aWorld.isRemote && canUse(aItem, aStack)) {
            int data = getMode(aStack);
            if (aPlayer.isSneaking()) {
                data++;
                if (data > 3) data = 0;
                switch (data) {
                    case 0:
                        aPlayer.addChatMessage(new ChatComponentText("Set Mode: Ore, Any Rock Block"));
                        break;
                    case 1:
                        aPlayer.addChatMessage(new ChatComponentText("Set Mode: Ore (with small), Any Rock Block"));
                        break;
                    case 2:
                        aPlayer.addChatMessage(new ChatComponentText("Set Mode: Oil, Any Block"));
                        break;
                    case 3:
                        aPlayer.addChatMessage(new ChatComponentText("Set Mode: Pollution, Any Block"));
                        break;
                    default:
                        aPlayer.addChatMessage(new ChatComponentText("Set Mode: ERROR"));
                        break;
                }
                setMode(aStack, data);
                return super.onItemRightClick(aItem, aStack, aWorld, aPlayer);
            }

//            final DetravMetaGeneratedTool01 tool = (DetravMetaGeneratedTool01) aItem;
            final int cX = ((int) aPlayer.posX) >> 4;
            final int cZ = ((int) aPlayer.posZ) >> 4;
            int size = radius + 1;
            final List<Chunk> chunks = new ArrayList<>();
            aPlayer.addChatMessage(new ChatComponentText("Scanning..."));
            for (int i = -size; i <= size; i++)
                for (int j = -size; j <= size; j++)
                    if (i != -size && i != size && j != -size && j != size)
                        chunks.add(aWorld.getChunkFromChunkCoords(cX + i, cZ + j));
            size = size - 1;
            final ProspectingPacket packet = new ProspectingPacket(cX, cZ, (int) aPlayer.posX, (int) aPlayer.posZ, size, data);
            final String small_ore_keyword = StatCollector.translateToLocal("detrav.scanner.small_ore.keyword");
            for (Chunk c : chunks) {
                for (int x = 0; x < 16; x++)
                    for (int z = 0; z < 16; z++) {
                        final int ySize = c.getHeightValue(x, z);
                        for (int y = 1; y < ySize; y++) {
                            switch (data) {
                                case 0:
                                case 1:
                                    final Block tBlock = c.getBlock(x, y, z);
                                    short tMetaID = (short) c.getBlockMetadata(x, y, z);
                                    if (tBlock instanceof GT_Block_Ores_Abstract) {
                                        TileEntity tTileEntity = c.getTileEntityUnsafe(x, y, z);
                                        if ((tTileEntity instanceof GT_TileEntity_Ores) && ((GT_TileEntity_Ores) tTileEntity).mNatural) {
                                            tMetaID = (short) ((GT_TileEntity_Ores) tTileEntity).getMetaData();
                                            try {
                                                String name = GT_LanguageManager.getTranslation(tBlock.getUnlocalizedName() + "." + tMetaID + ".name");
                                                if (data != 1 && name.startsWith(small_ore_keyword)) continue;
                                                packet.addBlock(c.xPosition * 16 + x, y, c.zPosition * 16 + z, tMetaID);
                                            } catch (Exception e) {
                                                String name = tBlock.getUnlocalizedName() + ".";
                                                if (data != 1 && name.contains(".small.")) continue;
                                                packet.addBlock(c.xPosition * 16 + x, y, c.zPosition * 16 + z, tMetaID);
                                            }
                                        }
                                    } else if (data == 1) {
                                        ItemData tAssotiation = GT_OreDictUnificator.getAssociation(new ItemStack(tBlock, 1, tMetaID));
                                        if ((tAssotiation != null) && (tAssotiation.mPrefix.toString().startsWith("ore"))) {
                                            packet.addBlock(c.xPosition * 16 + x, y, c.zPosition * 16 + z, (short) tAssotiation.mMaterial.mMaterial.mMetaItemSubID);
                                        }
                                    }
                                    break;
                                case 2:
                                    if ((x == 0) || (z == 0)) { //Skip doing the locations with the grid on them.
                                        break;
                                    }
                                    FluidStack fStack = GT_UndergroundOil.undergroundOil(aWorld.getChunkFromBlockCoords(c.xPosition * 16 + x, c.zPosition * 16 + z), -1);
                                    if (fStack.amount > 0) {
                                        packet.addBlock(c.xPosition * 16 + x, 1, c.zPosition * 16 + z, (short) fStack.getFluidID());
                                        packet.addBlock(c.xPosition * 16 + x, 2, c.zPosition * 16 + z, (short) fStack.amount);
                                    }
                                    break;
                                case 3:
                                    float polution = (float) getPolution(aWorld, c.xPosition * 16 + x, c.zPosition * 16 + z);
                                    polution /= 2000000;
                                    polution *= -0xFF;
                                    if (polution > 0xFF)
                                        polution = 0xFF;
                                    polution = 0xFF - polution;
                                    packet.addBlock(c.xPosition * 16 + x, 1, c.zPosition * 16 + z, (short) polution);
                                    break;
                            }
                            if (data > 1)
                                break;
                        }
                    }
            }
            packet.level = 0;
            DetravNetwork.INSTANCE.sendToPlayer(packet, (EntityPlayerMP) aPlayer);
            use(aItem, aPlayer, aStack);
        }
        return super.onItemRightClick(aItem, aStack, aWorld, aPlayer);
    }

    void addChatMassageByValue(EntityPlayer aPlayer, int value, String name) {
        if (value < 0) {
            aPlayer.addChatMessage(new ChatComponentText(foundTexts[6] + name));
        } else if (value < 1) {
            aPlayer.addChatMessage(new ChatComponentText(foundTexts[0]));
        } else
            aPlayer.addChatMessage(new ChatComponentText(foundTexts[6] + name + " " + value));
    }

    public boolean onItemUse(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
        long data = getMode(aStack);
        if (data < 2) {
            if (aWorld.getBlock(aX, aY, aZ) == Blocks.bedrock) {
                if (!aWorld.isRemote) {
                    FluidStack fStack = GT_UndergroundOil.undergroundOil(aWorld.getChunkFromBlockCoords(aX, aZ), -1);
                    addChatMassageByValue(aPlayer, fStack.amount, fStack.getLocalizedName());
//                    if (!aPlayer.capabilities.isCreativeMode)
//                        ((DetravMetaGeneratedTool01)aItem).doDamage(aStack, this.mCosts);
                }
                return true;
            } else {
                if (!aWorld.isRemote) {
                    prospectSingleChunk(aItem, aStack, aPlayer, aWorld, aX, aY, aZ);
                }
                return true;
            }
        }
        if (data < 3)
            if (!aWorld.isRemote) {
                FluidStack fStack = GT_UndergroundOil.undergroundOil(aWorld.getChunkFromBlockCoords(aX, aZ), -1);
                addChatMassageByValue(aPlayer, fStack.amount, fStack.getLocalizedName());
//                if (!aPlayer.capabilities.isCreativeMode)
//                    ((DetravMetaGeneratedTool01) aItem).doDamage(aStack, this.mCosts);
                return true;
            }
        if (!aWorld.isRemote) {
            int polution = getPolution(aWorld, aX, aZ);
            addChatMassageByValue(aPlayer, polution, "Pollution");
        }
        return true;
    }



    boolean canUse(GT_MetaBase_Item item, ItemStack is) {
        return item.canUse(is, usage);
    }

    void use(GT_MetaBase_Item item, EntityPlayer player, ItemStack is) {
        if (!player.capabilities.isCreativeMode) {
            item.use(is, usage, player);
        }
    }

    @Override
    public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
        aList.add("Uses " + usage + " per scan");
        aList.add("Scan area: " + EnumChatFormatting.YELLOW + (radius * 2 + 1) + "x" + (radius * 2 + 1) + EnumChatFormatting.GRAY + " chunks");
        aList.add("Shift rightclick air to change mode");
        aList.add("Available modes: GT-Ores, GT-Ores with small, Underbedrock Fluids, GT-Pollution");
        aList.add("Right click block to prospect current chunk");
        aList.add("Right click air to prospect current area (Requires Quantum Set)");
        return super.getAdditionalToolTips(aItem, aList, aStack);
    }
}
