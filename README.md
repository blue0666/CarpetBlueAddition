English | [简体中文](README_CN.md)

# Carpet Blue Addition

A [carpet](https://github.com/gnembon/fabric-carpet) addition for SSFS server, including some feature introduced from higher Minecraft versions.

## Feature

1.CobWebResistanceTuner

The first rule of the mod LOL!

"我们低版本没有细雪啊这个收集用不了= ="-xinianxiyuan

Change the Resistance parameter of **CobwebBlock** into **Powdersnow block**, easily understandble as we do not have powdersnow in 1.16
Technically useful for storage tech since these two blocks perform similarly in most cases

```
/carpet cobWebResistanceTuner true
```

2.CrossDimensionProjectileLootFix

A Fix for Player owned projectiles losing their player ownership when exiting portals like 23w41a

Then you can use your CrossDimension Ghast/Arrow->TNT/Firework-based Raid Farm or other powerful farms in lower versions.

```
/carpet crossDimensionProjectileLootFix true
```

3.TNTInWaterCantHurtBlockLikeEntity

Introduce the magic explosion feature that Damagesource touching water will not hurt BlockLikeEntities in 24w33a, discoverd by KD.

In detail, this rule will make those entities immune from TNTEntity explosion(when TNTEntity's collision box has even a little overlap with water):
Armor stand, Item frame and **Item entity**

```
/carpet tntInWaterCantHurtBlockLikeEntity true
```

4.SoundSuppresionIntroduce

Introduce the sound suppressor of a calibrated sculk sensor in 1.20-1.21

Place a trappedChest and name it "SoundSuppresor" or "声音抑制器" and put a lectern at its back with 15 pages, then use the specific channels as you want.

Tips:Only safe channels are transplanted, other channels are not in cosideration yet.

```
 /carpet soundSuppressionIntroduce true
```

5.SculkCatalystIntroduce

Introduce the Sculk Catalyst block from 1.19

Now a barrel block with name "sculkcatalyst" or "幽匿催发体" will work like that(absorb experience from entity death), but it wont spread sculk, play animation or update neighboor.

```
/carpet sculkCatalystIntroduce true
```

6.SoftCryObsidian

Turn its hardness into endstone like the softObsidian rule

7.NewWitchDropIntroduce

<del>"Totally Not Vanilla"</del>

Introduce the new witch drop in 1.21+

8.enderPearlLoadTicketIntroduce/enderPearlDiagonalChunkLoader

Introduce the enderPearl Chunk loading in 1.21.2+
