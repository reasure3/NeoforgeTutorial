package com.reasure.neoforge_tutorial.sound;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {
    // Sounds should be .ogg in mono (not stereo)
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, NeoforgeTutorial.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> CHAINSAW_CUT = registerSoundEvent("chainsaw_cut");
    public static final DeferredHolder<SoundEvent, SoundEvent> CHAINSAW_PULL = registerSoundEvent("chainsaw_pull");

    public static final DeferredHolder<SoundEvent, SoundEvent> MAGIC_BLOCK_BREAK = registerSoundEvent("magic_block_break");
    public static final DeferredHolder<SoundEvent, SoundEvent> MAGIC_BLOCK_STEP = registerSoundEvent("magic_block_step");
    public static final DeferredHolder<SoundEvent, SoundEvent> MAGIC_BLOCK_PLACE = registerSoundEvent("magic_block_place");
    public static final DeferredHolder<SoundEvent, SoundEvent> MAGIC_BLOCK_HIT = registerSoundEvent("magic_block_hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> MAGIC_BLOCK_FALL = registerSoundEvent("magic_block_fall");

    public static final DeferredSoundType MAGIC_BLOCK_SOUNDS = new DeferredSoundType(1f, 1f,
            MAGIC_BLOCK_BREAK, MAGIC_BLOCK_STEP, MAGIC_BLOCK_PLACE, MAGIC_BLOCK_HIT, MAGIC_BLOCK_FALL);

    public static final DeferredHolder<SoundEvent, SoundEvent> BAR_BRAWL = registerSoundEvent("bar_brawl");
    public static final ResourceKey<JukeboxSong> BAR_BRAWL_SONG = crateSong("bar_brawl");

    private static ResourceKey<JukeboxSong> crateSong(String name) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, name));
    }

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
