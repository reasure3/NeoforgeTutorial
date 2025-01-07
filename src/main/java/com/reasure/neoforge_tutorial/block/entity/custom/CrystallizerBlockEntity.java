package com.reasure.neoforge_tutorial.block.entity.custom;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import com.reasure.neoforge_tutorial.block.custom.CrystallizerBlock;
import com.reasure.neoforge_tutorial.block.entity.ModBlockEntities;
import com.reasure.neoforge_tutorial.block.entity.menu.custom.CrystallizerMenu;
import com.reasure.neoforge_tutorial.fluid.ModFluidTypes;
import com.reasure.neoforge_tutorial.recipe.CrystallizingRecipe;
import com.reasure.neoforge_tutorial.recipe.CrystallizingRecipeInput;
import com.reasure.neoforge_tutorial.recipe.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CrystallizerBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler inventory = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return switch (slot) {
                case FLUID_ITEM_SLOT ->
                        FluidUtil.getFluidContained(stack).map(fluid -> fluid.getFluidType() == ModFluidTypes.BLACK_OPAL_WATER_FLUID_TYPE.get()).orElse(false);
                case INPUT_SLOT -> true;
                case OUTPUT_SLOT -> false;
                case ENERGY_ITEM_SLOT -> stack.getCapability(Capabilities.EnergyStorage.ITEM) != null;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private static final int FLUID_ITEM_SLOT = 0;
    private static final int INPUT_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;
    private static final int ENERGY_ITEM_SLOT = 3;

    private final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;
    private final int DEFAULT_MAX_PROGRESS = 72;

    public CrystallizerBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.CRYSTALLIZER_BE.get(), pos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> CrystallizerBlockEntity.this.progress;
                    case 1 -> CrystallizerBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0:
                        CrystallizerBlockEntity.this.progress = value;
                    case 1:
                        CrystallizerBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container." + NeoforgeTutorial.MODID + ".crystallizer");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new CrystallizerMenu(containerId, playerInventory, this, data);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.putInt("crystallizer.progress", progress);
        tag.putInt("crystallizer.max_progress", maxProgress);
        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        this.progress = tag.getInt("crystallizer.progress");
        this.maxProgress = tag.getInt("crystallizer.max_progress");
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for (int i = 0; i < inventory.getSlots(); i++) {
            inv.setItem(i, inventory.getStackInSlot(i));
        }
        Containers.dropContents(level, worldPosition, inv);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (hasRecipe() && isOutputSlotEmptyOrReceivable()) {
            setMaxCraftingProgress();
            increaseCraftingProgress();
            if (!state.getValue(CrystallizerBlock.LIT)) {
                level.setBlockAndUpdate(pos, state.setValue(CrystallizerBlock.LIT, true));
            }
            setChanged(level, pos, state);

            if (hasCraftingFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            if (state.getValue(CrystallizerBlock.LIT)) {
                level.setBlockAndUpdate(pos, state.setValue(CrystallizerBlock.LIT, false));
            }
            resetProgress();
        }
    }

    private void setMaxCraftingProgress() {
        Optional<RecipeHolder<CrystallizingRecipe>> recipe = getCurrentRecipe();
        this.maxProgress = recipe.get().value().time();
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = DEFAULT_MAX_PROGRESS;
    }

    private void craftItem() {
        Optional<RecipeHolder<CrystallizingRecipe>> recipe = getCurrentRecipe();

        ItemStack output = recipe.get().value().getResultItem(null);

        inventory.extractItem(INPUT_SLOT, 1, false);
        inventory.setStackInSlot(OUTPUT_SLOT, new ItemStack(output.getItem(), inventory.getStackInSlot(OUTPUT_SLOT).getCount() + output.getCount()));
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProgress() {
        this.progress++;
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return this.inventory.getStackInSlot(OUTPUT_SLOT).isEmpty() ||
                this.inventory.getStackInSlot(OUTPUT_SLOT).getCount() < this.inventory.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<CrystallizingRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return false;

        ItemStack output = recipe.get().value().getResultItem(null);

        return canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output);
    }

    private Optional<RecipeHolder<CrystallizingRecipe>> getCurrentRecipe() {
        return level.getRecipeManager()
                .getRecipeFor(ModRecipes.CRYSTALLIZING_RECIPE.get(), new CrystallizingRecipeInput(inventory.getStackInSlot(INPUT_SLOT)), level);
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return inventory.getStackInSlot(OUTPUT_SLOT).isEmpty() ||
                inventory.getStackInSlot(OUTPUT_SLOT).is(output.getItem());
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = inventory.getStackInSlot(OUTPUT_SLOT).isEmpty() ? 64 : inventory.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
        int currentCount = inventory.getStackInSlot(OUTPUT_SLOT).getCount();

        return maxCount >= currentCount + count;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }
}
