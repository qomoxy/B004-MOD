package xyz.sncf.b004.events;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import xyz.sncf.b004.registry.ModItems;

public class PlayerInvisibilityHandler {

    public static void register() {
        MinecraftForge.EVENT_BUS.register(new PlayerInvisibilityHandler());
    }

    @SubscribeEvent
    public void onPlayerAttacked(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (isWearingCape(player)) {
                removeEffects(player);
                spawnReappearanceParticlesAndSound(player);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerAttack(AttackEntityEvent event) {
        Player player = event.getEntity();
        if (isWearingCape(player)) {
            removeEffects(player);
            spawnReappearanceParticlesAndSound(player);
        }
    }

    private boolean isWearingCape(Player player) {
        return player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.ASSASSIN_CAPE.get();
    }

    private void removeEffects(Player player) {
        player.removeEffect(MobEffects.INVISIBILITY);
        player.removeEffect(MobEffects.MOVEMENT_SPEED);
    }

    private void spawnReappearanceParticlesAndSound(Player player) {
        // Spawn particles
        for (int i = 0; i < 20; i++) {
            double offsetX = (player.level().random.nextDouble() - 0.5) * 2.0;
            double offsetY = player.level().random.nextDouble() * 2.0;
            double offsetZ = (player.level().random.nextDouble() - 0.5) * 2.0;

            player.level().addParticle(
                    ParticleTypes.HAPPY_VILLAGER,
                    player.getX() + offsetX,
                    player.getY() + offsetY,
                    player.getZ() + offsetZ,
                    0.0, 0.0, 0.0
            );
        }

        // Play sound effect for reappearing
        player.level().playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                net.minecraft.sounds.SoundEvents.ARMOR_EQUIP_LEATHER, // Example sound
                net.minecraft.sounds.SoundSource.PLAYERS,
                1.0F,
                1.0F
        );
    }
}