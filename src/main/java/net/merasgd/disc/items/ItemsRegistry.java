package net.merasgd.disc.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.merasgd.disc.Disc;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemsRegistry {
    public static final Item DISC_FRAGMENT = registerItem("disc_fragment", new Item(new FabricItemSettings().maxCount(1)));

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Disc.MOD_ID, name), item);
    }

    public static void registerAllItems() {
        Disc.LOGGER.info("Registring items...");
    }

}
