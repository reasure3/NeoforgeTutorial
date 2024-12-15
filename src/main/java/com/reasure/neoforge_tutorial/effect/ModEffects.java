package com.reasure.neoforge_tutorial.effect;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, NeoforgeTutorial.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> SLIMEY_EFFECT = MOB_EFFECTS.register("slimey",
            () -> new SlimeyEffect(MobEffectCategory.NEUTRAL, 0x36EBAB)
                    .addAttributeModifier(Attributes.SAFE_FALL_DISTANCE,
                            ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, "slimey"),
                            10, AttributeModifier.Operation.ADD_VALUE));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
