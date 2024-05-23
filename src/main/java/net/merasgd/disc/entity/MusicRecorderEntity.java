package net.merasgd.disc.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.merasgd.disc.recipe.MusicRecorderRecipe;
import net.merasgd.client.screen.MusicRecorderScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class MusicRecorderEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {

    private final DefaultedList<ItemStack> container = DefaultedList.ofSize(3, ItemStack.EMPTY);

    private static final int INPUT = 0;
    private static final int MATERIAL = 1;
    private static final int OUTPUT = 2;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 100;

    public MusicRecorderEntity(BlockPos pos, BlockState state) {
        super(EntityRegistry.MUSIC_RECORDER_ENTITY_TYPE, pos, state);

        this.propertyDelegate = new PropertyDelegate() {

            @Override
            public int get(int var1) {
                return switch (var1) {
                    case 0 -> MusicRecorderEntity.this.progress;
                    case 1 -> MusicRecorderEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int var1, int var2) {
                switch (var1) {
                    case 0 -> MusicRecorderEntity.this.progress = var2;
                    case 1 -> MusicRecorderEntity.this.maxProgress = var2;
                };
            }

            @Override
            public int size() {
                return 2;
            }
            
        };
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("gui.disc.music_recorder");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, container);
        nbt.putInt("disc.music_recorder.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, container);
        progress = nbt.getInt("disc.music_recorder.progress");
    }

    @Override
    public ScreenHandler createMenu(int var1, PlayerInventory var2, PlayerEntity var3) {
        return new MusicRecorderScreenHandler(var1, var2, this, this.propertyDelegate);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return container;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

	public void tick(World world, BlockPos pos, BlockState state) {
		if(world.isClient) return;
        
        if(outputItemCheck()) {
            if(this.hasRecipe()) {
                this.increaseProgress();
                markDirty(world, pos, state);

                if(this.isFinished()) {
                    this.giveItem();
                    this.resetProgress();
                }
            } else {
                this.resetProgress();
            }
        } else {
            this.resetProgress();
            markDirty(world, pos, state);
        }
	}

    private void resetProgress() {
        this.progress = 0;
    }

    private boolean isFinished() {
        return progress >= maxProgress;
    }

    private void increaseProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        Optional<RecipeEntry<MusicRecorderRecipe>> recipe = getCurrentRecipe();

        return recipe.isPresent() && outputSlotAvailableCount(recipe.get().value().getResult(null)) && outputSlotAvailable(recipe.get().value().getResult(null).getItem());
    }

    private Optional<RecipeEntry<MusicRecorderRecipe>> getCurrentRecipe() {
        SimpleInventory inv = new SimpleInventory(this.size());
        for(int i = 0; i < this.size(); i++) {
            inv.setStack(i, this.getStack(i));
        }

        return getWorld().getRecipeManager().getFirstMatch(MusicRecorderRecipe.Type.INSTANCE, inv, getWorld());
    }

    private boolean outputSlotAvailable(Item item) {
        return this.getStack(OUTPUT).getItem() == item || this.getStack(OUTPUT).isEmpty();
    }

    private boolean outputSlotAvailableCount(ItemStack result) {
        return this.getStack(OUTPUT).getCount() + result.getCount() <= this.getStack(OUTPUT).getMaxCount();
    }

    private void giveItem() {
        Optional<RecipeEntry<MusicRecorderRecipe>> recipe = getCurrentRecipe();

        this.removeStack(INPUT, 1);
        this.removeStack(MATERIAL, 1);

        this.setStack(OUTPUT, new ItemStack(recipe.get().value().getResult(null).getItem(), getStack(OUTPUT).getCount() + recipe.get().value().getResult(null).getCount()));
    }

    private boolean outputItemCheck() {
        return this.getStack(OUTPUT).isEmpty() || this.getStack(OUTPUT).getCount() < this.getStack(OUTPUT).getMaxCount();
    }

}
