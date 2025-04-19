[English](README.md) | 简体中文

# Carpet Blue Addition

为Ssfs生电区定制的[地毯](https://github.com/gnembon/fabric-carpet)附属，提供了一些高版本特性移植

## Feature

1.蜘蛛网阻力修改 ``CobWebResistanceTuner``

模组的第一个小规则awa

"我们低版本没有细雪啊这个收集用不了= ="-xinianxiyuan

将蜘蛛网阻力计算的系数修改成和细雪完全一致

2.跨纬度投掷物实体搜索修复``CrossDimensionProjectileLootFix``

如23w41a的表现一致，修复跨纬度后投掷物无法找到实体以致不能吃到掠夺效果和提供buff

3.k爆低版本移植``TNTInWaterCantHurtBlockLikeEntity``

将k爆移植到低版本
"这不是愚人节玩笑，奶爆原地退役!"-KD

具体的结果是tnt只要沾水，盔甲架，展示框和掉落物实体就会完全免疫这次爆炸

4.声音抑制移植``soundsuppressionintroduce``

一个部分声音抑制效果的移植

使用方式是将箱子命名为声音抑制器或者SoundSuppressor，讲台的放置和高版本一致，然后该怎么正常用就怎么用

如果还有哪些频道需要加可以直接提交，但是仅崩服功能的肯定不会加，没啥必要

具体的频道列表:


| 频道 | 抑制的内容                                |
| :--: | ----------------------------------------- |
|  5  | 基于盔甲架放装备的物品分身                |
|  9  | 活板门，门关闭时抑制                      |
|  10  | 活板门，门打开时抑制                      |
|  10  | TNT点燃不删除，不消耗打火石耐久(复制掠夺) |
|  10  | 音符盒激活时抑制                          |
|  11  | 钟激活时抑制                              |
