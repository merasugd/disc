package net.merasgd.disc.recipe;

import net.merasgd.disc.Disc;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RecipeRegistry {
    
    public static void registerRecipes() {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(Disc.MOD_ID, MusicRecorderRecipe.Serializer.ID), MusicRecorderRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(Disc.MOD_ID, MusicRecorderRecipe.Type.id), MusicRecorderRecipe.Type.INSTANCE);
    }

}
