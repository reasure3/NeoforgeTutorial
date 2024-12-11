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
    }
}
