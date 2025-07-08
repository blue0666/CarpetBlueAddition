package com.blue0666.carpetblueaddition.settings;

import carpet.api.settings.CarpetRule;
import carpet.api.settings.Validator;
import com.sun.java.accessibility.util.Translator;
import net.minecraft.server.command.ServerCommandSource;
import org.jetbrains.annotations.Nullable;

public class CarpetBlueValidators {
    public static class SoundSuppressionRadiusValidator<T extends Number> extends Validator<T> {
        @Override
        public T validate(ServerCommandSource source, CarpetRule<T> currentRule, T newValue, String string) {
            return newValue.doubleValue() >= 0 && newValue.doubleValue() <= 128 ? newValue : null;
        }

        @Override
        public String description() {
            return "SoundSuppression Radius Must be between 0-128";
        }
    }
}
