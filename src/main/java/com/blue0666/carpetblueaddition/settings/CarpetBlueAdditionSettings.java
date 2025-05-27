package com.blue0666.carpetblueaddition.settings;

import carpet.settings.Rule;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class CarpetBlueAdditionSettings {

    public static final String BLUE = "BLUE";

    @Rule(
            desc ="Change the resistance of cobweb into powdersnowblock",
            category = {BLUE, "feature"})
    public static boolean cobwebResistanceTuner;
    @Rule(
            desc = "Fix Player owned projectiles lose their player ownership when exiting portals like 23w41a",
            category = {BLUE,"bugfix","survival"}
    )
    public static boolean crossDimensionProjectileLootFix;
    @Rule(
            desc="Introduce the new explosion feature that Damagesource touching water will not hurt BlockLikeEntities",
            category = {BLUE,"feature","tnt"}
    )
    public static boolean tntInWaterCantHurtBlockLikeEntity;
    @Rule(
            desc="Introduce the SoundSuppresion from 1.20-1.21",
            category = {BLUE,"feature"}
    )
    public static boolean soundSuppressionIntroduce;
    @Rule(
            desc="Introduce the Sculk Catalyst Block from 1.19, only deleting experience orbs but wont spread sculk or update neighbour",
            category = {BLUE,"feature"}
    )
    public static boolean sculkCatalystIntroduce;
    @Rule(
            desc="Make crying-obsidian easy to mine",
            category={BLUE,"feature","client"}
    )
    public static boolean softCryingObsidian;
    @Rule(
            desc="Introduce the new witch drop in 1.21+",
            category={BLUE,"feature"}
    )
    public static boolean newWitchDropIntroduce;
    @Rule(
            desc="Introduce the ender_pearl chunk load ticket in 1.21.2+, " +
                    "tips :it will cover the enderpearlDiagonalChunkLoader rule",
            category={BLUE,"feature"}
    )
    public static boolean enderpearlLoadTicketIntroduce;
    @Rule(
            desc="Enables chunk loading for ender_pearl that occur diagonally (both X and Z axes), " +
                    "while preventing chunk load on single-axis movements.",
            category={BLUE,"feature"}
    )
    public static boolean enderpearlDiagonalChunkLoader;
    @Rule(
            desc="Now when player login in, all the pearls thrown by him will automatically be called and load chunks for 40 ticks, "+
                     "can work together will other loading system to permanently load chunks",
            category={BLUE,"feature"}
    )
    public static boolean enderpearlChunkLoaderOnPlayerLogin;
    @Rule(
            desc="Boats can be leashed by leads",
            category={BLUE,"feature"}
    )
    public static boolean leashableBoat;
}
