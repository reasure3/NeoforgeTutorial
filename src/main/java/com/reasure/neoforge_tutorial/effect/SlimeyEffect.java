package com.reasure.neoforge_tutorial.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

// Climbing Effect by SameDifferent: https://github.com/samedifferent/TrickOrTreat/blob/master/LICENSE
// Distributed under MIT
public class SlimeyEffect extends MobEffect {
    static final Double FALL_SPEED = -0.05;

    public SlimeyEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    /**
     * @param entity 효과를 적용할 엔티티
     * @return 엔티티가 블록에 붙어있고, 떨어지는 중이라면 true
     */
    private boolean canApplyEffect(LivingEntity entity) {
        return entity.horizontalCollision && entity.getDeltaMovement().y < -0.07;
    }

    @Override
    public boolean applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        if (canApplyEffect(entity)) {
            Vec3 initialVec = entity.getDeltaMovement();
            if (initialVec.y < -0.13) {
                double scale = FALL_SPEED / initialVec.y;
                entity.setDeltaMovement(initialVec.x * scale, FALL_SPEED, initialVec.z * scale);
            } else {
                entity.setDeltaMovement(initialVec.x, FALL_SPEED, initialVec.z);
            }

            return true;
        }

        return super.applyEffectTick(entity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
