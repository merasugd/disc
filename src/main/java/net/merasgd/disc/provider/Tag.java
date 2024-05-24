package net.merasgd.disc.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.merasgd.disc.Disc;
import net.merasgd.disc.Provider;
import net.merasgd.disc.items.ItemsRegistry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class Tag extends FabricTagProvider.ItemTagProvider {
    public Tag(FabricDataOutput out, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(out, completableFuture);

        Disc.LOGGER.info("Loading Item Tags...");
    }

    @Override
    protected void configure(WrapperLookup arg) {
        getOrCreateTagBuilder(ItemTags.MUSIC_DISCS).add(ItemsRegistry.DISC_HISTORIA);
        getOrCreateTagBuilder(ItemTags.MUSIC_DISCS).add(ItemsRegistry.DISC_KOKOA);
        getOrCreateTagBuilder(ItemTags.MUSIC_DISCS).add(ItemsRegistry.DISC_ZENZEN);
        getOrCreateTagBuilder(ItemTags.MUSIC_DISCS).add(ItemsRegistry.DISC_SUZUME);

        getOrCreateTagBuilder(Provider.LyricTags.Items.LYRICS).add(ItemsRegistry.LYRIC_HISTORIA);
        getOrCreateTagBuilder(Provider.LyricTags.Items.LYRICS).add(ItemsRegistry.LYRIC_KOKOA);
        getOrCreateTagBuilder(Provider.LyricTags.Items.LYRICS).add(ItemsRegistry.LYRIC_ZENZEN);
        getOrCreateTagBuilder(Provider.LyricTags.Items.LYRICS).add(ItemsRegistry.LYRIC_SUZUME);
    }
}
