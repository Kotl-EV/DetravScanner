package com.detrav.utils;

import gregtech.api.enums.Materials;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.Arrays;
import java.util.Objects;

import static com.detrav.net.ProspectingPacket.fluidColors;

public class FluidColors {

    public static void makeColors(){

        reFillFluidColors();

        Arrays.stream(Materials.values()).forEach(
                mat -> {
                    if ( mat.getSolid(0) != null)
                        fluidColors.putIfAbsent(mat.getSolid(0).getFluidID(), mat.mRGBa);
                    if ( mat.getGas(0) != null)
                        fluidColors.putIfAbsent(mat.getGas(0).getFluidID(), mat.mRGBa);
                    if ( mat.getFluid(0) != null)
                        fluidColors.putIfAbsent(mat.getFluid(0).getFluidID(), mat.mRGBa);
                    if ( mat.getMolten(0) != null)
                        fluidColors.putIfAbsent(mat.getMolten(0).getFluidID(), mat.mRGBa);
                }
        );
        FluidRegistry.getRegisteredFluids().values().stream().filter(Objects::nonNull).forEach(fluid -> {
                    fluidColors.putIfAbsent(fluid.getID(), convertColorInt(fluid.getColor()));
                }
        );
    }

    private static void reFillFluidColors(){
        try {
            fluidColors.put(Materials.NatruralGas.mGas.getID(), new short[]{0x00, 0xff, 0xff});
            fluidColors.put(Materials.OilLight.mFluid.getID(), new short[]{0xff, 0xff, 0x00});
            fluidColors.put(Materials.OilMedium.mFluid.getID(), new short[]{0x00, 0xFF, 0x00});
            fluidColors.put(Materials.OilHeavy.mFluid.getID(), new short[]{0xFF, 0x00, 0xFF});
            fluidColors.put(Materials.Oil.mFluid.getID(), new short[]{0x00, 0x00, 0x00});
            fluidColors.put(Materials.Helium_3.mGas.getID(), new short[]{0x80, 0x20, 0xe0});
            fluidColors.put(Materials.SaltWater.mFluid.getID(), new short[]{0x80, 0xff, 0x80});
            fluidColors.put(Materials.Naquadah.getMolten(0).getFluid().getID(), new short[]{0x20, 0x20, 0x20});
            fluidColors.put(Materials.NaquadahEnriched.getMolten(0).getFluid().getID(), new short[]{0x60, 0x60, 0x60});
            fluidColors.put(Materials.Lead.getMolten(0).getFluid().getID(), new short[]{0xd0, 0xd0, 0xd0});
            fluidColors.put(Materials.Chlorobenzene.mFluid.getID(), new short[]{0x40, 0x80, 0x40});
            fluidColors.put(FluidRegistry.getFluid("liquid_extra_heavy_oil").getID(), new short[]{0x00, 0x00, 0x50});
            fluidColors.put(Materials.Oxygen.mGas.getID(), new short[]{0x40, 0x40, 0xA0});
            fluidColors.put(Materials.Nitrogen.mGas.getID(), new short[]{0x00, 0x80, 0xd0});
            fluidColors.put(Materials.Methane.mGas.getID(), new short[]{0x80, 0x20, 0x20});
            fluidColors.put(Materials.Ethane.mGas.getID(), new short[]{0x40, 0x80, 0x20});
            fluidColors.put(Materials.Ethylene.mGas.getID(), new short[]{0xd0, 0xd0, 0xd0});
            fluidColors.put(FluidRegistry.LAVA.getID(), new short[]{0xFF, 0x00, 0x00});
            fluidColors.put(Materials.LiquidAir.mFluid.getID(), new short[]{0x40, 0x80, 0x40});
        }catch (Exception ignored){
        }
    }

    private static short[] convertColorInt(int color){
        return new short[]{(short) (color << 16 &0xff), (short) (color << 8 &0xff), (short) (color &0xff)};
    }

}