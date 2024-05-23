package net.merasgd.disc.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.dynamic.Codecs;

import java.util.Iterator;

//From The Shapeless Recipe Serializer
public class MusicRecorderSerializer implements RecipeSerializer<MusicRecorderRecipe2> {
    public static final MusicRecorderSerializer INSTANCE = new MusicRecorderSerializer();
    public static final String ID = "music_recorder";

    private static final Codec<MusicRecorderRecipe2> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codecs.createStrictOptionalFieldCodec(Codec.STRING, "group", "").forGetter((recipe) -> {
            return recipe.group;
        }), CraftingRecipeCategory.CODEC.fieldOf("category").orElse(CraftingRecipeCategory.MISC).forGetter((recipe) -> {
            return recipe.category;
        }), ItemStack.RECIPE_RESULT_CODEC.fieldOf("result").forGetter((recipe) -> {
            return recipe.result;
        }), Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients").flatXmap((ingredients) -> {
            Ingredient[] ingredients2 = (Ingredient[])ingredients.stream().filter((ingredient) -> {
                return !ingredient.isEmpty();
            }).toArray((i) -> {
                return new Ingredient[i];
            });
            if (ingredients2.length == 0) {
                return DataResult.error(() -> {
                    return "No ingredients";
                });
            } else {
                return ingredients2.length > 9 ? DataResult.error(() -> {
                    return "Too many ingredients";
                }) : DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY, ingredients2));
            }
        }, DataResult::success).forGetter((recipe) -> {
            return recipe.ingredients;
        })).apply(instance, MusicRecorderRecipe2::new);
    });

    public MusicRecorderSerializer() {
    }

    public Codec<MusicRecorderRecipe2> codec() {
        return CODEC;
    }

    public MusicRecorderRecipe2 read(PacketByteBuf packetByteBuf) {
        String string = packetByteBuf.readString();
        CraftingRecipeCategory craftingRecipeCategory = (CraftingRecipeCategory) packetByteBuf.readEnumConstant(CraftingRecipeCategory.class);
        int i = packetByteBuf.readVarInt();
        DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i, Ingredient.EMPTY);
  
        for(int j = 0; j < defaultedList.size(); ++j) {
           defaultedList.set(j, Ingredient.fromPacket(packetByteBuf));
        }
  
        ItemStack itemStack = packetByteBuf.readItemStack();
        return new MusicRecorderRecipe2(string, craftingRecipeCategory, itemStack, defaultedList);
    }

    public void write(PacketByteBuf packetByteBuf, MusicRecorderRecipe2 recipe) {
        packetByteBuf.writeString(recipe.group);
        packetByteBuf.writeEnumConstant(recipe.category);
        packetByteBuf.writeVarInt(recipe.ingredients.size());
        Iterator<?> iterator = recipe.ingredients.iterator();
    
        while(iterator.hasNext()) {
           Ingredient ingredient = (Ingredient) iterator.next();
           ingredient.write(packetByteBuf);
        }
    
        packetByteBuf.writeItemStack(recipe.result);
    }
}
