// Source code is decompiled from a .class file using FernFlower decompiler.
package net.merasgd.disc.recipe;

import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class MusicRecorderRecipe2 implements Recipe<SimpleInventory> {
    final String group;
    final CraftingRecipeCategory category;
    final ItemStack result;
    final DefaultedList<Ingredient> ingredients;

    public static final RecipeSerializer<MusicRecorderRecipe2> Serializer = MusicRecorderSerializer.INSTANCE;

    public MusicRecorderRecipe2(String group, CraftingRecipeCategory category, ItemStack result, DefaultedList<Ingredient> ingredients) {
        this.group = group;
        this.category = category;
        this.result = result;
        this.ingredients = ingredients;
    }

    public RecipeSerializer<?> getSerializer() {
        return Serializer;
    }

    public String getGroup() {
        return this.group;
    }

    public CraftingRecipeCategory getCategory() {
        return this.category;
    }

    public ItemStack getResult(DynamicRegistryManager registryManager) {
        return this.result;
    }

    public DefaultedList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public boolean matches(SimpleInventory input, World world) {
        RecipeMatcher recipeMatcher = new RecipeMatcher();
        int i = 0;
  
        for(int j = 0; j < input.size(); ++j) {
           ItemStack itemStack = input.getStack(j);
           if (!itemStack.isEmpty()) {
              ++i;
              recipeMatcher.addInput(itemStack, 1);
           }
        }
  
        return i == this.ingredients.size() && recipeMatcher.match(this, (IntList)null);
    }

    public ItemStack craft(SimpleInventory input, DynamicRegistryManager dynamicRegistryManager) {
        return this.result.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<MusicRecorderRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "music_recorder";
    }
}
