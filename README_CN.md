[English](README.md) | 简体中文

# Carpet Blue Addition

为Ssfs生电区定制的[地毯](https://github.com/gnembon/fabric-carpet)附属，提供了一些高版本特性<del>和not vanilla的轮椅</del>移植

## Feature

1.蜘蛛网阻力修改 ``CobWebResistanceTuner``

模组的第一个小规则awa

"我们低版本没有细雪啊这个收集用不了= ="-xinianxiyuan

将蜘蛛网阻力计算的系数修改成和细雪完全一致(但是依然推动会被破坏，而不是像细雪一样可推动)

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

Tips:如果要防崩溃可以安装Carpet TIS Addition, 那个的防崩溃选项已经包含此处的几个方块更新频道了

具体的频道列表:


| 频道 | 抑制的内容                                |
| :--: | ----------------------------------------- |
|  5  | 基于盔甲架放装备的物品分身                |
|  9  | 活板门，门关闭时抑制                      |
|  10  | 活板门，门打开时抑制                      |
|  10  | TNT点燃不删除，不消耗打火石耐久(复制掠夺) |
|  10  | 音符盒激活时抑制                          |
|  11  | 钟激活时抑制                              |

5.幽匿催发体移植``sculkCatalystIntroduce``

"越来越轮椅了"

将幽匿催发体能吸收经验的功能移植到低版本，现在将木桶命名为幽匿催发体或者sculkcatalyst就能让对应范围(和高版本一致)内的生物死亡不掉落经验

但是蔓延幽匿方块，催发体动画和发出nc更新这三个步骤就没有了

6.易碎哭泣黑曜石``softCryingObsidian``

既然黑曜石可以易碎，那么哭泣黑曜石为什么不能呢？

类似的，将硬度修改为和末地石一样,方便拆迁奇奇怪怪的建筑和爆炸室(逃)

7.新版女巫掉落物移植``newwitchdropintroduce``

"<del>用这个功能的不要说自己在玩原版生存</del>--YanHwa"

移植1.21+的新版女巫掉落物

8.末影珍珠加载区块修改``enderpearlLoadTicketIntroduce/enderpearlDiagonalChunkLoader``

开启沿轴运动不加载规则，仅会在两个方向同时运动的情况下加载区块，用于炮的测试

开启全加载会覆盖沿轴规则

tips:高版本的珍珠有三个功能:

1.加载未加载的同维度新区块

2.跨纬度直接传送玩家

3.在世界重载时自动加载区块

目前只写了第一个(lazy)
