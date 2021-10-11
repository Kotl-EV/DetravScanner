package devhook;

import gloomyfolken.hooklib.asm.Hook;
import gloomyfolken.hooklib.asm.ReturnCondition;
import ic2.core.IC2Potion;
import ic2.core.init.BlocksItems;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class FixFuckingPotionRadiationHook {



    @Hook(injectOnExit = false, returnCondition = ReturnCondition.ALWAYS)
    public static void initPotions(BlocksItems b) {
        System.out.println("No more fucking POTION_RADIATION missing");
        try {
            Class<IC2Potion> cl = IC2Potion.class;
            Constructor<IC2Potion> constructor = (Constructor<IC2Potion>) IC2Potion.class.getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            Field field = cl.getDeclaredField("radiation");
            field.setAccessible(true);
            field.set(null, constructor.newInstance(ConfigUtil.getInt(MainConfig.get(), "misc/radiationPotionID"), true, 5149489, new ItemStack[0]));

        }catch (Exception | Error e) {
            e.printStackTrace();
            int a = 0;
        }
    }
}
