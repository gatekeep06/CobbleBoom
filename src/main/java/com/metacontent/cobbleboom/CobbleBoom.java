package com.metacontent.cobbleboom;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.minecraft.world.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CobbleBoom implements ModInitializer {
    public static final String ID = "cobbleboom";
    public static final Logger LOGGER = LoggerFactory.getLogger(ID);
    public static final CobbleBoomConfig CONFIG = CobbleBoomConfig.init();

    @Override
    public void onInitialize() {

    }
}
