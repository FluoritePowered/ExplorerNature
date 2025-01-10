package moe.kotori.explorernature;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = ExplorerNature.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ENDER_LEAD_DENYS = BUILDER.comment("配置末影拴绳不可栓走的实体ResourceLocation").defineListAllowEmpty("ender_lead_denys", List.of("minecraft:pig"), Config::validateEntityName);
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ENDER_LEAD_ALLOWS = BUILDER.comment("配置更多末影拴绳可拴走的动物外实体ResourceLocation").defineListAllowEmpty("ender_lead_allows", List.of("minecraft:glow_squid"), Config::validateEntityName);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static Set<EntityType<?>> denyTypes;
    public static Set<EntityType<?>> allowTypes;

    private static boolean validateEntityName(final Object obj) {
        return obj instanceof final String itemName && ForgeRegistries.ENTITY_TYPES.containsKey(new ResourceLocation(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        denyTypes = ENDER_LEAD_DENYS.get().stream().map(entityName -> ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(entityName))).collect(Collectors.toSet());
        allowTypes = ENDER_LEAD_ALLOWS.get().stream().map(entityName -> ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(entityName))).collect(Collectors.toSet());
    }
}
