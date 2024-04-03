package com.metacontent.cobbleboom.mixin;

import com.cobblemon.mod.common.api.battles.interpreter.BattleMessage;
import com.cobblemon.mod.common.api.battles.interpreter.Effect;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.ShowdownInterpreter;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.metacontent.cobbleboom.CobbleBoom;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ShowdownInterpreter.class)
public abstract class ShowdownInterpreterMixin {
    @Inject(method = "handleMoveInstruction", at = @At("TAIL"), remap = false)
    protected void injectHandleMoveInstructionMethod(PokemonBattle battle, BattleMessage message, List<String> remainingLines, CallbackInfo ci) {
        Effect effect = message.effectAt(1);

        BattlePokemon battlePokemon = message.getBattlePokemon(0, battle);
        if (effect == null || battlePokemon == null) {
            return;
        }
        String moveId = effect.getId();
        if (moveId.equals("explosion") || moveId.equals("selfdestruct") || moveId.equals("mistyexplosion")) {
            PokemonEntity entity = battlePokemon.getEntity();
            if (entity != null) {
                Vec3d pos = entity.getPos();
                World world = battle.getPlayers().get(0).getServerWorld();
                float power = (float) world.getGameRules().get(CobbleBoom.COBBLEBOOM_POWER).get();
                world.createExplosion(entity, pos.x, pos.y, pos.z, power, World.ExplosionSourceType.MOB);
            }
        }
    }
}
