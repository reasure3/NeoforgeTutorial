package com.reasure.neoforge_tutorial.inventory.menu.custom;

import com.reasure.neoforge_tutorial.block.ModBlocks;
import com.reasure.neoforge_tutorial.block.entity.custom.PedestalBlockEntity;
import com.reasure.neoforge_tutorial.inventory.menu.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class PedestalMenu extends AbstractContainerMenu {
    public final PedestalBlockEntity blockEntity;
    private final Level level;

    private static final int PEDESTAL_FIRST_SLOT_INDEX = 0;
    private static final int PEDESTAL_LAST_SLOT_INDEX = 0;
    private static final int INVENTORY_FIRST_SLOT_INDEX = 1;
    private static final int HOTBAR_LAST_SLOT_INDEX = 36;

    // for registration factory
    public PedestalMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public PedestalMenu(int containerId, Inventory inv, BlockEntity blockEntity) {
        super(ModMenuTypes.PEDESTAL_MENU.get(), containerId);
        this.blockEntity = (PedestalBlockEntity) blockEntity;
        this.level = inv.player.level();

        // 슬롯 추가 (추가된 순서대로 0번부터 id 할당)
        // 0: Pedestal의 슬롯
        // 1 ~ 27: 인벤토리
        // 28 ~ 36: 핫바
        addSlot(new SlotItemHandler(this.blockEntity.inventory, 0, 80, 35));
        addPlayerInventory(inv, 8, 84);
        addPlayerHotbar(inv, 8, 142);
    }

    /**
     * Shift 클릭시 일어나는 함수
     *
     * @param player: 클릭한 플레이어
     * @param index:  클릭한 슬롯의 Id
     * @return 이동되기 전의 ItemStack 복사본. 만약 이동이 실패하면 ItemStack.EMPTY를 반환.
     */
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
            if (!moveItemStackTo(sourceStack, PEDESTAL_FIRST_SLOT_INDEX, PEDESTAL_LAST_SLOT_INDEX + 1, false)) {
                return ItemStack.EMPTY;
            }
            // check if the slot clicked is one of the pedestal container slots
        } else if (index >= PEDESTAL_FIRST_SLOT_INDEX && index <= PEDESTAL_LAST_SLOT_INDEX) {
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
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, ModBlocks.PEDESTAL.get());
    }

    private void addPlayerInventory(Inventory playerInventory, int x, int y) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                // j + i * 9 + 9: slotId (Pedestal의 slotId가 아니라 플레이어 인벤토리의 slotId)
                addSlot(new Slot(playerInventory, j + i * 9 + 9, x + j * 18, y + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory, int x, int y) {
        for (int i = 0; i < 9; ++i) {
            // i: slotId (Pedestal의 slotId가 아니라 플레이어 인벤토리의 slotId)
            addSlot(new Slot(playerInventory, i, x + i * 18, y));
        }
    }
}
