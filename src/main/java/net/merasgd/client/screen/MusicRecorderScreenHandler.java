package net.merasgd.client.screen;

import net.merasgd.disc.entity.MusicRecorderEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class MusicRecorderScreenHandler extends ScreenHandler {

    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final MusicRecorderEntity blockEntity;

    public MusicRecorderScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()),
                new ArrayPropertyDelegate(3));
    }

    public MusicRecorderScreenHandler(int syncId, PlayerInventory inventory, BlockEntity blockEntity,
            PropertyDelegate arrayPropertyDelegate) {
        super(ScreenHandlers.MUSIC_RECORDER_SCREEN_HANDLER, syncId);
        checkSize(((Inventory) blockEntity), 3);
        this.inventory = ((Inventory) blockEntity);
        inventory.onOpen(inventory.player);
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = ((MusicRecorderEntity) blockEntity);

        this.addSlot(new Slot(this.inventory, 0, 56, 17));
        this.addSlot(new Slot(this.inventory, 1, 56, 53));
        this.addSlot(new RecorderSlot(inventory.player, this.inventory, 2, 116, 35));

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        addProperties(arrayPropertyDelegate);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity var1) {
        return this.inventory.canPlayerUse(var1);
    }

    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }

    public boolean isNormal() {
        return propertyDelegate.get(2) == 0;
    }

    public boolean isMusic() {
        return propertyDelegate.get(2) == 1;
    }

    public boolean isEmptying() {
        return propertyDelegate.get(2) == 2;
    }

    public int getScale() {
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1);  // Max Progress
        int progressArrowSize = 24; // This is the width in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    public class RecorderSlot extends Slot {

        private final PlayerEntity player;
        private int amount;

        public RecorderSlot(PlayerEntity entity, Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
            this.player = entity;
        }

        @Override
        public void onTakeItem(PlayerEntity player, ItemStack stack) {
            this.onCrafted(stack);
            super.onTakeItem(player, stack);
        }

        @Override
        public ItemStack takeStack(int amount) {
            if (this.hasStack()) {
                this.amount += Math.min(amount, this.getStack().getCount());
            }
            return super.takeStack(amount);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return false;
        }

        @Override
        protected void onCrafted(ItemStack stack, int amount) {
            this.amount += amount;
            this.onCrafted(stack);
        }

        @Override
        protected void onCrafted(ItemStack stack) {
            stack.onCraftByPlayer(this.player.getWorld(), this.player, this.amount);
            this.amount = 0;
        }
        
    }
}
