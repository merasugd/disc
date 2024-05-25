package net.merasgd.disc.sound;

import net.merasgd.disc.Disc;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundRegistry {

    public static final SoundEvent HISTORIA = registerEvent("historia_song");
    public static final SoundEvent KOKOA = registerEvent("kokoa_song");
    public static final SoundEvent ZENZEN = registerEvent("zenzen_song");
    public static final SoundEvent SUZUME = registerEvent("suzume_song");
    public static final SoundEvent MARIA_CLARA = registerEvent("mariaclara_song");

    public static final SoundEvent EMPTY = registerEvent("empty");

    public static SoundEvent registerEvent(String name) {
        Identifier id = new Identifier(Disc.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSound() {
        Disc.LOGGER.info("Registering Music..");
    }
}
