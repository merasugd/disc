package net.merasgd.disc.sound;

import net.merasgd.disc.Disc;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundRegistry {

    public static final SoundEvent HISTORIA = registerEvent("historia_song");

    public static SoundEvent registerEvent(String name) {
        Identifier id = new Identifier(Disc.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSound() {
        Disc.LOGGER.info("Registering Music..");
    }
}
