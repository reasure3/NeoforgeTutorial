package com.reasure.neoforge_tutorial.datagen;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import com.reasure.neoforge_tutorial.sound.ModSounds;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.JsonCodecProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.concurrent.CompletableFuture;

public class ModMusicDiscProvider extends JsonCodecProvider<JukeboxSong> {
    public ModMusicDiscProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, PackOutput.Target.DATA_PACK, "jukebox_song", PackType.SERVER_DATA, JukeboxSong.DIRECT_CODEC, lookupProvider, NeoforgeTutorial.MODID, existingFileHelper);
    }

    @Override
    protected void gather() {
        song(ModSounds.BAR_BRAWL, 162);
    }

    private void song(DeferredHolder<SoundEvent, SoundEvent> sound, float second) {
        JukeboxSong music = new JukeboxSong(
                Holder.direct(sound.get()),
                Component.translatable("item." + modid + "." + sound.getId().getPath() + "_music_disc.desc"),
                second, 15);
        unconditional(sound.getId(), music);
    }
}
