package com.lensmods.lenssgs;

import com.lensmods.lenssgs.client.ADSHandler;
import com.lensmods.lenssgs.client.ClientFireHandler;
import com.lensmods.lenssgs.client.GunTooltipHandler;
import com.lensmods.lenssgs.client.render.CustomRenderHandler;
import com.lensmods.lenssgs.core.data.AllowedParts;
import com.lensmods.lenssgs.core.data.MaterialProvider;
import com.lensmods.lenssgs.core.entity.render.GenericProjRender;
import com.lensmods.lenssgs.core.util.KeyManager;
import com.lensmods.lenssgs.datagen.*;
import com.lensmods.lenssgs.init.*;
import com.lensmods.lenssgs.networking.PacketReg;
import com.mojang.logging.LogUtils;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.util.Random;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(LensSGS.MODID)
public class LensSGS
{
    public static final String MODID = "lenssgs";
    public static final Random RANDY = new Random();
    public static final RandomSource STORYTELLER = RandomSource.create();
    // Directly reference a slf4j logger
    public static final Logger L3NLOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    // public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));

    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.lenssgs")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> LenItems.GUNPRINTER_PAPER.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(LenItems.GUNPRINTER_PAPER.get());
                output.accept(LenItems.VOIDMETAL.get());
                output.accept(LenItems.WYRMSTEEL.get());
                output.accept(LenItems.BLITZGOLD.get());
                output.accept(LenItems.HOCOFE.get());
                output.accept(LenItems.NURUKUKAN.get());
            }).build());

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public LensSGS(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        //Keep up here
        LenDataComponents.DATA_REG.register(modEventBus);
        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        LenItems.ITEMS.register(modEventBus);
        //Register The Entity Thing
        LenEnts.ENTITIES.register(modEventBus);
        LenEnts.EFFECTS.register(modEventBus);
        LenEnts.POTIONS.register(modEventBus);
        LenSounds.SOUNDS.register(modEventBus);
        //AHHHH recipes.
        LenRecipes.RECIPE_SERIALIZERS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);


        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);


        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, LenConfig.SPEC);
        modEventBus.addListener(LenConfig::onLoad);

        modEventBus.addListener(PacketReg::register);
        modEventBus.addListener(this::gatherData);
        modEventBus.addListener(LenDataReg::register);

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        L3NLOGGER.info("I miss when mods were mostly debug hellos in console, don't you?");
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        L3NLOGGER.info("Why did you download this mod? Guns are cool, but Len's code is garbage!");
    }

    public void gatherData(GatherDataEvent event) {
        L3NLOGGER.info("Hello from GatherData! Server:{}", event.includeServer());
        event.getGenerator().addProvider(event.includeClient(), (DataProvider.Factory<LenLang>) out -> new LenLang(out,LensSGS.MODID,"en_us"));
        event.getGenerator().addProvider(event.includeServer(), (DataProvider.Factory<LenDamageGen>) out -> new LenDamageGen(out,event.getLookupProvider(),event.getExistingFileHelper()));
        BlockTagsProvider BTP = new LenBlockTagGen(event.getGenerator().getPackOutput(),event.getLookupProvider(),LensSGS.MODID,event.getExistingFileHelper()); //This is literally needed for item tags and I hate it.
        event.getGenerator().addProvider(event.includeServer(),BTP);
        event.getGenerator().addProvider(event.includeServer(), (DataProvider.Factory<LenTagKeys>) out->
                new LenTagKeys(out,event.getLookupProvider(),BTP.contentsGetter()));
        event.getGenerator().addProvider(event.includeServer(), (DataProvider.Factory<MaterialProvider>) out ->
                new MaterialProvider(out,LenMaterialGen.DEFAULT_MATERIALS_BUILDER, event.getLookupProvider()));
        event.getGenerator().addProvider((event.includeServer()),(DataProvider.Factory<LenRecipeGen>) out -> new LenRecipeGen(out,event.getLookupProvider()));
        event.getGenerator().addProvider(true, (DataProvider.Factory<LenModelGen>) out -> new LenModelGen(out, MODID,event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, (DataProvider.Factory<LenSoundGen>) out -> new LenSoundGen(out,event.getExistingFileHelper()));
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {


        @SubscribeEvent
        public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
            event.register(KeyManager.CYCLE_BACK.get());
            event.register(KeyManager.CYCLE_NEXT.get());
            event.register(KeyManager.DISPLAY_STATS.get());
            event.register(KeyManager.DISPLAY_TRAITS.get());
            event.register(KeyManager.DISPLAY_CONSTRUCTION.get());
            event.register(KeyManager.RELOAD.get());
        }
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            L3NLOGGER.info("You better appreciate those gun models, they were incredibly difficult to make!");
            NeoForge.EVENT_BUS.register(ClientFireHandler.get());
            NeoForge.EVENT_BUS.register(ADSHandler.get());//Might need to move this if I want aiming to do anything, TODO?
            NeoForge.EVENT_BUS.register(GunTooltipHandler.INSTANCE);
        }
        @SubscribeEvent
        public static void registerEntRenders(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(LenEnts.GENERIC_PROJ.get(), GenericProjRender::new);
        }
        @SubscribeEvent
        public static void registerGeometryLoaders(ModelEvent.RegisterGeometryLoaders event) {
            event.register(CustomRenderHandler.ID, CustomRenderHandler.INSTANCE);
        }
        //Model reg
        @SubscribeEvent
        public static void registerAdditional(ModelEvent.RegisterAdditional e) {
            for(String s : AllowedParts.ANY_GUN_SUB_PART) {
                e.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MODID,"gunparts/"+s)));
            }
            e.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MODID,"gunparts/action_automatic_bullpup")));
            e.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MODID,"gunparts/action_manual_bullpup")));
            e.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MODID,"gunparts/action_single_bullpup")));
            e.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MODID,"gunparts/magazine_belt_bullpup")));
            e.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MODID,"gunparts/magazine_short_bullpup")));
            e.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MODID,"gunparts/magazine_normal_bullpup")));
            e.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MODID,"gunparts/magazine_extended_bullpup")));
            e.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MODID,"gunparts/stock_full_bullpup")));
            e.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MODID,"gunparts/stock_short_bullpup")));
            e.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MODID,"gunparts/receiver_bullpup_bullpup")));
            e.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MODID,"gunparts/scope_irons_bullpup")));
            e.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MODID,"gunparts/scope_short_bullpup")));
            e.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MODID,"gunparts/scope_medium_bullpup")));
            e.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MODID,"gunparts/scope_long_bullpup")));
            e.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MODID,"ammo/box")));
        }
    }
}
