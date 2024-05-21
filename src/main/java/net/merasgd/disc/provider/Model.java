package net.merasgd.disc.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.merasgd.disc.Disc;
import net.merasgd.disc.items.ItemsRegistry;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class Model extends FabricModelProvider {
    public Model(FabricDataOutput out) {
        super(out);

        Disc.LOGGER.info("Loading Models...");
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ItemsRegistry.DISC_FRAGMENT, Models.GENERATED);
        itemModelGenerator.register(ItemsRegistry.DISC_HISTORIA, Models.GENERATED);
        itemModelGenerator.register(ItemsRegistry.DISC_KOKOA, Models.GENERATED);
    }

    
}
