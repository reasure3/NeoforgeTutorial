package com.reasure.neoforge_tutorial.datagen;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import com.reasure.neoforge_tutorial.sound.ModSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class ModSoundDefinitionsProvider extends SoundDefinitionsProvider {
    public ModSoundDefinitionsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, NeoforgeTutorial.MODID, existingFileHelper);
    }

    @Override
    public void registerSounds() {
        add(ModSounds.CHAINSAW_CUT, SoundDefinition.definition()
                .with(sound(ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, "chainsaw_cut")))
                .subtitle("sounds." + NeoforgeTutorial.MODID + ".chainsaw_cut")
        );
        add(ModSounds.CHAINSAW_PULL, SoundDefinition.definition()
                .with(sound(ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, "chainsaw_pull")))
                .subtitle("sounds." + NeoforgeTutorial.MODID + ".chainsaw_cut")
        );
        add(ModSounds.MAGIC_BLOCK_BREAK, SoundDefinition.definition()
                .with(sound(ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, "magic_block_break")))
                .subtitle("subtitles.block.generic.break")
        );
        add(ModSounds.MAGIC_BLOCK_STEP, SoundDefinition.definition()
                .with(sound(ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, "magic_block_step")))
                .subtitle("subtitles.block.generic.step")
        );
        add(ModSounds.MAGIC_BLOCK_PLACE, SoundDefinition.definition()
                .with(sound(ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, "magic_block_place")))
                .subtitle("subtitles.block.generic.place")
        );
        add(ModSounds.MAGIC_BLOCK_HIT, SoundDefinition.definition()
                .with(sound(ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, "magic_block_hit")))
                .subtitle("subtitles.block.generic.hit")
        );
        add(ModSounds.MAGIC_BLOCK_FALL, SoundDefinition.definition()
                .with(sound(ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, "magic_block_fall")))
                .subtitle("subtitles.block.generic.fall")
        );
        add(ModSounds.BAR_BRAWL, SoundDefinition.definition()
                .with(sound(ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, "bar_brawl")).stream())
                .subtitle("jukebox_song." + NeoforgeTutorial.MODID + ".bar_brawl")
        );
    }
}
