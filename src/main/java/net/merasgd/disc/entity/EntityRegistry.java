package net.merasgd.disc.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.merasgd.disc.Disc;
import net.merasgd.disc.block.BlockRegistry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

@SuppressWarnings("deprecation")
public class EntityRegistry {

    public static final BlockEntityType<MusicRecorderEntity> MUSIC_RECORDER_ENTITY_TYPE = 
        Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Disc.MOD_ID, "music_recorder_entity"), 
            FabricBlockEntityTypeBuilder.create(MusicRecorderEntity::new, BlockRegistry.MUSIC_RECORDER).build());

    public static void registerEntity() {
        Disc.LOGGER.info("Registering Entity...");
    }
}