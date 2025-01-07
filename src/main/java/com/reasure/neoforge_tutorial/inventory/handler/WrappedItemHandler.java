package com.reasure.neoforge_tutorial.inventory.handler;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

import java.util.Objects;

/**
 * A handler that wraps a subset of slots from an existing {@link IItemHandlerModifiable}.
 * <p>
 * This handler provides access to a specific range of slots from the original handler
 * and maps the slot IDs appropriately. For example, if the range is from slot 3 to 5
 * in the original handler, these slots are exposed as slots 0 to 2 in the wrapped handler.
 */
public class WrappedItemHandler implements IItemHandlerModifiable {
    private final int fromSlotId;
    private final int toSlotId;
    private final IItemHandlerModifiable handler;

    public WrappedItemHandler(IItemHandlerModifiable handler, int from, int to) {
        Objects.checkFromToIndex(from, to, handler.getSlots());
        this.fromSlotId = from;
        this.toSlotId = to;
        this.handler = handler;
    }

    /**
     * Converts the slot ID of the wrapped handler to the corresponding slot ID in the original handler.
     *
     * @param wrappedSlotId the slot ID in the wrapped handler
     * @return the corresponding slot ID in the original handler
     */
    private int convertToOriginalSlotId(int wrappedSlotId) {
        if (wrappedSlotId < 0 || wrappedSlotId >= getSlots()) {
            throw new IndexOutOfBoundsException("Wrapped slot ID out of range: " + wrappedSlotId);
        }
        return fromSlotId + wrappedSlotId;
    }

    /**
     * Converts the slot ID of the original handler to the corresponding slot ID in the wrapped handler.
     *
     * @param originalSlotId the slot ID in the original handler
     * @return the corresponding slot ID in the wrapped handler
     */
    private int mapOriginalToWrappedSlotId(int originalSlotId) {
        if (originalSlotId < fromSlotId || originalSlotId > toSlotId) {
            throw new IndexOutOfBoundsException("Original slot ID out of range: " + originalSlotId);
        }
        return originalSlotId - fromSlotId;
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        handler.setStackInSlot(convertToOriginalSlotId(slot), stack);
    }

    @Override
    public int getSlots() {
        return toSlotId - fromSlotId + 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return handler.getStackInSlot(convertToOriginalSlotId(slot));
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return handler.insertItem(convertToOriginalSlotId(slot), stack, simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return handler.extractItem(convertToOriginalSlotId(slot), amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        return handler.getSlotLimit(convertToOriginalSlotId(slot));
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return handler.isItemValid(convertToOriginalSlotId(slot), stack);
    }
}
