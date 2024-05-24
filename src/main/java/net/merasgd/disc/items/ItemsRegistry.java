package net.merasgd.disc.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.merasgd.disc.Disc;
import net.merasgd.disc.sound.SoundRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemsRegistry {
    public static final Item DISC_FRAGMENT = registerItem("disc_fragment", new Item(new FabricItemSettings().maxCount(1)));
    public static final Item DISC_HISTORIA = registerItem("disc_historia", new MusicDiscItem(7, SoundRegistry.HISTORIA, new FabricItemSettings().maxCount(1), 268));
    public static final Item DISC_KOKOA = registerItem("disc_kokoa", new MusicDiscItem(7, SoundRegistry.KOKOA, new FabricItemSettings().maxCount(1), 211));
    public static final Item DISC_ZENZEN = registerItem("disc_zenzen", new MusicDiscItem(7, SoundRegistry.ZENZEN, new FabricItemSettings().maxCount(1), 292));
    public static final Item DISC_SUZUME = registerItem("disc_suzume", new MusicDiscItem(7, SoundRegistry.SUZUME, new FabricItemSettings().maxCount(1), 237));

    public static final Item LYRIC_HISTORIA = registerItem("lyric_historia", new MusicDiscItem(0, SoundRegistry.EMPTY, new FabricItemSettings().maxCount(5), 0));
    public static final Item LYRIC_KOKOA = registerItem("lyric_kokoa", new MusicDiscItem(0, SoundRegistry.EMPTY, new FabricItemSettings().maxCount(5), 0));
    public static final Item LYRIC_ZENZEN = registerItem("lyric_zenzen", new MusicDiscItem(0, SoundRegistry.EMPTY, new FabricItemSettings().maxCount(5), 0));
    public static final Item LYRIC_SUZUME = registerItem("lyric_suzume", new MusicDiscItem(0, SoundRegistry.EMPTY, new FabricItemSettings().maxCount(5), 0));

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Disc.MOD_ID, name), item);
    }

    public static void registerAllItems() {
        Disc.LOGGER.info("Registring items...");
    }

}
