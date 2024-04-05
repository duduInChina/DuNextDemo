## DuDemo

使用Kotlin和MVI模式，搭建手脚架、工具方法

### 下载预览


### Common Api
- Hilt：自动依赖注入
- AppStartup：使用ContentProvider优化启动初始化流程，减轻Application负担
- SplashScreen启动画面：优化启动白屏问题
- BaseView：顶层赋值完成Title、ViewBinding、ViewModel初始化
- RootLayout：Activity和Fragment加入根布局，装载标题、加载UI、空布局UI
- PermissionX：权限判断，在当前Activity加入空布局的Fragment完成判断流程
- build-logic：注册插件模块，ProjectConfig配置项目，libs管理包版本

<img src="./images/common.gif" alt="weather" style="zoom:67%;" /> 

### 天气预报

根据《第一行代码》Demo进行优化调整
- IntentViewModel：定制MVI分发流程
- 沉浸式标题栏适配，已适配sdk30，StatusBarUtil
- ViewModel：Base层泛型初始化实现，LiveData更改时通知视图
- DataStore：代替SharedPreferences，封装委托by方式实现调用
- Retrofit：网络请求，手机网络异常、服务器接口异常、接口业务异常，展示不同失败UI
        CallAdapter：增加Flow数据流方式返回
        Converter：增加kotlinx.serialization解析JSON，同时判断业务ErrorCode

<img src="./images/weather.jpg" alt="weather" style="zoom:67%;" /> 
