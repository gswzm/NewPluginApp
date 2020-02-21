[官方文档](https://github.com/Qihoo360/RePlugin/wiki)
## 主程序接入
### 第 1 步：添加 RePlugin Host Gradle 依赖
在**项目根目录**的 build.gradle（注意：不是 app/build.gradle） 中添加 replugin-host-gradle 依赖：

```
buildscript {
    dependencies {
        classpath 'com.qihoo360.replugin:replugin-host-gradle:2.3.2'
        ...
    }
}
```
### 第 2 步：添加 RePlugin Host Library 依赖
在 **app/build.gradle** 中应用 **replugin-host-gradle** 插件，并添加 **replugin-host-lib** 依赖:

```
android {
    ...
}

// ATTENTION!!! Must be PLACED AFTER "android{}" to read the applicationId

apply plugin: 'replugin-host-gradle'

/**
 * 配置项均为可选配置，默认无需添加
 * 更多可选配置项参见replugin-host-gradle的RepluginConfig类
 * 可更改配置项参见 自动生成RePluginHostConfig.java
 */
repluginHostConfig {
    /**
     * 是否使用 AppCompat 库
     * 不需要个性化配置时，无需添加
     */
    useAppCompat = true
    /**
     * 背景不透明的坑的数量
     * 不需要个性化配置时，无需添加
     */
    countNotTranslucentStandard = 6
    countNotTranslucentSingleTop = 2
    countNotTranslucentSingleTask = 3
    countNotTranslucentSingleInstance = 2
}

dependencies {
    compile 'com.qihoo360.replugin:replugin-host-lib:2.3.2'
    ...
}
```
### 务必注意
- 请将apply plugin: 'replugin-host-gradle'放在 android{} 块之后，防止出现无法读取applicationId，导致生成的坑位出现异常
- 如果您的应用需要支持AppComat，则除了在主程序中引入AppComat-v7包以外，还需要在宿主的build.gradle中添加下面的代码若不支持AppComat则请不要设置此项：

```
repluginHostConfig {
    useAppCompat = true
}
```
###### 开启useAppCompat后，我们会在编译期生成AppCompat专用坑位，这样插件若使用AppCompat的Theme时就能生效了。若不设置，则可能会出现“IllegalStateException: You need to use a Theme.AppCompat theme (or descendant) with this activity.”的异常。
- 如果您的应用需要个性化配置坑位数量，则需要在宿主的build.gradle中添加下面的代码：

```
repluginHostConfig {
     /**
     * 背景不透明的坑的数量
     */
    countNotTranslucentStandard = 6
    countNotTranslucentSingleTop = 2
    countNotTranslucentSingleTask = 3
    countNotTranslucentSingleInstance = 2
}
```
### 第 3 步：配置 Application 类
让工程的 Application 直接继承自 RePluginApplication。

## 插件接入指南
### 第 1 步：添加 RePlugin Plugin Gradle 依赖
在**项目根目录**的 build.gradle（注意：不是 app/build.gradle） 中添加 replugin-plugin-gradle 依赖：

```
buildscript {
    dependencies {
        classpath 'com.qihoo360.replugin:replugin-plugin-gradle:2.3.2'
        ...
    }
}
```
### 第 2 步：添加 RePlugin Plugin Library 依赖
在** app/build.gradle** 中应用 **replugin-plugin-gradle** 插件，并添加 **replugin-plugin-lib** 依赖:

```
apply plugin: 'replugin-plugin-gradle'

dependencies {
    compile 'com.qihoo360.replugin:replugin-plugin-lib:2.3.2'
    ...
}
```
### 外置插件使用
将集成插件化的apk打包放在服务器
在需要打开的地方调用下面方法：

```
MPluginHelper.getInstance().openPlugin(this, "survey", "com.gsww.pluginapk.PluginActivity", new MPluginHelper.InstallListener() {

});

```
下载的方法在工具类中已经写好

升级的时候根据后台获取的版本号进行判断

```
PluginInfo info = RePlugin.getPluginInfo(pluginName);
            //版本号由 后台接口获得，然后进行对比，插件版本低于接口的版本就下载更新
            if (info.getVersion() < 2) {

                //http://36.40.85.189:8081/authApi/feign/download
                downPlugin(context, "https://ali-fir-pro-binary.fir.im/fa819d2b28faa326794161d24e1a3b30e9ebf12e.apk?auth_key=1582198033-0-0-e8923b8d70ab526ab4953802a4c5543e", pluginName, activityName, true);
            }
```


