# chat_room

#### 项目介绍
使用Spring Boot + WebSocket 构建的一个简易聊天室。

#### 软件架构
Spring Boot + WebSocket


#### 安装教程

1. maven打包
2. 部署到tomcat
3. 如果要打jar包，请将WebSocketConfig.java类上面的注解恢复

#### 使用说明

1. 后台暂无数据库，不保留用户聊天数据；
2. 图片最大只能发送1M的，格式为常见图片格式；
3. 用户发送的图片会在服务器上短暂储存供其他用户加载，约一分钟后会被删除；
4. 打开项目后输入任意昵称和房间号即可进入指定房间，也可选择已存在的房间，同一房间中昵称不可重复；
5. 标签页不在前台时收到消息会有提示音和弹窗通知，用户可根据需要开启/关闭此功能，开关是侧边栏上方的音量按钮。

#### 参与贡献

1. 炒饭
2. 漂泊的树叶

#### 界面展示
##### 1. 登录界面

<img src="https://images.gitee.com/uploads/images/2018/1010/103255_33f93edd_687582.jpeg" alt="登录界面" title="登录.jpg" style="zoom: 33%;" />

##### 2. 主界面

<img src="https://images.gitee.com/uploads/images/2018/1015/103910_60e496f7_687582.jpeg" alt="主界面" title="TIM截图20181015100719.jpg" style="zoom:33%;" />

##### 3. 消息通知

<img src="https://images.gitee.com/uploads/images/2018/1015/103941_7cc8a1fb_687582.jpeg" alt="输入图片说明" title="TIM截图20181015103529.jpg" style="zoom:33%;" />

#### 演示地址

- ##### [在线演示地址](http://chat.vip.cpolar.cn)


#### 打个广告

我的另一个不值一提项目[kings_poster](https://gitee.com/chaofan2685_admin/kings_poster)可以下载王者荣耀皮肤原画，图片都是高清的，有兴趣的话可以去参观一下呦！[演示地址](http://king.vip.cpolar.cn)