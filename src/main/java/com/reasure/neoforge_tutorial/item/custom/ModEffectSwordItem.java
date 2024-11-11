package com.reasure.neoforge_tutorial.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ModEffectSwordItem extends SwordItem {
    private final Supplier<MobEffectInstance> effect;

    public ModEffectSwordItem(Tier tier, Properties properties, Supplier<MobEffectInstance> effect) {
        super(tier, properties);
        this.effect = effect;
    }

    @Override
    public boolean onLeftClickEntity(@NotNull ItemStack stack, @NotNull Player player, @NotNull Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(effect.get(), player);
        }
        return super.onLeftClickEntity(stack, player, entity);
    }
}
