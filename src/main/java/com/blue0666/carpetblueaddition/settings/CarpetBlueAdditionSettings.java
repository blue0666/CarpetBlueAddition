package com.blue0666.carpetblueaddition.settings;

import carpet.settings.Rule;

public class CarpetBlueAdditionSettings {

    public static final String BLUE = "BLUE";
    @Rule(
            desc = "Change the resistance of cobweb into powdersnowblock" ,
            category = {BLUE,"feature"}
    )
    public static boolean cobwebResistanceTuner;
    @Rule(
            desc = "Fix Player owned projectiles lose their player ownership when exiting portals like 23w41a",
            category = {BLUE,"bugfix","survival"}
    )
    public static boolean crossDimensionProjectileLootFix;
    @Rule(
            desc="Introduce the new explosion feature that Damagesource touching water will not hurt BlockLikeEntities",
            category = {BLUE,"feature"}
    )
    public static boolean tntInWaterCantHurtBlockLikeEntity;
    @Rule(
            desc="Introduce the SoundSuppresion from 1.20-1.21",
            category = {BLUE,"feature"}
    )
    public static boolean soundSuppressionIntroduce;
}
