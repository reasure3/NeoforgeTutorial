package com.reasure.neoforge_tutorial.potion;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import com.reasure.neoforge_tutorial.effect.ModEffects;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(BuiltInRegistries.POTION, NeoforgeTutorial.MODID);

    public static final DeferredHolder<Potion, Potion> SLIMEY_POTION = POTIONS.register("slimey_potion",
            () -> new Potion("slimey_potion", new MobEffectInstance(ModEffects.SLiMEY_EFFECT, 3600, 0)));

    public static final DeferredHolder<Potion, Potion> LONG_SLIMEY_POTION = POTIONS.register("long_slimey_potion",
            () -> new Potion("slimey_potion", new MobEffectInstance(ModEffects.SLiMEY_EFFECT, 9600, 0)));


    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}
