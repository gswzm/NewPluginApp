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

