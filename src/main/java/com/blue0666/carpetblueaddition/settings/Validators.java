package com.blue0666.carpetblueaddition.settings;

import carpet.api.settings.CarpetRule;
import carpet.api.settings.Validator;
import net.minecraft.server.command.ServerCommandSource;

public class Validators {
    public static class SoundSuppressorRadiusController extends Validator<Integer> {
        private SoundSuppressorRadiusController() {
        }

        @Override
        public Integer validate(ServerCommandSource source, CarpetRule<Integer> currentRule, Integer newValue, String string) {
            return newValue > 0 && newValue <= 64 ? newValue : null;
        }

        @Override
        public String description() {
            return "You must choose a value from 1 to 64";
        }
    }
}
