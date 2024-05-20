package net.merasgd.disc.items;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.merasgd.disc.Disc;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class DiscGroup {
    
    public static final ItemGroup Group = Registry.register(Registries.ITEM_GROUP, new Identifier(Disc.MOD_ID, "disc_group"), 
    FabricItemGroup.builder().displayName(Text.translatable("item.group.disc")).icon(() -> new ItemStack(ItemsRegistry.DISC_FRAGMENT)).entries((display, entries) -> {

        entries.add(ItemsRegistry.DISC_FRAGMENT);

    }).build());

    public static void registerGroup() {
        Disc.LOGGER.info("Registering Item Group...");
        
    }
}
