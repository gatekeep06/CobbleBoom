package com.metacontent.cobbleboom.mixin;

import com.cobblemon.mod.common.api.battles.interpreter.BattleMessage;
import com.cobblemon.mod.common.api.battles.interpreter.Effect;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.interpreter.instructions.MoveInstruction;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.metacontent.cobbleboom.CobbleBoom;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MoveInstruction.class)
public abstract class MoveInstructionMixin {
    @Final @Shadow private BattleMessage message;

    @Inject(method = "invoke", at = @At("TAIL"), remap = false)
    protected void injectHandleMoveInstructionMethod(PokemonBattle battle, CallbackInfo ci) {
        Effect effect = message.effectAt(1);

        BattlePokemon battlePokemon = message.battlePokemon(0, battle);
        if (effect == null || battlePokemon == null) {
            return;
        }
        String moveId = effect.getId();
        if (CobbleBoom.CONFIG.moves.containsKey(moveId)) {
            PokemonEntity entity = battlePokemon.getEntity();
            explode(entity, CobbleBoom.CONFIG.moves.get(moveId));
        }
    }

    @Unique
    private static void explode(@Nullable PokemonEntity entity, float power) {
        if (entity != null) {
            Vec3d pos = entity.getPos();
            World world = entity.getWorld();
            world.createExplosion(entity, pos.x, pos.y, pos.z, power, World.ExplosionSourceType.MOB);
        }
    }
}
