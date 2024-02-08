package com.skizlitmods.basemod.datagen;

import java.util.concurrent.CompletableFuture;

import com.skizlitmods.basemod.BaseMod;

import com.skizlitmods.basemod.datagen.blockstats.ModBlockStateProvider;
import com.skizlitmods.basemod.datagen.items.ModItemModelProvider;
import com.skizlitmods.basemod.datagen.loottables.ModLootTableProvider;
import com.skizlitmods.basemod.datagen.recipes.ModRecipeProvider;
import com.skizlitmods.basemod.datagen.tags.ModBlockTagProvider;
import com.skizlitmods.basemod.datagen.tags.ModItemTagProvider;
import com.skizlitmods.basemod.utils.exceptions.InstantiabilityException;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BaseMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    private DataGenerators() throws InstantiabilityException {

        throw new InstantiabilityException(this.getClass().getName());
    }

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {

        final DataGenerator generator = event.getGenerator();
        final PackOutput packOutput = generator.getPackOutput();
        final ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        final CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput));
        generator.addProvider(event.includeServer(), ModLootTableProvider.create(packOutput));

        generator.addProvider(event.includeClient(), new ModBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, existingFileHelper));

        ModBlockTagProvider blockTagGenerator = generator.addProvider(event.includeServer(), new ModBlockTagProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModItemTagProvider(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), existingFileHelper));
    }
}
