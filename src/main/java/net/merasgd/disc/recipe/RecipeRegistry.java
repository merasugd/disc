package net.merasgd.disc.recipe;

import net.merasgd.disc.Disc;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RecipeRegistry {

    public static final RecipeSerializer<MusicRecorderRecipe> MUSIC_RECORDER_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(Disc.MOD_ID, MusicRecorderRecipe.Serializer.ID), MusicRecorderRecipe.Serializer.INSTANCE);
    public static final RecipeType<MusicRecorderRecipe> MUSIC_RECORDER_TYPE = Registry.register(Registries.RECIPE_TYPE, new Identifier(Disc.MOD_ID, MusicRecorderRecipe.Type.ID), MusicRecorderRecipe.Type.INSTANCE);
    
    public static void registerRecipes() {
    }

}
