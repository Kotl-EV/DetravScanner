package com.detrav.items.behaviours;


import com.detrav.net.DetravNetwork;
import com.detrav.net.ProspectingPacket;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
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
        if (aWorld.isRemote) {
            return aStack;
        }
        /*if (!(aPlayer.isSneaking() || aPlayer.capabilities.isCreativeMode || GT_Utility.isWearingFullQuantumArmor(aPlayer))) {
            aPlayer.addChatMessage(new ChatComponentTranslation("detrav.scanner.QuantumSynchronization.Failed"));
            return aStack;
        }*/
        // todo remove code Kotl
        // Зависимость сета кванта для открытия гуи сканеров
        if (!aWorld.isRemote && canUse(aItem, aStack)) {
            int data = getMode(aStack);
            if (aPlayer.isSneaking()) {
                data++;
                if (data > 3) data = 0;
                switch (data) {
                    case 0:
                        aPlayer.addChatMessage(new ChatComponentTranslation("detrav.scanner.mode.0"));
                        break;
                    case 1:
                        aPlayer.addChatMessage(new ChatComponentTranslation("detrav.scanner.mode.1"));
                        break;
                    case 2:
                        aPlayer.addChatMessage(new ChatComponentTranslation("detrav.scanner.mode.2"));
                        break;
                    case 3:
                        aPlayer.addChatMessage(new ChatComponentTranslation("detrav.scanner.mode.3"));
                        break;
                    default:
                        aPlayer.addChatMessage(new ChatComponentTranslation("detrav.scanner.mode.error"));
                        break;
                }
                setMode(aStack, data);
                return super.onItemRightClick(aItem, aStack, aWorld, aPlayer);
            }

            final int cX = ((int) aPlayer.posX) >> 4;
            final int cZ = ((int) aPlayer.posZ) >> 4;
            int size = radius + 1;
            final List<Chunk> chunks = new ArrayList<>();
            aPlayer.addChatMessage(new ChatComponentTranslation("detrav.scanner.scanning"));
            for (int i = -size; i <= size; i++)
                for (int j = -size; j <= size; j++)
                    if (i != -size && i != size && j != -size && j != size)
                        chunks.add(aWorld.getChunkFromChunkCoords(cX + i, cZ + j));
            size = size - 1;
            final ProspectingPacket packet = new ProspectingPacket(cX, cZ, (int) aPlayer.posX, (int) aPlayer.posZ, size, data);
            final String small_ore_keyword = StatCollector.translateToLocal("detrav.scanner.small_ore.keyword");
            final String ore_keyword = StatCollector.translateToLocal("detrav.scanner.ore.keyword");
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
                                        if ((tAssotiation != null) && (tAssotiation.mPrefix.toString().startsWith(ore_keyword))) {
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

    void sendPolutionInfo(EntityPlayer aPlayer, int value) {
        if(value < 1)
            aPlayer.addChatMessage(new ChatComponentTranslation("detrav.scanner.prospecting.nothing")); //Не нашлось ничего интересного
        else
            aPlayer.addChatMessage(new ChatComponentTranslation("detrav.scanner.pollution.count", value)); //Обнаружено загрязнение: %d
    }

    public boolean onItemUse(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
        long data = getMode(aStack);
        if (data < 2) {
            if (aWorld.getBlock(aX, aY, aZ) == Blocks.bedrock) {
                if (!aWorld.isRemote) {
                    FluidStack fStack = GT_UndergroundOil.undergroundOil(aWorld.getChunkFromBlockCoords(aX, aZ), -1);
                    addChatMassageByValue(aPlayer, fStack.amount, fStack.getUnlocalizedName());

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
                addChatMassageByValue(aPlayer, fStack.amount, fStack.getUnlocalizedName());
                return true;
            }
        if (!aWorld.isRemote) {
            sendPolutionInfo(aPlayer, getPolution(aWorld, aX, aZ));
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
        int radiuschunk = radius * 2 + 1;
        aList.add(StatCollector.translateToLocalFormatted("detrav.tooltip.uses", usage));
        aList.add(StatCollector.translateToLocalFormatted("detrav.tooltip.scan.area", radiuschunk, radiuschunk));
        aList.add(StatCollector.translateToLocal("detrav.tooltip.scanner.usage.1"));
        aList.add(StatCollector.translateToLocal("detrav.tooltip.scanner.usage.modes"));
        aList.add(StatCollector.translateToLocal("detrav.tooltip.scanner.usage.0"));
        aList.add(StatCollector.translateToLocal("detrav.tooltip.scanner.quantum"));
        return super.getAdditionalToolTips(aItem, aList, aStack);
    }
}
