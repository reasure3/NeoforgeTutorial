package com.reasure.neoforge_tutorial.datagen;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import com.reasure.neoforge_tutorial.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.LinkedHashMap;

public class ModItemModelProvider extends ItemModelProvider {
    private static final LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();

    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, NeoforgeTutorial.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.BLACK_OPAL.get());
        basicItem(ModItems.RAW_BLACK_OPAL.get());
        basicItem(ModItems.CHAINSAW.get());
        basicItem(ModItems.TOMATO.get());
        basicItem(ModItems.FROSTFIRE_ICE.get());

        handheldItem(ModItems.BLACK_OPAL_SWORD);
        handheldItem(ModItems.BLACK_OPAL_PICKAXE);
        handheldItem(ModItems.BLACK_OPAL_SHOVEL);
        handheldItem(ModItems.BLACK_OPAL_AXE);
        handheldItem(ModItems.BLACK_OPAL_HOE);

        handheldItem(ModItems.BLACK_OPAL_PAXEL);

        handheldItem(ModItems.BLACK_OPAL_HAMMER);

        trimmedArmorItem(ModItems.BLACK_OPAL_HELMET);
        trimmedArmorItem(ModItems.BLACK_OPAL_CHESTPLATE);
        trimmedArmorItem(ModItems.BLACK_OPAL_LEGGINGS);
        trimmedArmorItem(ModItems.BLACK_OPAL_BOOTS);

        basicItem(ModItems.BLACK_OPAL_HORSE_ARMOR.get());
        basicItem(ModItems.KAUPEN_SMITHING_TEMPLATE.get());

        basicItem(ModItems.METAL_DETECTOR.get());
        dataTablet();

        bowItem(ModItems.KAUPEN_BOW);
    }

    private void handheldItem(DeferredItem<Item> item) {
        withExistingParent(item.getId().getPath(),
                ResourceLocation.withDefaultNamespace("item/handheld"))
                .texture("layer0", item.getId().withPrefix("item/"));
    }

    private void dataTablet() {
        ModelFile on = getBuilder("neoforge_tutorial:data_tablet_on")
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", "neoforge_tutorial:item/data_tablet");

        ItemModelBuilder base = getBuilder("neoforge_tutorial:data_tablet")
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", "neoforge_tutorial:item/data_tablet_off");

        base.override()
                .predicate(ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, "on"), 1f)
                .model(on)
                .end();
    }

    private void bowItem(DeferredItem<Item> item) {
        ModelFile[] pulling = new ModelFile[3];
        for (int i = 0; i < 3; i++) {
            ResourceLocation loc = item.getId().withPrefix("item/").withSuffix("_pulling_" + i);
            pulling[i] = getBuilder(loc.toString())
                    .parent(new ModelFile.UncheckedModelFile("item/generated"))
                    .texture("layer0", loc);
        }

        ItemModelBuilder base = getBuilder(item.getId().withPrefix("item/").toString())
                .parent(new ModelFile.UncheckedModelFile("neoforge_tutorial:item/bow_base"))
                .texture("layer0", item.getId().withPrefix("item/"));

        base.override()
                .predicate(mcLoc("pulling"), 1f)
                .model(pulling[0])
                .end();

        base.override()
                .predicate(mcLoc("pulling"), 1f)
                .predicate(mcLoc("pull"), 0.65f)
                .model(pulling[1])
                .end();

        base.override()
                .predicate(mcLoc("pulling"), 1f)
                .predicate(mcLoc("pull"), 0.9f)
                .model(pulling[2])
                .end();
    }

    // Shoutout to El_Redstoniano for making this
    private void trimmedArmorItem(DeferredItem<Item> itemDeferredItem) {
        final String MOD_ID = NeoforgeTutorial.MODID; // Change this to your mod id

        if (itemDeferredItem.get() instanceof ArmorItem armorItem) {
            trimMaterials.forEach((trimMaterial, value) -> {
                float trimValue = value;

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = armorItem.toString();
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = ResourceLocation.parse(armorItemPath);
                ResourceLocation trimResLoc = ResourceLocation.parse(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = ResourceLocation.parse(currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath())
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemDeferredItem.getId().getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc.getNamespace() + ":item/" + trimNameResLoc.getPath()))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                ResourceLocation.fromNamespaceAndPath(MOD_ID,
                                        "item/" + itemDeferredItem.getId().getPath()));
            });
        }
    }
}
