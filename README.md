<div align="center">
<img width="100" height="100" alt="icon" src="https://github.com/user-attachments/assets/4c050a85-67ca-4d2a-8332-a3aae52939d0" />
  
**BMWClient** is a free & open-source hacked client for Heypixel. Based [Liquidbounce](https://github.com/CCBlueX/LiquidBounce)

</div>

## 使用教程

请进入这个[qq群](https://qm.qq.com/q/tFU3WFlgXg)，然后点开右上角四个点然后点击**群文件**

<img width="400" height="400" alt="image" src="https://github.com/user-attachments/assets/3581b6ad-9e4e-4582-b587-cf8432e270ff" />
<img width="400" height="400" alt="image" src="https://github.com/user-attachments/assets/9282e1a2-edc6-4fe5-9a76-91669d4aca42" />

把压缩包的mod拖到你的**启动器**或者在启动器下.minecraft/mods目录


到这一步，请点击你**启动器**的**启动游戏**按钮就ok了

<img width="1920" height="1005" alt="image" src="https://github.com/user-attachments/assets/18cb3ad9-0847-4cd7-a0ec-26888ef56304" />

第一次进入你可能会看到**上面图片**的界面，这是在下载所需的依赖库，如果一直报错无法启动，请下载QQ群文件里BMWClient文件夹里的mcef.7z并解压到.minecraft/BMWClient那里

然后下载好了之后，请点击**MultiPlayer**，再点击**Direct**，输入**脱盒**给你的代理地址，点击**Join Sever**，就能享受黑客了

## BMWClient专属模块使用
- Auto L: 当与你战斗的敌人死亡时自动嘲讽
-- Mode: 模式
-- Normal: 正常模式，当你攻击过的人死亡了就自动嘲讽
-- Heypixel SW: 布吉岛空岛战争模式，当收到布吉岛空岛战争的击败提示就自动嘲讽
-- Word Pattern: 嘲讽内容
-- Poem: 嘲讽内容为随机古诗
-- Custom: 自定义嘲讽内容
-- Custom Messages: 自定义嘲讽发送的内容（如果添加了多条就是随机一条）
-- Global Message: 自动在嘲讽消息前加上"!"以发送全局消息
-- Name In Front: 在嘲讽内容开头加上敌人名字
-- Advertisement: 在嘲讽内容末尾加上BMWClient的广告
-- Random Text In End: 在嘲讽内容末尾加上随机字母+数字，防止被识别为刷屏
-- Length: 随机文本的长度

- Auto Report: 可以自动举报打死你的敌人，不会举报你添加的Friend和开启IRC后的其他BMW用户
注意：此模块仅在布吉岛空岛战争模式下有效，通过检测击杀事件自动执行举报命令并点击确认

- Old Auto Save: 掉落前自动开启Scaffold或Stuck自救，此模块过于老旧，建议使用Clutch模块
-- Auto Stuck: 自动开启Stuck自救
-- Stuck Only Void: 只有在掉入虚空前才会自救
-- Stuck Fall Distance: 从地面掉落多少格才自救
-- Auto Scaffold: 在战斗或受到伤害时自动开启Scaffold自救
-- Scaffold Only Void: 只有在掉入虚空前才会自救
-- Scaffold Void Distance: 从玩家掉落的位置往下数多少格都没有方块才自救
-- Pause On Flag: 检测到玩家回弹后暂停自救的时间，单位是tick

- Delay Blink: 在别人眼里你的移动比真实的移动慢
-- Delay: 慢多少刻（1刻=0.05秒）
-- Display Delay: 从开启Delay Blink开始计时，每tick都显示出来
-- Avoid Arrow: 通过瞬移自动躲避弓箭
-- Disable When: 什么时候自动停止瞬移（可多选）
-- Flag: 回弹时
-- Attack: 攻击别人时
-- Receive Hit: 受击时
-- Delay Packet Types: 要延迟处理的包的种类（可多选）
-- Outgoing: 客户端发送的包（即服务端收到的包）
-- Incoming: 客户端收到的包（即服务端发送的包）

- Fireball Fly: 通过延迟火球的击退以做到在空中飞行，效果类似Long Jump
-- Fireball Delay: 发射火球的间隔时间
-- Max Fireball Count: 发射火球的最大数量
-- Slot Reset Delay: 物品栏自动切换到火球后再切换回来的间隔时间
-- Jump: 发射火球前自动跳跃
-- Jump Delay: 跳跃后间隔多久发射火球
-- Rotations: 发射火球的转头设置
-- Pitch: 发射火球的俯仰角
-- Backwards: 发射火球时头朝后

- Grim Velocity: 可以绕过GrimAC的反击退
-- Mode: 反击退模式
-- Jump Reset: 跳跃重置
-- Require Kill Aura: 只有当Kill Aura正在攻击时才会触发跳跃重置
-- Full: 完全反击退，布吉岛已无法使用
-- Max Stuck Ticks: 卡住的时间上限，防止卡死
-- Only On Ground: 只有在地面时才会触发全反
-- Delay In Air: 在空中时是否延迟处理，开启后在空中受到击退时等待落地再触发
-- Debug: 当卡住的时间超过Max Stuck Ticks时会在聊天栏提醒
-- Attack Reduce: 在水平方向无击退，原理是通过多次发送点击包实现高cps以引起弱检测
-- Attack Count: 每当你受到击退时攻击敌人的次数
-- Auto Attack Count: 自动计算攻击次数
-- Attack Mode: 攻击模式
-- One Time: 在1tick内发送所有攻击包
-- Per Tick: 每tick只发送一个攻击包
-- Attack Target Range: 敌人在什么范围内时可以直接攻击并触发反击退
-- Alink Target Range: 敌人在什么范围时自动延迟击退
-- Alink Max Delay: 延迟击退的最大时间，单位是tick
-- Alink Until Ground: 开启后若玩家在空中受到击退，则Alink直到落地再处理
-- Auto Rotate: 自动转头
-- Rotation Time: 转头持续多久后恢复，单位是tick（Rotation Timing选择On Tick模式时此选项不生效）
-- Angle Smooth: 转头模式和速度选项
-- Rotation Timing: 转头的时刻
-- Normal: 正常转头
-- On Tick: 瞬间转头并瞬间转回来（类似无转头）
-- Not During Kill Aura: 在杀戮攻击敌人的时候禁用自动转头
-- Render Target Mode: 延迟击退时显示敌人位置方框（类似ESP）的样式
-- Require Kill Aura: 只有当开启Kill Aura时才能触发反击退
-- Debug: 聊天栏输出Alink状态和Attack Count
-- Delay: 延迟击退
-- Mode: 模式
-- In Air: 在空中受到击退时，延迟击退直到玩家落到地面上
-- Jump Reset: 跳跃重置
-- By Ticks: 把击退延迟一段固定时间
-- Delay: 延迟的时间，单位是tick
-- Render Target Mode: 延迟击退时显示敌人位置方框（类似ESP）的样式
-- Require Kill Aura: 只有当杀戮正在攻击敌人时才触发
-- Stop Backtrack: 在反击退工作时自动停止Backtrack
-- Pause On Flag: 回弹时反击退暂停的时间，单位是tick

- IRC: 可以在游戏内输入"#你想说的话"或".irc 你想说的话"与其他BMW用户聊天（仅限同一mc服务器内），也可以识别其他BMW用户，Kill Aura不会攻击其他BMW用户
-- Server Address: mc服务器的地址
-- IP: 自动获取mc服务器ip，脱盒不可用
-- Custom: 自定义mc服务器地址
-- Heypixel: 布吉岛服务器
-- OMG: 欧迈噶服务器

- Auto Break Out: 布吉岛空岛自动出笼，无需每次进入空岛都重新开启，具体操作：进入游戏时会自动出笼，但是无法移动，在游戏开始前2秒就可以移动了，在这2秒内你可以移动到箱子旁边。注意：如果在等待游戏中途退出游戏会一直无法移动，请关闭Auto Break Out并重新开启

- Clutch: 快掉入虚空时自动卡住并开启Scaffold自救
-- Stuck When Rescue: 自救时是否卡住
-- Max Rescue Time: 自救的最长时间，单位是秒
-- Max Try Count: 尝试自救的次数
-- Not During Combat: 在Kill Aura攻击敌人时不自救
-- Simulation Ticks: 模拟玩家移动的模拟深度（时间），单位是tick
-- Only Falling: 只在玩家正在下落时触发（速度y < -0.08）
-- Debug: 聊天栏输出自救状态和结果

- Grim No Slow: 可以绕过GrimAC的无减速
-- Food: 吃东西无减速
-- Mode: 模式
-- No C0F: 通过模拟高延迟并交换主副手物品来无减速
-- Half: 50%减速
-- Drop: 丢掉一个物品以无减速
-- Bow: 拉弓无减速
-- Mode: 模式
-- Half: 50%减速

- Attack Crystal: 自动攻击末影水晶
-- Range: 攻击范围
-- Swing Mode: 客户端或服务端是否显示挥手

- Auto MLG: 自动落地水
-- Fall Distance: 最小下落距离
-- Not During Kill Aura: 在杀戮攻击敌人的时候禁用落地水

- Color Blind Helper: 色盲派对辅助工具
-- Debug: 调试模式，显示当前游戏模式和到目标的距离
-- Heypixel: 布吉岛模式，定期自动切换到第5个物品栏
-- Heypixel Interval: 自动切换物品栏的间隔时间，单位是秒
-- Auto Stuck: 自动检测下方是否为虚空，如果是则开启Freeze自救
- 功能说明：
- 当快捷栏有方块时，自动寻找最近的同种方块并导航过去
- 当快捷栏没有方块时，自动寻找方块种类最丰富的区域
- 自动启用Auto Walk和Speed进行导航
- 检测游戏胜负状态，失败时自动暂停

- Helper: 辅助工具集合，包含多种实用功能
-- Put Out Fire: 自动灭火
功能：当玩家着火且在地面时，自动在脚下放置水桶灭火
-- Block Lava: 自动封堵岩浆
功能：检测周围岩浆并自动放置方块封堵
-- Range: 检测范围
-- Item To Block: 封堵方式
-- Block: 用方块封堵
-- Water: 用水桶封堵
-- Only On Ground: 只有在地面时才封堵
-- Block Water: 自动封堵水流
功能：检测周围水流并自动放置方块封堵
-- Range: 检测范围
-- Item To Block: 封堵方式
-- Block: 用方块封堵
-- Water: 用水桶封堵
-- Only On Ground: 只有在地面时才封堵
-- Block TNT: 自动挡住TNT
功能：检测周围TNT和苦力怕等爆炸物并自动放置方块挡住
-- Detection Range: 检测爆炸物的范围
-- Build Trigger Range: 触发建墙的范围
-- Wall Height: 墙的高度
-- Wall Width: 墙的宽度
-- Min Block Count: 最少需要的方块数量
-- Not During Combat: 在战斗中不建墙
-- Prefer Harder Blocks: 优先使用更硬的方块

- Auto Invite: 自动邀请敌人到队伍里，并自动嘲讽、唱歌、跨服传送（Shit分支）
-- Invite When: 什么时候邀请敌人进队伍（可多选）
-- Kill: 当你杀死敌人
-- Die: 当敌人杀死你
-- Auto L: 自动在队伍里嘲讽
-- Delay: 发送消息的间隔，单位是tick
-- Messages: 嘲讽消息
-- Random String In End: 在嘲讽内容末尾加上随机字母+数字，防止被识别为刷屏
-- Auto Sing: 自动在队伍里唱Heypixel Songs
-- Delay: 发送消息的间隔，单位是tick
-- Auto Zd W: 自动全队跨服传送到你所在服务器
-- By Delay: 固定时间间隔传送
-- Delay: 传送的时间间隔
-- When World Change: 当切换世界时传送
-- Hide Your Message: 在你的聊天栏隐藏你自动发送的嘲讽、唱歌消息

- Auto ScreenShot: 在布吉岛胜利后自动截图并保存到/BMWClient/screenshot/screenshots文件夹中
-- Delay: 胜利后延迟多少tick再截图

- TNT Warning: 若附近有即将爆炸的TNT，将在屏幕边框显示红色警告
-- Range: 检测TNT的范围
-- Thickness: 边框红色警告的厚度
-- Alpha: 边框红色警告的透明度
