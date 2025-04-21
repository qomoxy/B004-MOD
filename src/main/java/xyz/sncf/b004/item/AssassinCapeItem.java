package xyz.sncf.b004.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber
public class AssassinCapeItem extends ArmorItem {

    private static final Map<UUID, CapeData> PLAYER_DATA = new HashMap<>();
    private static final int EFFECT_DURATION = 6000; // 5 minutes (en ticks)
    private static final int COOLDOWN_DURATION = 3600; // 3 minutes (en ticks)
    private static final int CHECK_INTERVAL = 20; // Vérifie toutes les secondes

    private static class CapeData {
        int effectTimer = 0;
        int cooldownTimer = 0;
        boolean effectsActive = false;
    }

    public AssassinCapeItem(ArmorMaterial material, Properties properties) {
        super(material, Type.CHESTPLATE, properties.durability(250));
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (level.isClientSide) return;

        UUID uuid = player.getUUID();
        CapeData data = PLAYER_DATA.computeIfAbsent(uuid, k -> new CapeData());

        // Vérifie périodiquement pour économiser les performances
        if (player.tickCount % CHECK_INTERVAL != 0) return;

        boolean wearingCape = isWearingCape(player);

        if (wearingCape && !data.effectsActive && data.cooldownTimer <= 0) {
            // Active les effets
            applyEffects(player);
            data.effectsActive = true;
            data.effectTimer = EFFECT_DURATION;
        }
        else if (!wearingCape && data.effectsActive) {
            // Désactive immédiatement les effets
            removeEffects(player);
            data.effectsActive = false;
            data.cooldownTimer = COOLDOWN_DURATION;
        }

        // Gestion du timer d'effet
        if (data.effectsActive && data.effectTimer > 0) {
            data.effectTimer -= CHECK_INTERVAL;
            if (data.effectTimer <= 0) {
                removeEffects(player);
                data.effectsActive = false;
                data.cooldownTimer = COOLDOWN_DURATION;
            }
        }
    }

    private boolean isWearingCape(Player player) {
        return player.getItemBySlot(EquipmentSlot.CHEST).getItem() == this;
    }

    private void applyEffects(Player player) {
        player.addEffect(new MobEffectInstance(
                MobEffects.INVISIBILITY,
                EFFECT_DURATION,
                0,
                false,
                false,
                true // Affiche les particules pour le joueur
        ));

        player.addEffect(new MobEffectInstance(
                MobEffects.MOVEMENT_SPEED,
                EFFECT_DURATION,
                1, // Speed II (1.5x)
                false,
                false
        ));
    }

    private void removeEffects(Player player) {
        player.removeEffect(MobEffects.INVISIBILITY);
        player.removeEffect(MobEffects.MOVEMENT_SPEED);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide) return;

        UUID uuid = event.player.getUUID();
        CapeData data = PLAYER_DATA.get(uuid);

        if (data != null && data.cooldownTimer > 0) {
            data.cooldownTimer--;
        }
    }
}