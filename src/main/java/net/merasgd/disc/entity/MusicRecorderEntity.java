package net.merasgd.disc.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.merasgd.disc.items.ItemsRegistry;
import net.merasgd.disc.recipe.MusicRecorderRecipe;
import net.merasgd.client.screen.MusicRecorderScreenHandler;
import net.merasgd.datagen.Provider;
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
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;

public class MusicRecorderEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {

    private final DefaultedList<ItemStack> container = DefaultedList.ofSize(3, ItemStack.EMPTY);

    private static final int INPUT = 0;
    private static final int MATERIAL = 1;
    private static final int OUTPUT = 2;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 100;

    private int normalMaxProgress = 100;
    private int lyricMaxProgress = 200;
    private int emptyMaxProgress = 50;

    private int typeCraft = 0;

    public MusicRecorderEntity(BlockPos pos, BlockState state) {
        super(EntityRegistry.MUSIC_RECORDER_ENTITY_TYPE, pos, state);

        this.propertyDelegate = new PropertyDelegate() {

            @Override
            public int get(int var1) {
                return switch (var1) {
                    case 0 -> MusicRecorderEntity.this.progress;
                    case 1 -> MusicRecorderEntity.this.maxProgress;
                    case 2 -> MusicRecorderEntity.this.typeCraft;
                    default -> 0;
                };
            }

            @Override
            public void set(int var1, int var2) {
                switch (var1) {
                    case 0 -> MusicRecorderEntity.this.progress = var2;
                    case 1 -> MusicRecorderEntity.this.maxProgress = var2;
                    case 2 -> MusicRecorderEntity.this.typeCraft = var2;
                };
            }

            @Override
            public int size() {
                return 3;
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
    public void markDirty() {
        world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        super.markDirty();
    }

    public ItemStack getRenderItem() {
        if(this.getStack(OUTPUT).isEmpty()) {
            return this.getStack(INPUT);
        } else {
            return this.getStack(OUTPUT);
        }
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

    private void editMaxProgress(Optional<RecipeEntry<MusicRecorderRecipe>> recipe) {
        ItemStack result = recipe.get().value().getResult(null);

        if(result.isIn(Provider.LyricTags.Items.LYRICS)) {
            this.propertyDelegate.set(1, lyricMaxProgress);
            this.maxProgress = lyricMaxProgress;

            this.propertyDelegate.set(2, 1);
            this.typeCraft = 1;
        } else if(result.getItem() == ItemsRegistry.DISC_FRAGMENT) {
            this.propertyDelegate.set(1, emptyMaxProgress);
            this.maxProgress = emptyMaxProgress;

            this.propertyDelegate.set(2, 2);
            this.typeCraft = 2;
        } else {
            this.propertyDelegate.set(1, normalMaxProgress);
            this.maxProgress = normalMaxProgress;

            this.propertyDelegate.set(2, 0);
            this.typeCraft = 0;
        }
    }

    private boolean hasRecipe() {
        Optional<RecipeEntry<MusicRecorderRecipe>> recipe = getCurrentRecipe();

        if(recipe.isPresent()) {
            this.editMaxProgress(recipe);
        }

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

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

}
