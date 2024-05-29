package net.merasgd.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.merasgd.datagen.provider.*;
import net.merasgd.disc.Disc;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class Provider implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(Model::new);
        pack.addProvider(Tag::new);
    }
    
    public class LyricTags {
        
        public static class Items {
            public static final TagKey<Item> LYRICS = createTag("disc_lyrics");

            private static TagKey<Item> createTag(String name) {
                return TagKey.of(RegistryKeys.ITEM, new Identifier(Disc.MOD_ID, name));
            }
        }

    }
}
