package devhook;

public class HookLoader extends gloomyfolken.hooklib.minecraft.HookLoader {
    @Override
    protected void registerHooks() {
        registerHookContainer("devhook.FixFuckingPotionRadiationHook");
    }
}
