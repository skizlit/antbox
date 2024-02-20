package com.skizlitmods.antbox.datagen.loottables;

import java.util.Set;

import org.jetbrains.annotations.NotNull;

import com.skizlitmods.antbox.registries.minecraft.ModBlocks;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockLootTables extends BlockLootSubProvider {

    public ModBlockLootTables() {

        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        // this.dropSelf(ModBlocks.SAPPHIRE_BLOCK.get());
    }

    protected LootTable.Builder createCopperLikeOreDrops(final Block pBlock, final Item item) {

        return createSilkTouchDispatchTable(pBlock,
                                            this.applyExplosionDecay(pBlock,
                                                                     LootItem.lootTableItem(item)
                                                                             .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                                                             .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {

        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
