package moe.kotori.explorernature.mixin;

import dev.shadowsoffire.apotheosis.garden.EnderLeadItem;
import moe.kotori.explorernature.Config;
import moe.kotori.explorernature.ExplorerNature;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EnderLeadItem.class)
public abstract class MixinEnderLeadItem extends Item {
    @Shadow abstract void playSound(Player player);

    public MixinEnderLeadItem(Properties p_41383_) {
        super(p_41383_);
    }

    /**
     * @author Kotori0629
     * @reason add more entity support
     */
    @Override
    @Overwrite(remap = false)
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        ResourceLocation targetLocation = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        ExplorerNature.LOGGER.debug("Target location: {}", targetLocation);

        for (EntityType<?> entityTypes : Config.denyTypes) {
            ResourceLocation denyTypes = ForgeRegistries.ENTITY_TYPES.getKey(entityTypes);
            if (denyTypes.equals(targetLocation)) {
                return super.onLeftClickEntity(stack, player, entity);
            }
        }

        boolean isAllow = false;
        for (EntityType<?> entityTypes : Config.allowTypes) {
            ResourceLocation allowTypes = ForgeRegistries.ENTITY_TYPES.getKey(entityTypes);
            if (allowTypes.equals(targetLocation)) {
                isAllow = true;
            }
        }

        if (stack.getOrCreateTagElement("entity_data").isEmpty() && (entity instanceof Animal || isAllow)) {
            CompoundTag tag = entity.serializeNBT();
            if (!player.level().isClientSide) {
                entity.discard();
                stack.getTag().put("entity_data", tag);
                stack.getTag().putString("name", entity.getDisplayName().getString());
                this.playSound(player);
            }
            return true;
        }
        return super.onLeftClickEntity(stack, player, entity);

    }
}
