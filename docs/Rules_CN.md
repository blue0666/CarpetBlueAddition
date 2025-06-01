[English](Rules.md) | 简体中文

# 规则列表

### 蜘蛛网阻力系数修改

模组的第一个小规则awa

将蜘蛛网阻力计算的系数修改成和细雪完全一致(但是依然推动会被破坏，而不是像细雪一样可推动)

*"我们低版本没有细雪啊这个收集用不了= ="-xinian*

* 默认值: `false`
* 可选参数: `true`, `false`
* 开启方法: `/carpet cobWebResistanceTuner ture`
* 分类: `BLUE` , `feature`

### 跨纬度投掷物实体搜索修复

如23w41a的表现一致，修复跨纬度后投掷物无法找到实体以致不能吃到掠夺效果和提供buff

然后就可以使用恶魂和烟花掠夺袭击塔了

* 默认值: `false`
* 可选参数: `true`, `false`
* 开启方法: `/carpet crossDimensionProjectileLootFix ture`
* 分类: `BLUE` , `survival`, `bugfix`

### k爆低版本移植

将k爆移植到低版本   *"这不是愚人节玩笑，奶爆原地退役!"-KD*

具体的结果是tnt只要沾水，盔甲架，展示框和**掉落物实体**就会完全免疫这次爆炸

* 默认值: `false`
* 可选参数: `true`, `false`
* 开启方法: `/carpet kExplosionIntroduce ture`
* 分类: `BLUE` , `feature` , `tnt`

### 声音抑制移植

一个部分声音抑制效果的移植

使用方式是将箱子命名为声音抑制器或者SoundSuppressor，讲台的放置和高版本一致，然后该怎么正常用就怎么用

如果还有哪些频道需要加可以直接提交，但是仅崩服功能的肯定不会加，没啥必要

Tips:如果要防崩溃可以安装Carpet TIS Addition, 那个的防崩溃选项已经包含此处的几个方块更新频道了

* 默认值: `false`
* 可选参数: `true`, `false`
* 开启方法: `/carpet soundSuppressionIntroduce ture`
* 分类: `BLUE` , `feature`

具体的频道列表:


| 频道 | 抑制的内容                                |
| :--: | ----------------------------------------- |
|  5  | 基于盔甲架放装备的物品分身                |
|  9  | 活板门，门关闭时抑制                      |
|  10  | 活板门，门打开时抑制                      |
|  10  | TNT点燃不删除，不消耗打火石耐久(复制掠夺) |
|  10  | 音符盒激活时抑制                          |
|  11  | 钟激活时抑制                              |

![声音抑制示意图](.././src/main/resources/assets/carpetblueaddition/SoundSuppression.png)

#### 声音抑制范围控制

自定义声音抑制箱子的范围，输入值表示半径，1-64格，>64并不是不能实现，只是为了性能考虑

* 默认值: `false`
* 可选参数: `8`, `16`, `32`, `64`
* 切换示例: `/carpet soundSuppressionIntroduce 64`
* 分类: `BLUE` , `feature`

### 幽匿催发体移植

~~"越来越轮椅了"~~

将幽匿催发体能吸收经验的功能移植到低版本，现在将木桶命名为幽匿催发体或者sculkcatalyst就能让对应范围(和高版本一致)内的生物死亡不掉落经验

但是蔓延幽匿方块，催发体动画和发出nc更新这三个步骤就没有了

* 默认值: `false`
* 可选参数: `true`, `false`
* 开启方法: `/carpet sculkCatalystIntroduce ture`
* 分类: `BLUE` , `feature`

### 易碎哭泣黑曜石

既然黑曜石可以易碎，那么哭泣黑曜石为什么不能呢？

类似的，将硬度修改为和末地石一样,方便拆迁奇奇怪怪的建筑和爆炸室(逃)

* 默认值: `false`
* 可选参数: `true`, `false`
* 开启方法: `/carpet softCryingObsidian ture`
* 分类: `BLUE` , `feature`

### 新版女巫掉落物移植

"~用这个功能的不要说自己在玩原版生存--YanHwa~"

移植1.21+的新版女巫掉落物

* 默认值: `false`
* 可选参数: `true`, `false`
* 开启方法: `/carpet newWitchDropIntroduce ture`
* 分类: `BLUE` , `feature`

### 末影珍珠区块加载移植

一个高版本(1.21.2+)的末影珍珠自加载功能移植，和高版本表现一致的一点是一切加载都需要**投掷珍珠的那个玩家在线**，没有Owner属性的末影珍珠是不能加载区块的

目前唯一没有的功能是珍珠跨纬度传送玩家，因为末影珍珠过门的方法在游戏里未定义，不是很容易实现

* 默认值: `false`
* 可选参数: `true`, `false`
* 开启方法: Listed in the graph below
* 分类: `BLUE` , `feature`


| 规则列表 | 名称                   | 效果                                            | 备注                |
| :------: | :--------------------- | :---------------------------------------------- | :------------------ |
|    1    | 珍珠区块加载移植       | 珍珠在运动时会自动加载区块                      | 会覆盖2             |
|    2    | 珍珠斜向运动区块加载   | 珍珠只有XZ轴都在移动(前后tick偏移都>0.01)才加载 | 用于弱加载炮        |
|    3    | 世界重启玩家珍珠自加载 | 玩家进入世界会自动加载一次他丢了珍珠的区块      | 可和1组合来加载区块 |

### 拴绳可以栓村民

顾名思义，让村民的是否可以被拴住改为true

* 默认值: `false`
* 可选参数: `true`, `false`
* 开启方法: `/carpet leashableVillager ture`
* 分类: `BLUE` , `feature`
