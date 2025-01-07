package com.reasure.neoforge_tutorial.inventory.menu.custom;

import com.reasure.neoforge_tutorial.block.ModBlocks;
import com.reasure.neoforge_tutorial.block.entity.custom.CrystallizerBlockEntity;
import com.reasure.neoforge_tutorial.inventory.menu.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class CrystallizerMenu extends AbstractContainerMenu {
    public final CrystallizerBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    private static final int FLUID_ITEM_SLOT_INDEX = 0;
    private static final int INPUT_SLOT_SLOT_INDEX = 1;
    private static final int OUTPUT_SLOT_INDEX = 2;
    private static final int ENERGY_ITEM_SLOT_INDEX = 3;
    private static final int INVENTORY_FIRST_SLOT_INDEX = 4;
    private static final int HOTBAR_LAST_SLOT_INDEX = 39;

    public CrystallizerMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public CrystallizerMenu(int containerId, Inventory inv, BlockEntity blockEntity, ContainerData data) {
        super(ModMenuTypes.CRYSTALLIZER_MENU.get(), containerId);
        this.blockEntity = (CrystallizerBlockEntity) blockEntity;
        this.level = inv.player.level();
        this.data = data;

        addSlot(new SlotItemHandler(this.blockEntity.inventory, 0, 8, 62)); // fluid
        addSlot(new SlotItemHandler(this.blockEntity.inventory, 1, 54, 34)); // input
        addSlot(new SlotItemHandler(this.blockEntity.inventory, 2, 152, 62)); // energy
        addSlot(new SlotItemHandler(this.blockEntity.inventory, 3, 104, 34)); // output

        addPlayerInventory(inv, 8, 84);
        addPlayerHotbar(inv, 8, 142);

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledArrowProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int arrowPixelSize = 24;
        return maxProgress != 0 && progress != 0 ? arrowPixelSize * progress / maxProgress : 0;
    }

    public int getScaledCrystalProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int crystalPixelSize = 16;
        return maxProgress != 0 && progress != 0 ? crystalPixelSize * progress / maxProgress : 0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
        // sourceStack: 실제로 이동이 일어나는 중.
        ItemStack sourceStack = sourceSlot.getItem();
        // copyOfSourceStack: 이동이 일어나기 전 복사본
        ItemStack copyOfSourceStack = sourceStack.copy();

        // check if the slot clicked is one of the vanilla container slots
        if (index >= INVENTORY_FIRST_SLOT_INDEX && index <= HOTBAR_LAST_SLOT_INDEX) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            // moveItemStackTo(ItemStack stack, int startIndex(inclusive), int endIndex(exclusive), boolean reverseDirection)
            if (!moveItemStackTo(sourceStack, FLUID_ITEM_SLOT_INDEX, ENERGY_ITEM_SLOT_INDEX + 1, false)) {
                return ItemStack.EMPTY;
            }
            // check if the slot clicked is one of the pedestal container slots
        } else if (index >= FLUID_ITEM_SLOT_INDEX && index <= ENERGY_ITEM_SLOT_INDEX) {
            if (!moveItemStackTo(sourceStack, INVENTORY_FIRST_SLOT_INDEX, HOTBAR_LAST_SLOT_INDEX + 1, true)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }

        if (sourceStack.isEmpty()) {
            sourceSlot.setByPlayer(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        if (sourceStack.getCount() == copyOfSourceStack.getCount()) {
            return ItemStack.EMPTY;
        }

        sourceSlot.onTake(player, sourceStack);

        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, ModBlocks.CRYSTALLIZER.get());
    }

    private void addPlayerInventory(Inventory playerInventory, int x, int y) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlot(new Slot(playerInventory, j + i * 9 + 9, x + j * 18, y + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory, int x, int y) {
        for (int i = 0; i < 9; ++i) {
            addSlot(new Slot(playerInventory, i, x + i * 18, y));
        }
    }
}
